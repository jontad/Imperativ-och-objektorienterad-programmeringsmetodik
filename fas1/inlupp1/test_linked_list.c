#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <CUnit/Basic.h>
#include "list_linked.h"
#include "iterator.h"


#define int_elem(i) (elem_t) {.ioopm_int=(i)}
#define str_elem(s) (elem_t) {.ioopm_str=(s)}
#define bool_elem(b) (elem_t) {.ioopm_bool=(b)}
#define void_elem(v) (elem_t) {.ioopm_void_ptr=(v)}


#define Free(ptr) {free(ptr); ptr = NULL;}


struct link 
{
  elem_t value; //int
  ioopm_link_t *next;
};


struct list
{
  size_t list_size;
  ioopm_eq_function equal;
  ioopm_link_t *first;
  ioopm_link_t *last;
};

struct iter
{
  ioopm_link_t *current;
  ioopm_list_t *list;
};



static bool check_func(ioopm_link_t *element, void *extra)
{
  return ((element->value).ioopm_int == 0);
}

static bool elem_equal(elem_t fstInt, elem_t sndInt)
{
  return fstInt.ioopm_int == sndInt.ioopm_int;
}

static void apply_func(ioopm_link_t **element, void *extra)
{
  ((*element)->value) = int_elem(0);
}

static void link_destroy(ioopm_link_t *link)
{
  Free(link);
}

static elem_t remove_aux(ioopm_list_t *list, int index, ioopm_link_t *prev, ioopm_link_t *elem)
{
  elem_t value = elem->value;

  prev->next = elem->next;
  link_destroy(elem);

  --list->list_size;
  
  return value;
}

static elem_t test_iterator_func(ioopm_list_t *list, int index)
{
  ioopm_list_iterator_t *iter = ioopm_list_iterator(list);
  for(int i = 0; i<index; ++i)
    {
      if(!ioopm_iterator_has_next(iter))
	{
	  break;
	}
      ioopm_iterator_next(iter);
    }

  ioopm_link_t *elem = iter->current;
  
  ioopm_iterator_reset(iter);
  for(int i = 0; i<index-1; ++i)
    {
      if(!ioopm_iterator_has_next(iter))
	{
	  break;
	}
      ioopm_iterator_next(iter);
    }
  
  
  
  ioopm_link_t *prev = iter->current;
  elem_t value;
  
  if (index == 0)
    {
      value = prev->value;
      list->first = prev->next;
      link_destroy(prev);
      --list->list_size;
    }
  else if(elem != NULL)
    {
      value = remove_aux(list, index, prev, elem);
    }
  ioopm_iterator_destroy(iter);
  return value;
}


void test_linked_list_get(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_append(ll, int_elem(13));
  int i = ioopm_linked_list_get(ll, 0).ioopm_int;
  CU_ASSERT_EQUAL(i, 13);

  
  ioopm_linked_list_insert(ll, 7, int_elem(14));
  i = ioopm_linked_list_get(ll, 1).ioopm_int;
  CU_ASSERT_EQUAL(i,14);

  ioopm_linked_list_insert(ll, 8, int_elem(15));
  i = ioopm_linked_list_get(ll, 2).ioopm_int;
  CU_ASSERT_EQUAL(i,15);
  
    
  ioopm_linked_list_destroy(ll);
}




void test_linked_list_insert(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_insert(ll, 5, int_elem(10));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 10);

  
  ioopm_linked_list_insert(ll, 1, int_elem(1));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 1).ioopm_int, 1);
  
  ioopm_linked_list_insert(ll, 0, int_elem(5));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 5);

  ioopm_linked_list_insert(ll, 35, int_elem(27));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 3).ioopm_int, 27);

  ioopm_linked_list_insert(ll, -17, int_elem(99));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 99);
  
  ioopm_linked_list_destroy(ll);
}


void test_linked_list_prepend(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_prepend(ll, int_elem(10));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 10);

  
  ioopm_linked_list_prepend(ll, int_elem(100));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 100);

  
  ioopm_linked_list_prepend(ll, int_elem(1000));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 1000);
  
  ioopm_linked_list_destroy(ll);
}


void test_linked_list_append(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_append(ll, int_elem(10));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 10);

  
  ioopm_linked_list_append(ll, int_elem(100));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 1).ioopm_int, 100);

  ioopm_linked_list_append(ll, int_elem(1000));
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 2).ioopm_int, 1000);
  
  
  ioopm_linked_list_destroy(ll);
}

void test_linked_list_remove(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_append(ll, int_elem(10));
  ioopm_linked_list_append(ll, int_elem(100));
  ioopm_linked_list_append(ll, int_elem(1000));
  
  ioopm_linked_list_remove(ll, 1);
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 1).ioopm_int, 1000);  

  
  ioopm_linked_list_remove(ll, 0);
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 1000);  
  
  ioopm_linked_list_remove(ll, 0);
  CU_ASSERT_PTR_NULL(ll->first);  
  
  ioopm_linked_list_destroy(ll);
}


