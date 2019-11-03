#include <CUnit/Basic.h>
#include <stdlib.h>
#include <stdio.h>

#include "hash_table.h"
#include "linked_list.h"
#include "common.h"
#include "backend.h"
#include "utils.h"

#define Success(v)      (option_t) { .success = true, .value = v };
#define Failure()       (option_t) { .success = false };
#define Successful(o)   (o.success == true)
#define Unsuccessful(o) (o.success == false)

#define int_elem(i) (elem_t) {.ioopm_int = (i)}
#define unsigned_elem(u) (elem_t) {.ioopm_u_int = (u)}
#define bool_elem(b) (elem_t) {.ioopm_bool = (b)}
#define str_elem(s) (elem_t) {.ioopm_str = (s)}
#define float_elem(f) (elem_t) {.ioopm_float = (f)}
#define void_elem(v) (elem_t) {.ioopm_void_ptr = (v)}
#define merch_elem(m) (elem_t) {.ioopm_merch = (m)}
#define shelf_elem(sh) (elem_t) {.ioopm_shelf = (sh)}
#define cart_elem(c) (elem_t) {.ioopm_cart = (c)}
#define item_elem(ci) (elem_t) {.ioopm_item = (ci)}





struct database
{
  ioopm_hash_table_t *merch_ht;
  ioopm_hash_table_t *shelves_ht; //Currently we do not use this hash_table, but we are keeping it because it could be useful later on
  ioopm_hash_table_t *carts;
  unsigned int id_counter; 
};

struct link
{
  elem_t value;
  ioopm_link_t *next;
};

struct list
{
  size_t list_size; 
  ioopm_eq_function equal;
  ioopm_link_t *first;
  ioopm_link_t *last;
};


struct hash_table
{
  float load_factor;
  size_t capacity;
  entry_t **buckets;
  hash_function hash_func;
  ioopm_eq_function key_eq_func;
  ioopm_eq_function value_eq_func;
  size_t size;
};

int init_suite(void)
{
  return 0;
}

int clean_suite(void)
{
  return 0;
}



void test_add_merch(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_add_merch(db); //Add first merch
  option_t result = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn"));
  CU_ASSERT_TRUE(result.success);

  char *desc = result.value.ioopm_merch->desc;
  char *desc2 = "beskrivning";
  bool comparison = strcmp(desc, desc2) == 0;
  CU_ASSERT_TRUE(comparison);

  int price = result.value.ioopm_merch->price_per_unit;
  CU_ASSERT_EQUAL(price, 10);
  
  int size = result.value.ioopm_merch->stock->list_size;
  CU_ASSERT_EQUAL(size, 0);
  
  ioopm_destroy_database(db);
}

void test_remove_merch(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_remove_merch(db); //Try removing from an empty database
  
  ioopm_add_merch(db); //Add a merch

  ioopm_remove_merch(db); //and remove it
  option_t result = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn"));
  CU_ASSERT_FALSE(result.success);
  ioopm_remove_merch(db); //This line checks for segfault when removing from an empty database
  
  ioopm_destroy_database(db);
}

void test_edit(void) //this test also tests "ask to continue"-functionality
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_add_merch(db); //Add a merch

  ioopm_edit_merch(db); //Edit it, and check that we can cancel the action

  option_t result = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn"));
  CU_ASSERT_FALSE(result.success);

  result = ioopm_hash_table_lookup(db->merch_ht, str_elem("nytt_namn"));
  CU_ASSERT_TRUE(result.success);

  char *desc = result.value.ioopm_merch->desc;
  CU_ASSERT_TRUE(strcmp(desc, "ny_beskrivning") == 0);

  int price = result.value.ioopm_merch->price_per_unit;
  
  CU_ASSERT_EQUAL(price, 20);
  
  int size = result.value.ioopm_merch->stock->list_size;
  CU_ASSERT_EQUAL(size, 0);
  
  ioopm_destroy_database(db);
}

