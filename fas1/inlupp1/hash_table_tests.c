#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <CUnit/Basic.h>
#include "hash_table.h"
#include "list_linked.h"

#define int_elem(i) (elem_t) {.ioopm_int=(i)}
#define str_elem(s) (elem_t) {.ioopm_str=(s)}
#define bool_elem(b) (elem_t) {.ioopm_bool=(b)}
#define void_elem(v) (elem_t) {.ioopm_void_ptr=(v)}

#define No_Buckets 17

#define Free(ptr) {free(ptr); ptr = NULL;}

struct entry
{
  elem_t key;       // holds the key
  elem_t value;   // holds the value
  entry_t *next; // points to the next entry (possibly NULL)
};


struct hash_table
{
  ioopm_hash_function hash;
  ioopm_eq_function eq_key;
  ioopm_eq_function eq_val;
  entry_t *buckets;
  int no_buckets;
  float load_factor;
};

struct option
{
  bool success;
  char *value;
};

static bool int_equal(elem_t fstInt, elem_t sndInt)
{
  return fstInt.ioopm_int == sndInt.ioopm_int;
}

static bool str_equal(elem_t fstStr, elem_t sndStr)
{
  return (strcmp(fstStr.ioopm_str, sndStr.ioopm_str) == 0);
}

static bool key_equiv(ioopm_hash_table_t *ht, elem_t key, elem_t value, void *x)
{
  elem_t elem = *(elem_t *)x;
 
  return ht->eq_key(elem, key);
}

static bool value_equiv(ioopm_hash_table_t *ht, elem_t key_ignored, elem_t value, void *x)
{
  //char *tmp = (char *) x;
  //elem_t t = str_elem(tmp);
  elem_t elem = *((elem_t *)x);

  //return  ht->hash(value) == ht->hash(tmp);
  return ht->eq_val(elem, value);
}


static void apply_int(ioopm_hash_table_t *ht, elem_t key, elem_t *value, void *arg)
{
  elem_t t = int_elem(*((int *) arg)); 
  *value = t;
}

static void apply_str(ioopm_hash_table_t *ht, elem_t key, elem_t *value, void *arg)
{
  elem_t t = str_elem((char *) arg); 
  *value = t;
}

static unsigned long int_extract(elem_t key)
{
  return (key.ioopm_int);
}

static unsigned long str_extract(elem_t key)
{
  unsigned long result = 0;
  char *str = key.ioopm_str;
  do
    {
      result = result * 31 + *str;
      str = str + 1;
    }
  while(*str != '\0');
  
  return (result);
}


static unsigned long bool_extract(elem_t key)
{
  return (key.ioopm_bool);
}

/*
static unsigned long void_ptr_extract(elem_t key)
{
  return (key.ioopm_void_ptr);
}
*/




void test_hash_table_insert_str(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);
  
  ioopm_hash_table_insert(ht, int_elem(1), str_elem("fresh key"));
  option_t o = ioopm_hash_table_lookup(ht, int_elem(1));
  CU_ASSERT_EQUAL(o.value,"fresh key");
  
  ioopm_hash_table_insert(ht, int_elem(1), str_elem("replace key"));
  o = ioopm_hash_table_lookup(ht, int_elem(1));
  CU_ASSERT_EQUAL(o.value,"replace key");

  ioopm_hash_table_insert(ht, int_elem(18), str_elem("additional key"));
  o = ioopm_hash_table_lookup(ht, int_elem(18));
  CU_ASSERT_EQUAL(o.value,"additional key");

  ioopm_hash_table_insert(ht, int_elem(1371111), str_elem("large key"));
  o = ioopm_hash_table_lookup(ht, int_elem(1371111));
  CU_ASSERT_EQUAL(o.value,"large key");
  
  ioopm_hash_table_destroy(ht);
}


void test_hash_table_insert_int(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(str_extract, str_equal, str_equal);
  
  ioopm_hash_table_insert(ht, str_elem("fresh key"), int_elem(1));
  option_t o = ioopm_hash_table_lookup(ht, int_elem(1));
  CU_ASSERT_EQUAL(o.value,"fresh key");
  
  ioopm_hash_table_insert(ht, int_elem(1), str_elem("replace key"));
  o = ioopm_hash_table_lookup(ht, int_elem(1));
  CU_ASSERT_EQUAL(o.value,"replace key");

  ioopm_hash_table_insert(ht, int_elem(18), str_elem("additional key"));
  o = ioopm_hash_table_lookup(ht, int_elem(18));
  CU_ASSERT_EQUAL(o.value,"additional key");

  ioopm_hash_table_insert(ht, int_elem(1371111), str_elem("large key"));
  o = ioopm_hash_table_lookup(ht, int_elem(1371111));
  CU_ASSERT_EQUAL(o.value,"large key");
  
  ioopm_hash_table_destroy(ht);
}