void test_linked_list_contains(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  CU_ASSERT_FALSE(ioopm_linked_list_contains(ll, int_elem(10)));

  ioopm_linked_list_append(ll, int_elem(10));
  CU_ASSERT_TRUE(ioopm_linked_list_contains(ll, int_elem(10)));
  		 
  ioopm_linked_list_append(ll, int_elem(100));
  ioopm_linked_list_append(ll, int_elem(1000));
  CU_ASSERT_TRUE(ioopm_linked_list_contains(ll, int_elem(1000)));
  
  ioopm_linked_list_destroy(ll);
}



void test_linked_list_size(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  size_t i = ioopm_linked_list_size(ll);
  CU_ASSERT_EQUAL(i, 0);
  
  ioopm_linked_list_append(ll, int_elem(0));
  ioopm_linked_list_append(ll, int_elem(0));
  ioopm_linked_list_append(ll, int_elem(0));
  
  i = ioopm_linked_list_size(ll);
  CU_ASSERT_EQUAL(i,3);

  ioopm_linked_list_destroy(ll);
}

void test_linked_list_is_empty(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);
  CU_ASSERT_TRUE(ioopm_linked_list_is_empty(ll));

  ioopm_linked_list_append(ll, int_elem(10));
  ioopm_linked_list_append(ll, int_elem(100));
  ioopm_linked_list_append(ll, int_elem(1000));
  CU_ASSERT_FALSE(ioopm_linked_list_is_empty(ll));

  ioopm_linked_list_destroy(ll);
}


void test_linked_list_all(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_append(ll, int_elem(0));
  ioopm_linked_list_append(ll, int_elem(0));
  ioopm_linked_list_insert(ll, 1, int_elem(0));
  
  CU_ASSERT_TRUE(ioopm_linked_list_all(ll, check_func, NULL));

  ioopm_linked_list_append(ll, int_elem(10));

  CU_ASSERT_FALSE(ioopm_linked_list_all(ll, check_func, NULL));
  
  ioopm_linked_list_destroy(ll);
}

void test_linked_list_any(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_append(ll, int_elem(10));
  ioopm_linked_list_append(ll, int_elem(1000));
  ioopm_linked_list_append(ll, int_elem(1100));
  
  CU_ASSERT_FALSE(ioopm_linked_list_any(ll, check_func, NULL));

  ioopm_linked_list_append(ll, int_elem(0));

  CU_ASSERT_TRUE(ioopm_linked_list_any(ll, check_func, NULL));
  
  ioopm_linked_list_destroy(ll);
}


void test_linked_list_apply_to_all(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_append(ll, int_elem(10));
  ioopm_linked_list_append(ll, int_elem(1000));
  ioopm_linked_list_append(ll, int_elem(1100));
  CU_ASSERT_FALSE(ioopm_linked_list_any(ll, check_func, NULL));

  ioopm_linked_apply_to_all(ll, apply_func, NULL);
  CU_ASSERT_TRUE(ioopm_linked_list_any(ll, check_func, NULL));

  ioopm_linked_list_destroy(ll);
}


void test_iterator(void)
{
  ioopm_list_t *ll = ioopm_linked_list_create(elem_equal);

  ioopm_linked_list_append(ll, int_elem(10));
  ioopm_linked_list_append(ll, int_elem(100));
  ioopm_linked_list_append(ll, int_elem(1000));

  test_iterator_func(ll, 1);
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 1).ioopm_int, 1000);  
  
  test_iterator_func(ll, 0);
  CU_ASSERT_EQUAL(ioopm_linked_list_get(ll, 0).ioopm_int, 1000);  

  test_iterator_func(ll, 0);
  CU_ASSERT_PTR_NULL(ll->first);  

  ioopm_linked_list_destroy(ll);
}

int main()
{
  CU_initialize_registry();
  
  CU_pSuite test_suite = CU_add_suite("Test the linked list functions", NULL, NULL);
  CU_add_test(test_suite, "Insert", test_linked_list_insert);
  CU_add_test(test_suite, "Get", test_linked_list_get);

  CU_add_test(test_suite, "Prepend", test_linked_list_prepend);
  CU_add_test(test_suite, "Append", test_linked_list_append);

  CU_add_test(test_suite, "Remove", test_linked_list_remove);
  CU_add_test(test_suite, "Contains", test_linked_list_contains); //DENNA FEL (comparison)
  CU_add_test(test_suite, "Size", test_linked_list_size);
  CU_add_test(test_suite, "Empty", test_linked_list_is_empty);
  CU_add_test(test_suite, "All", test_linked_list_all);
  CU_add_test(test_suite, "Any", test_linked_list_any); 
  CU_add_test(test_suite, "Apply", test_linked_list_apply_to_all);
  CU_add_test(test_suite, "Iterator", test_iterator);
    
  CU_basic_run_tests();

  CU_cleanup_registry(); 
  
  return 0;
}