void test_replenish(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_replenish_stock(db); //test replenishing on an empty database, we expect it to simply print that the database is empty and do nothing
  
  ioopm_add_merch(db); //Add first merch
  ioopm_replenish_stock(db); //Replenish first merch

  option_t result = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn"));
  CU_ASSERT_TRUE(result.success);
  
  int size = result.value.ioopm_merch->stock->list_size;
  CU_ASSERT_EQUAL(size, 1);

  shelf_t *shelf = result.value.ioopm_merch->stock->first->value.ioopm_shelf;
  CU_ASSERT_TRUE(strcmp(shelf->shelf_name, "H20") == 0);
  CU_ASSERT_EQUAL(shelf->amount, 42);

  ioopm_add_merch(db); //Add second merch
  ioopm_replenish_stock(db); //Replenish second merch

  option_t result2 = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn2"));
  CU_ASSERT_TRUE(result2.success);
  
  size = result2.value.ioopm_merch->stock->list_size;
  CU_ASSERT_EQUAL(size, 1);

  ioopm_replenish_stock(db); //Replenish first merch again
  result = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn"));
  CU_ASSERT_TRUE(result.success);
  
  size = result.value.ioopm_merch->stock->list_size;
  CU_ASSERT_EQUAL(size, 2);
  char *shelf1 = result.value.ioopm_merch->stock->first->value.ioopm_shelf->shelf_name;
  char *shelf2 = result.value.ioopm_merch->stock->last->value.ioopm_shelf->shelf_name;
  char *shelf3 = result.value.ioopm_merch->stock->first->next->value.ioopm_shelf->shelf_name;
  CU_ASSERT_TRUE(strcmp(shelf1, "A10") == 0);
  CU_ASSERT_TRUE(strcmp(shelf2, "H20") == 0);
  CU_ASSERT_TRUE(strcmp(shelf3, "H20") == 0);

  //check that shelves_ht is updated properly
  result = ioopm_hash_table_lookup(db->shelves_ht, str_elem("A10"));
  CU_ASSERT_TRUE(result.success);
  
  ioopm_destroy_database(db);
}

void test_create_cart(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_create_cart(db);

  option_t result = ioopm_hash_table_lookup(db->carts, unsigned_elem(0));
  CU_ASSERT_TRUE(result.success);

  ioopm_create_cart(db);
  result = ioopm_hash_table_lookup(db->carts, unsigned_elem(1));
  CU_ASSERT_TRUE(result.success);

  result = ioopm_hash_table_lookup(db->carts, unsigned_elem(0)); //Check that the original cart hasn't been overwritten
  CU_ASSERT_TRUE(result.success);
  
  ioopm_destroy_database(db);
}

void test_remove_cart(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_remove_cart(db); //try removing from empty database
  
  ioopm_create_cart(db); //Cart 0
  ioopm_create_cart(db); //Cart 1
  ioopm_create_cart(db); //Cart 2

  ioopm_remove_cart(db); //Remove cart 1

  option_t result = ioopm_hash_table_lookup(db->carts, unsigned_elem(1));
  CU_ASSERT_FALSE(result.success);

  result = ioopm_hash_table_lookup(db->carts, unsigned_elem(0));
  CU_ASSERT_TRUE(result.success);

  result = ioopm_hash_table_lookup(db->carts, unsigned_elem(2));
  CU_ASSERT_TRUE(result.success);
  
  ioopm_destroy_database(db);
}

void test_add_to_cart(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_create_cart(db);
  ioopm_add_merch(db); //Add merch
  ioopm_replenish_stock(db); //Replenish merch
  
  ioopm_add_to_cart(db); //Add it to cart

  option_t result = ioopm_hash_table_lookup(db->carts, unsigned_elem(0));

   
  item_t *item = result.value.ioopm_cart->basket->first->value.ioopm_item;
  
  CU_ASSERT_EQUAL(result.value.ioopm_cart->basket->list_size, 1);
  printf("%s", item->name);
  CU_ASSERT_TRUE(strcmp(item->name, "namn") == 0);
  CU_ASSERT_EQUAL(item->amount, 10);
  CU_ASSERT_EQUAL(item->price_per_unit, 10);
  
  ioopm_destroy_database(db);
}