void test_hash_table_lookup(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  option_t opt;
  for (int i = 0; i < (No_Buckets + 1); ++i)
    {
      opt = ioopm_hash_table_lookup(ht, int_elem(i));
      CU_ASSERT_EQUAL(opt.value, NULL);
    }

  opt = ioopm_hash_table_lookup(ht, int_elem(35));
  CU_ASSERT_FALSE(opt.success);

  ioopm_hash_table_insert(ht, int_elem(1), str_elem("insert"));
  opt = ioopm_hash_table_lookup(ht, int_elem(1));
  CU_ASSERT_EQUAL(opt.value,"insert");

  ioopm_hash_table_destroy(ht);
}


void test_hash_table_remove(void)
{
  char *msg;
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);
  
  msg = ioopm_hash_table_remove(ht, int_elem(1)).ioopm_str;
  CU_ASSERT_STRING_EQUAL(msg, "Entry doesn't exist!");

  ioopm_hash_table_insert(ht, int_elem(1), str_elem("insert"));
  msg = ioopm_hash_table_remove(ht, int_elem(1)).ioopm_str;
  CU_ASSERT_EQUAL(msg, "insert");

  ioopm_hash_table_destroy(ht);
}


void test_hash_table_size(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);
 
  CU_ASSERT_EQUAL(ioopm_hash_table_size(ht), 0);

  for (int i = 0; i < 10; ++i) {
    ioopm_hash_table_insert(ht, int_elem(i), str_elem("test"));
  }

  int size = ioopm_hash_table_size(ht);
  
  CU_ASSERT_EQUAL(size, 10);
  
  ioopm_hash_table_destroy(ht);
}


void test_hash_table_is_empty(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  CU_ASSERT_TRUE(ioopm_hash_table_is_empty(ht));

  ioopm_hash_table_insert(ht, int_elem(1), str_elem("inserted value"));
  CU_ASSERT_FALSE(ioopm_hash_table_is_empty(ht));
  
  
  ioopm_hash_table_destroy(ht);
}

void test_hash_table_clear(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  ioopm_hash_table_clear(ht);
  CU_ASSERT_TRUE(ioopm_hash_table_is_empty(ht));

  ioopm_hash_table_insert(ht, int_elem(1), str_elem("inserted value"));
  ioopm_hash_table_clear(ht);
  CU_ASSERT_TRUE(ioopm_hash_table_is_empty(ht));

  ioopm_hash_table_destroy(ht);
}


void test_hash_table_keys(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  for (int i = 0; i < 10; ++i)
    {
      ioopm_hash_table_insert(ht, int_elem(i), str_elem("hej"));
    }

  ioopm_list_t *list = ioopm_hash_table_keys(ht);

  for (int i = 0; i < 10; ++i)
    {
      CU_ASSERT_EQUAL(ioopm_linked_list_get(list, i).ioopm_int, i);
    }
  
  
  ioopm_hash_table_destroy(ht);
  ioopm_linked_list_destroy(list);
}

void test_hash_table_values(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  ioopm_hash_table_insert(ht, int_elem(1), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(2), str_elem("på"));
  ioopm_hash_table_insert(ht, int_elem(3), str_elem("dig"));
  ioopm_hash_table_insert(ht, int_elem(4), str_elem("jag"));
  ioopm_hash_table_insert(ht, int_elem(5), str_elem("gillar"));
  ioopm_hash_table_insert(ht, int_elem(6), str_elem("kaffe"));
  ioopm_hash_table_insert(ht, int_elem(7), str_elem("från"));
  ioopm_hash_table_insert(ht, int_elem(8), str_elem("skrubben"));
  ioopm_hash_table_insert(ht, int_elem(9), str_elem("som"));
  ioopm_hash_table_insert(ht, int_elem(10), str_elem("bränns"));

  ioopm_list_t *arr = ioopm_hash_table_values(ht);

  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 0), str_elem("hej")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 1), str_elem("på")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 2), str_elem("dig")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 3), str_elem("jag")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 4), str_elem("gillar")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 5), str_elem("kaffe")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 6), str_elem("från")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 7), str_elem("skrubben")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 8), str_elem("som")));
  CU_ASSERT_TRUE(str_equal(ioopm_linked_list_get(arr, 9), str_elem("bränns")));

  ioopm_linked_list_destroy(arr);
  ioopm_hash_table_destroy(ht);
}


