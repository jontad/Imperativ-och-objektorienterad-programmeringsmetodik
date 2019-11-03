#ifndef _COMMONH_
#define _COMMONH_

#include <stdlib.h>
#include <stdbool.h>


//type definitions for linked list
typedef struct list ioopm_list_t;
typedef struct link ioopm_link_t;


typedef bool(*ioopm_char_predicate)(ioopm_link_t *element, void *extra);
typedef void(*ioopm_apply_char_function)(ioopm_link_t **element, void *extra);



typedef struct merch merch_t;

typedef struct item item_t;
struct item
{
  char *name;
  unsigned int amount; // amount of merch in cart
  unsigned int price_per_unit;
  merch_t *pointer; //pointer to merch in ht
};

typedef struct cart cart_t;
struct cart
{
  unsigned int id; // key 
  ioopm_list_t *basket;  // list of items, where total amount is amount of merch in cart,
                        // and locations is the locations from which we take that merch on checkout.
                       // this should be automatically assigned when added to cart! 
};


typedef struct shelf shelf_t;
struct shelf
{
  char *shelf_name;
  unsigned int amount; //amount on shelf
};


struct merch
{
  char *name; //key
  char *desc;
  unsigned int price_per_unit;
  unsigned int available_amount; //amount of merch in stock that is not in cart
  ioopm_list_t *stock;
};

typedef union elem elem_t;
union elem
{
  int ioopm_int;
  unsigned int ioopm_u_int;
  bool ioopm_bool;
  char *ioopm_str;
  float ioopm_float;
  void *ioopm_void_ptr;
  merch_t *ioopm_merch;
  shelf_t *ioopm_shelf;
  cart_t *ioopm_cart;
  item_t *ioopm_item;
  /// other choices certainly possible
};



//type definitions for hash table
typedef struct entry entry_t;
typedef struct hash_table ioopm_hash_table_t;
typedef struct option option_t;
struct option
{
  bool success;
  elem_t value; 
};


typedef unsigned long(*hash_function)(elem_t key); 
typedef bool(*ioopm_eq_function)(elem_t a, elem_t b); /// Compares two elements and returns true if they are equal

typedef bool(*ioopm_predicate)(elem_t key, elem_t value, void *extra);
typedef void(*ioopm_apply_function)(elem_t key, elem_t *value, void *extra);

//type definitions for iterator 
typedef struct iter ioopm_list_iterator_t;

//type definitions for webstore.c
typedef struct database ioopm_database_t;
#endif