void test_remove_from_cart(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_create_cart(db); 
  ioopm_add_merch(db); //Add merch
  ioopm_replenish_stock(db); //Replenish merch
  ioopm_add_to_cart(db); //Add merch to cart

  ioopm_remove_from_cart(db); //Tests only removing some
  option_t result = ioopm_hash_table_lookup(db->carts, unsigned_elem(0));
  CU_ASSERT_TRUE(result.success);
  CU_ASSERT_EQUAL(result.value.ioopm_cart->basket->first->value.ioopm_item->amount, 20);
  
  char *name = result.value.ioopm_cart->basket->first->value.ioopm_item->name;
  option_t lookup_merch = ioopm_hash_table_lookup(db->merch_ht, str_elem(name));
  CU_ASSERT_EQUAL(lookup_merch.value.ioopm_merch->available_amount, 30);
  
  ioopm_remove_from_cart(db); //Tests removing everything
  result = ioopm_hash_table_lookup(db->carts, unsigned_elem(0));
  CU_ASSERT_TRUE(result.success);
  CU_ASSERT_EQUAL(result.value.ioopm_cart->basket->list_size, 0);
  CU_ASSERT_EQUAL(result.value.ioopm_cart->basket->first, NULL);
  CU_ASSERT_EQUAL(result.value.ioopm_cart->basket->last, NULL);

  ioopm_destroy_database(db);
}

void test_checkout(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_create_cart(db);
  ioopm_add_merch(db); //Add merch
  ioopm_replenish_stock(db); //Replenish it
  ioopm_add_to_cart(db); //Add it to cart

  ioopm_checkout(db); //Confirm checkout
  
  option_t result = ioopm_hash_table_lookup(db->carts, unsigned_elem(0));
  CU_ASSERT_FALSE(result.success);
  
  ioopm_destroy_database(db);
}

void test_resize_and_continue_listing(void)
{
  ioopm_database_t *db = ioopm_create_database();
  ioopm_add_merch(db); //Adds 23 merch
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  ioopm_add_merch(db); 
  ioopm_add_merch(db);
  ioopm_add_merch(db);
  
  ioopm_remove_merch(db); //test removing the 23rd merch after continue listing
  ioopm_remove_merch(db); //test removing the 22rd merch before continue listing
  ioopm_remove_merch(db); //test removing the first merch after continue listing

  option_t namn9 = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn9"));
  CU_ASSERT_FALSE(namn9.success);

  option_t namn8 = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn8"));
  CU_ASSERT_FALSE(namn8.success);

  option_t namn1 = ioopm_hash_table_lookup(db->merch_ht, str_elem("namn1"));
  CU_ASSERT_FALSE(namn1.success);
  
  
  ioopm_destroy_database(db);
}

int main()
{
  CU_pSuite test_suite1 = NULL;
  
  if (CUE_SUCCESS != CU_initialize_registry())
    return CU_get_error();


  test_suite1 = CU_add_suite("Test Suite 1: webstore backend", init_suite, clean_suite);
  if (NULL == test_suite1)
    {
      CU_cleanup_registry();
      return CU_get_error();
    }
  
  if (
      (NULL == CU_add_test(test_suite1, "test add merch", test_add_merch)) ||
      (NULL == CU_add_test(test_suite1, "test remove merch", test_remove_merch)) ||
      (NULL == CU_add_test(test_suite1, "test edit merch", test_edit)) ||
      (NULL == CU_add_test(test_suite1, "test replenish", test_replenish)) ||
      (NULL == CU_add_test(test_suite1, "test create cart", test_create_cart)) ||
      (NULL == CU_add_test(test_suite1, "test remove cart", test_remove_cart)) ||
      (NULL == CU_add_test(test_suite1, "test add to cart", test_add_to_cart)) ||
										   (NULL == CU_add_test(test_suite1, "test remove from cart", test_remove_from_cart)) ||
      (NULL == CU_add_test(test_suite1, "test checkout", test_checkout)) ||
      (NULL == CU_add_test(test_suite1, "test resize and continue listing", test_resize_and_continue_listing))
      )
    {
      CU_cleanup_registry();
      return CU_get_error();
    }
  
  CU_basic_set_mode(CU_BRM_VERBOSE);
  CU_basic_run_tests();
  CU_cleanup_registry();
  return CU_get_error();
}