// Tests both keys, values and ioopm_hash_table_any().
void test_hash_table_has_key(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  for (int i = 0; i < 10; ++i)
    {
      ioopm_hash_table_insert(ht, int_elem(i), str_elem("hej"));
      CU_ASSERT_TRUE(ioopm_hash_table_has_key(ht, int_elem(i)));
    }

  CU_ASSERT_FALSE(ioopm_hash_table_has_key(ht, int_elem(76)));
  
  ioopm_hash_table_destroy(ht);
}

void test_hash_table_has_value_str(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  char *str = "hej";
  
  ioopm_hash_table_insert(ht, int_elem(1), str_elem(str));
  CU_ASSERT_TRUE(ioopm_hash_table_has_value(ht, str_elem(str)));

  char *dup;
  dup = ioopm_strdup(str);
  
  CU_ASSERT_TRUE(ioopm_hash_table_has_value(ht, str_elem(dup)));
  

  CU_ASSERT_FALSE(ioopm_hash_table_has_value(ht, str_elem("foo")));

  free(dup);
  ioopm_hash_table_destroy(ht);
}

void test_hash_table_has_value_int(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(str_extract, str_equal, int_equal);

  int i = 1;
  
  ioopm_hash_table_insert(ht, str_elem("foo"), int_elem(i));
  CU_ASSERT_TRUE(ioopm_hash_table_has_value(ht, int_elem(i)));

  CU_ASSERT_FALSE(ioopm_hash_table_has_value(ht, int_elem(23)));

  ioopm_hash_table_destroy(ht);
}



void test_hash_table_all_str(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  ioopm_hash_table_insert(ht, int_elem(1), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(2), str_elem("på"));
  ioopm_hash_table_insert(ht, int_elem(3), str_elem("på"));
  ioopm_hash_table_insert(ht, int_elem(4), str_elem("dig"));
  ioopm_hash_table_insert(ht, int_elem(5), str_elem("jag"));
  ioopm_hash_table_insert(ht, int_elem(6), str_elem("gillar"));
  ioopm_hash_table_insert(ht, int_elem(7), str_elem("kaffe"));
  ioopm_hash_table_insert(ht, int_elem(8), str_elem("från"));
  ioopm_hash_table_insert(ht, int_elem(9), str_elem("skrubben"));

  CU_ASSERT_FALSE(ioopm_hash_table_all(ht, value_equiv, &str_elem("hej")));
  
  ioopm_hash_table_insert(ht, int_elem(2), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(3), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(4), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(5), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(6), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(7), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(8), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(9), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(10), str_elem("hej"));
  
  CU_ASSERT_TRUE(ioopm_hash_table_all(ht, value_equiv, &str_elem("hej")));
  
  ioopm_hash_table_destroy(ht);
}

void test_hash_table_all_int(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(str_extract, str_equal, int_equal);

  ioopm_hash_table_insert(ht, str_elem("a"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("b"), int_elem(2));
  ioopm_hash_table_insert(ht, str_elem("c"), int_elem(2));
  ioopm_hash_table_insert(ht, str_elem("d"), int_elem(3));
  ioopm_hash_table_insert(ht, str_elem("e"), int_elem(4));
  ioopm_hash_table_insert(ht, str_elem("f"), int_elem(5));
  ioopm_hash_table_insert(ht, str_elem("g"), int_elem(6));
  ioopm_hash_table_insert(ht, str_elem("h"), int_elem(7));
  ioopm_hash_table_insert(ht, str_elem("i"), int_elem(8));

  CU_ASSERT_FALSE(ioopm_hash_table_all(ht, value_equiv, &int_elem(1)));
  
  ioopm_hash_table_insert(ht, str_elem("b"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("c"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("d"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("e"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("f"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("g"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("h"), int_elem(1));
  ioopm_hash_table_insert(ht, str_elem("i"), int_elem(1));
  
  CU_ASSERT_TRUE(ioopm_hash_table_all(ht, value_equiv, &int_elem(1)));
  
  ioopm_hash_table_destroy(ht);
}

void test_hash_table_apply_to_all_str(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  ioopm_hash_table_insert(ht, int_elem(1), str_elem("hej"));
  ioopm_hash_table_insert(ht, int_elem(2), str_elem("på"));
  ioopm_hash_table_insert(ht, int_elem(3), str_elem("dig"));
  ioopm_hash_table_insert(ht, int_elem(4), str_elem("jag"));
  ioopm_hash_table_insert(ht, int_elem(5), str_elem("gillar"));
  ioopm_hash_table_insert(ht, int_elem(6), str_elem("kaffe"));
  ioopm_hash_table_insert(ht, int_elem(7), str_elem("från"));
  ioopm_hash_table_insert(ht, int_elem(8), str_elem("skrubben"));
  ioopm_hash_table_insert(ht, int_elem(9), str_elem("som"));
  ioopm_hash_table_insert(ht, int_elem(10), str_elem("bränns"));

  CU_ASSERT_FALSE(ioopm_hash_table_all(ht, value_equiv, &str_elem("hej")));
  
  ioopm_hash_table_apply_to_all(ht, apply_str, "hej");
  
  CU_ASSERT_TRUE(ioopm_hash_table_all(ht, value_equiv, &str_elem("hej")));
  
  ioopm_hash_table_destroy(ht);
}


void test_hash_table_apply_to_all_int(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(str_extract, str_equal, int_equal);

  ioopm_hash_table_insert(ht, str_elem("hej"), int_elem(2));
  ioopm_hash_table_insert(ht, str_elem("på"), int_elem(3));
  ioopm_hash_table_insert(ht, str_elem("dig"), int_elem(4));
  ioopm_hash_table_insert(ht, str_elem("jag"), int_elem(5));
  ioopm_hash_table_insert(ht, str_elem("gillar"), int_elem(6));
  ioopm_hash_table_insert(ht, str_elem("kaffe"), int_elem(7));
  ioopm_hash_table_insert(ht, str_elem("från"), int_elem(8));
  ioopm_hash_table_insert(ht, str_elem("skrubben"), int_elem(8));
  ioopm_hash_table_insert(ht, str_elem("som"), int_elem(9));
  ioopm_hash_table_insert(ht, str_elem("bränns"), int_elem(10));

  CU_ASSERT_FALSE(ioopm_hash_table_all(ht, value_equiv, &int_elem(1)));

  int i = 2;
  ioopm_hash_table_apply_to_all(ht, apply_int, &i);

  elem_t t = int_elem(2);
  CU_ASSERT_TRUE(ioopm_hash_table_all(ht, value_equiv, &t));

  
  
  ioopm_hash_table_destroy(ht);
}

void test_hash_table_adaptive_buckets(void)
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(int_extract, int_equal, str_equal);

  for (int i = 0; i < 30; ++i) {
    ioopm_hash_table_insert(ht, int_elem(i), str_elem("hej"));
  }

  printf("Nunber of buckets: %d\n", ht->no_buckets);
  CU_ASSERT_EQUAL(ht->no_buckets, 34);
  
  ioopm_hash_table_destroy(ht);
}
  
int main()
{
  CU_initialize_registry();
  
  CU_pSuite test_suite = CU_add_suite("Test the hash_table functions", NULL, NULL);
  CU_add_test(test_suite, "Insert", test_hash_table_insert_str);

  CU_add_test(test_suite, "Lookup", test_hash_table_lookup);
  CU_add_test(test_suite, "Remove", test_hash_table_remove);
  CU_add_test(test_suite, "Size", test_hash_table_size);
  CU_add_test(test_suite, "Empty", test_hash_table_is_empty);
  CU_add_test(test_suite, "Clear", test_hash_table_clear);
  CU_add_test(test_suite, "Keys", test_hash_table_keys);
  CU_add_test(test_suite, "Values", test_hash_table_values); 
  CU_add_test(test_suite, "Has key", test_hash_table_has_key);
  CU_add_test(test_suite, "Has value (Str)", test_hash_table_has_value_str);
  CU_add_test(test_suite, "Has value (Int)", test_hash_table_has_value_int);
  
  CU_add_test(test_suite, "All (Str)", test_hash_table_all_str);
  CU_add_test(test_suite, "All (Int)", test_hash_table_all_int);
  
  CU_add_test(test_suite, "Apply to all (Str)", test_hash_table_apply_to_all_str);
  CU_add_test(test_suite, "Apply to all (Int)", test_hash_table_apply_to_all_int);

  CU_add_test(test_suite, "Adaptive buckets", test_hash_table_adaptive_buckets);
  
  CU_basic_run_tests();

  CU_cleanup_registry(); 
  
  return 0;
}
