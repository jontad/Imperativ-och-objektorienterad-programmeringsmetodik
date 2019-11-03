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

//////////////////////////////// STRUCTS /////////////////////////////////////



struct database
{
  ioopm_hash_table_t *merch_ht;
  ioopm_hash_table_t *shelves_ht;
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





ioopm_database_t *ioopm_create_database()
{
  ioopm_database_t *db = calloc(1, sizeof(ioopm_database_t));
  db->merch_ht = ioopm_hash_table_create(str_hash_func, equality_function_str, equality_function_merch, 0.75, 17);
  db->shelves_ht = ioopm_hash_table_create(str_hash_func, equality_function_str, equality_function_merch, 0.75, 17);
  db->carts = ioopm_hash_table_create(uns_int_hash_func, equality_function_uns_int, equality_function_pointer, 0.75, 17);
  db->id_counter = 0;
  return db;
}

static void free_items_in_cart(ioopm_link_t **element, void *extra)
{
  free((*element)->value.ioopm_item);
}

static void free_shelves_in_stock(ioopm_link_t **element, void *extra)
{
  free((*element)->value.ioopm_shelf->shelf_name);
  free((*element)->value.ioopm_shelf);
}

static void free_carts_apply_func(elem_t key, elem_t *value, void *extra)
{
  ioopm_linked_apply_to_all(value->ioopm_cart->basket, free_items_in_cart, NULL);
  ioopm_linked_list_destroy(value->ioopm_cart->basket);
  free(value->ioopm_cart);
}

static void clear_stock(ioopm_list_t *stock)
{
  elem_t element;
  for (int i = 0; i < stock->list_size; ++i)
    {
      element = ioopm_linked_list_get(stock, i);
      free(element.ioopm_shelf->shelf_name);
      free(element.ioopm_shelf);
    }
}


static void destroy_merch(ioopm_database_t *db, merch_t *merch) //Note: remember to remove from hash table as well!
{
  ioopm_list_t *stock = merch->stock;
  ioopm_link_t *current_location = stock->first;
  
  while (current_location != NULL)  //1. remove from shelves_ht hash table
    {
      elem_t shelf = str_elem(current_location->value.ioopm_shelf->shelf_name);
      
      ioopm_hash_table_remove(db->shelves_ht, shelf);
      current_location = current_location->next;
    }
  clear_stock(stock);
  ioopm_linked_list_destroy(stock); //2. free list of shelves_ht
  free(merch->name);
  free(merch->desc);
  free(merch);
}

static void free_merch_apply_func(elem_t key, elem_t *value, void *db)
{  
  destroy_merch(db, value->ioopm_merch);  
}

void ioopm_destroy_database(ioopm_database_t *db)
{
  ioopm_hash_table_apply_to_all(db->carts, free_carts_apply_func, NULL);
  ioopm_hash_table_destroy(db->carts);
  ioopm_hash_table_apply_to_all(db->merch_ht, free_merch_apply_func, db);
  ioopm_hash_table_destroy(db->merch_ht);
  ioopm_hash_table_destroy(db->shelves_ht);

  free(db);
}



/////////////////////////////////// ADD /////////////////////////////////////////////////
void ioopm_add_merch(ioopm_database_t *db) 
{
  char *new_name = ask_question_string("Name of merchandise: ");
  option_t result = ioopm_hash_table_lookup(db->merch_ht, str_elem(new_name)); //Check if a merch of that name already exists
  
  if (!result.success)
    {      
      merch_t *merch = calloc(1, sizeof(merch_t));
      merch->name = new_name;
      char *new_desc = ask_question_string("Description of merchandise: ");
      merch->desc = new_desc;
      merch->price_per_unit = ask_question_int("Price of merchandise: ");
      merch->available_amount = 0;
      merch->stock = ioopm_linked_list_create(equality_function_str);

      ioopm_hash_table_insert(db->merch_ht, str_elem(merch->name), merch_elem(merch));
    }
  else
    {
      printf("%s already exists!\n", new_name);
      free(new_name);
    }
}


////////////////////////////////////// LIST /////////////////////////////////////////////

static bool ask_continue_listing()
{
  char *answer = NULL;
  bool cont = false;
  bool quit = false;

  do //asks to continue listing merchandise after printing 20 items
    {
      answer = ask_question_string("[C]ontinue listing? [Y/N]: ");

      cont = (toupper(answer[0]) == 'Y' && (strlen(answer) == 1));
      quit = (toupper(answer[0]) == 'N' && (strlen(answer) == 1));
      
      free(answer);
    }
  while (!cont && !quit);

  return quit;
}



static int strcompare(const void *merch1, const void *merch2)
{
  return strcmp(*(char * const *) merch1, *(char * const *) merch2);
}


static char **sort_list(ioopm_list_t *list) //returns a sorted list of merch names
{
  ioopm_link_t *current_link = list->first;
  
  size_t size = ioopm_linked_list_size(list);
  char **merch_list = calloc(size, sizeof(char *));
  
  for(int i = 0; i < size; ++i)
    {
      merch_list[i] = current_link->value.ioopm_str;
      current_link = current_link->next;
    }

  qsort(merch_list, size, sizeof(char *), strcompare);

  return merch_list;
}


void ioopm_list_merch(ioopm_database_t *db)
 {
   ioopm_list_t *names = ioopm_hash_table_keys(db->merch_ht); // generate list
   size_t size = ioopm_linked_list_size(names);
   char **merch_list = sort_list(names);             // sort list

   if(size == 0)
     {
       puts("\nNo merchandise in database!");
     }
   
   for (int i = 0; i < size; ++i)
     {
       if (((i+1) % 20) == 0)
	 {
	   bool quit = ask_continue_listing();
	   if (quit) break;
	 }
       printf("%d. %s\n", i+1, merch_list[i]); //i+1: remember, arrays start a 0
     }
   printf("\n");
   free(merch_list);
   ioopm_linked_list_destroy(names);
 }

static option_t choose_merch(ioopm_database_t *db) //choose merch from list with number
{  
  ioopm_list_merch(db);

  int result = 0;
  if(db->merch_ht->size != 0)
    {
      do
	{
	  result = ask_question_int("Choose merchandise by number: ");
	}
      while((1 > result) || (result > db->merch_ht->size));
    

      ioopm_list_t *names = ioopm_hash_table_keys(db->merch_ht); // generate list.
   
      char **merch_list = sort_list(names);
      char *chosen_merch = merch_list[result-1]; //choose value from sorted list

      option_t merch = ioopm_hash_table_lookup(db->merch_ht, str_elem(chosen_merch));

      free(merch_list);
      ioopm_linked_list_destroy(names);
      return merch;
    }
  return Failure();
}

///////////////////////////////// REMOVE ////////////////////////////////////////////

static bool ask_to_continue()
{
  char *answer = NULL;
  bool cont = false;
  bool quit = false;

  do //asks to continue listing merchandise after printing 20 items
    {
      free(answer);
      answer = ask_question_string("[C]ontinue? [Y/N]: ");

      cont = (toupper(answer[0]) == 'Y' && (strlen(answer) == 1));
      quit = (toupper(answer[0]) == 'N' && (strlen(answer) == 1));
    }
  while (!cont && !quit);

  free(answer);
  return !quit;
}


void ioopm_remove_merch(ioopm_database_t *db)
{ 
  option_t result = choose_merch(db);

  if(Successful(result))
    {
      bool cont = ask_to_continue();
      if (!cont) return;
      
      merch_t *merch = result.value.ioopm_merch;
      ioopm_hash_table_remove(db->merch_ht, str_elem(merch->name));      
      destroy_merch(db, merch);

    }
}


///////////////////////////////// EDIT ///////////////////////////////////////////

static merch_t *rename_merch(char *name, merch_t *merch)
{
  merch_t *new_merch = calloc(1, sizeof(merch_t));
  new_merch->name = name; 
  new_merch->desc = merch->desc;
  new_merch->price_per_unit = merch->price_per_unit;
  new_merch->available_amount = merch->available_amount;
  new_merch->stock = merch->stock;
  return new_merch; 
}


static merch_t *insert_merch(ioopm_database_t *db, char *new_name, merch_t *merch)
{
  merch_t *new_merch = rename_merch(new_name, merch);

  ioopm_hash_table_remove(db->merch_ht, str_elem(merch->name));
  free(merch->name);
  ioopm_linked_apply_to_all(merch->stock, free_shelves_in_stock, NULL);
  free(merch);
  ioopm_hash_table_insert(db->merch_ht, str_elem(new_name), merch_elem(new_merch));    
  return new_merch;
}


static void print_item(char *name, char *desc, int price)
{
  printf("\nName: %s\n", name);
  printf("Description: %s\n", desc);
  printf("Price: %d\n\n", price);
}

static void event_loop_edit(ioopm_database_t *db, merch_t *merch)
{
  char *answer = NULL;
  char *new_name = NULL;
  option_t lookup_result;
  do
    {
      free(answer);
      answer = ask_question_string("Edit [n]ame\nEdit [d]escription\nEdit [p]rice\n[Q]uit\n");
      if(strlen(answer) == 1)
	{
	  if(toupper(answer[0]) == 'N')
	    {
	      new_name = ask_question_string("New name of merchandise: ");
	      lookup_result = ioopm_hash_table_lookup(db->merch_ht, str_elem(new_name));

	      bool cont = ask_to_continue();
	      if (cont)
		{
		  if (Unsuccessful(lookup_result))
		    {
		      merch = insert_merch(db, new_name, merch);
		      merch->name = new_name;
		      print_item(merch->name, merch->desc, merch->price_per_unit);
		    }
		  else
		    {
		      printf("%s already exists!\n", new_name);
		      free(new_name);
		    }
		}
	      else
		{
		  free(new_name);
		}
	    }
	  if(toupper(answer[0]) == 'D')
	    {
	      char *new_desc = ask_question_string("New description of merchandise: ");	    
	      bool cont = ask_to_continue();
	      if (cont)
		{
		  free(merch->desc);
		  merch->desc = new_desc;
		  print_item(merch->name, merch->desc, merch->price_per_unit);
		}
	      else
		{
		  free(new_desc);
		}
	    }
	  if(toupper(answer[0]) == 'P')
	    {
	      int new_price = ask_question_int("New price of merchandise: ");
	      bool cont = ask_to_continue();
	      if (cont)
		{
		  merch->price_per_unit = new_price;
		  print_item(merch->name, merch->desc, merch->price_per_unit);
		}
	    }
	}
    }
  while(toupper(answer[0]) != 'Q' || strlen(answer) != 1);

  free(answer);
       
}

void ioopm_edit_merch(ioopm_database_t *db)
{
  option_t result = choose_merch(db);
  if(Successful(result))
    {
      merch_t *merch = result.value.ioopm_merch;
      print_item(merch->name, merch->desc, merch->price_per_unit);
      event_loop_edit(db, merch);
    }
}


///////////////////////////////// SHOW STOCK /////////////////////////////////////

void ioopm_show_stock(ioopm_database_t *db)
{

  option_t result = choose_merch(db);
  if(Successful(result))
    {
      merch_t *merch = result.value.ioopm_merch;
      ioopm_link_t *current_link = merch->stock->first;
      while (current_link != NULL)
	{
	  char *shelf = current_link->value.ioopm_shelf->shelf_name;
	  int amount = current_link->value.ioopm_shelf->amount;
	
	  printf("\nShelf: %s, Amount: %d", shelf, amount); 
	  current_link = current_link->next;
	}
      printf("\n");
    }
}

////////////////////////////////// REPLENISH //////////////////////////////////////////////

static int shelf_value(char *shelf_name)
{
  int value1 = shelf_name[0];
  int value2 = shelf_name[1];
  int value3 = shelf_name[2];

  return (value1*100 + value2*10 + value3);
}
static int shelf_compare(ioopm_list_t *stock, char *shelf_name)
{
  int counter = 0;
  ioopm_link_t *current_loc = stock->first;
  if (current_loc != NULL)
    {
      int current_shelf_value = shelf_value(current_loc->value.ioopm_shelf->shelf_name);
      int new_shelf_value = shelf_value(shelf_name);
  
      while(current_loc != NULL && new_shelf_value > current_shelf_value)
	{
	  current_shelf_value = shelf_value(current_loc->value.ioopm_merch->name);
	  current_loc = current_loc->next;
	  ++counter;
	}
    }
  return counter;
}


void ioopm_replenish_stock(ioopm_database_t *db)
{      
  option_t result = choose_merch(db);
  if(Successful(result))
    {
      merch_t *merch = result.value.ioopm_merch;
      shelf_t *new_shelf = calloc(1, sizeof(shelf_t));
      new_shelf->shelf_name = ask_question_shelf("Storage location: ");
      new_shelf->amount = ask_question_int("Amount: ");

      merch->available_amount = merch->available_amount + new_shelf->amount;
      
      int index = shelf_compare(merch->stock, new_shelf->shelf_name);  //Index shelves in alphanumerical order
  
      ioopm_linked_list_insert(merch->stock, index, shelf_elem(new_shelf));
      ioopm_hash_table_insert(db->shelves_ht, str_elem(new_shelf->shelf_name), merch_elem(merch));
    }
}

/////////////////////////////// CREATE CART ////////////////////////////////

cart_t *ioopm_create_cart(ioopm_database_t *db)
{
  cart_t *cart = calloc(1, sizeof(cart_t));
  cart->id = db->id_counter;
  ++db->id_counter;
  cart->basket = ioopm_linked_list_create(equality_function_merch);
  ioopm_hash_table_insert(db->carts, int_elem(cart->id), cart_elem(cart));
  printf("\nCart ID: %u\n", cart->id);
  return cart;
}
    
static unsigned int total_price(cart_t *cart)
{
  unsigned int total = 0;
  ioopm_link_t *current_link = cart->basket->first;
  while (current_link != NULL)
    {
      int amount = current_link->value.ioopm_item->amount;
      int price_per_unit = current_link->value.ioopm_item->price_per_unit;
      
      total = total + (amount * price_per_unit);
      current_link = current_link->next;
    }
  return total;
}

static void print_cart(cart_t *cart)
{
  char *name = NULL;
  unsigned int amount = 0;
  unsigned int price = 0;
  unsigned int total = total_price(cart);
  
  printf("\nCart ID: %u\n", cart->id);

  ioopm_link_t *current_link = cart->basket->first;
  while(current_link != NULL)
    {
      name = current_link->value.ioopm_item->name;
      amount = current_link->value.ioopm_item->amount;
      price = current_link->value.ioopm_item->price_per_unit;

      printf("-%s: %u x %u SEK\n", name, amount, price);
      
      current_link = current_link->next;
    }
  printf("Total price: %u SEK\n\n", total);

}

//////////////////////////////// REMOVE CART ////////////////////////////////// 

//Note: array_from_list_id and list_id are modified versions of ioopm_list_merch
static unsigned int *array_from_list_id(ioopm_list_t *list) //returns a sorted list of id
{
  ioopm_link_t *current_link = list->first;
  
  size_t size = ioopm_linked_list_size(list);
  
  unsigned int *id_list = calloc(size, sizeof(unsigned int));
  
  for(int i = 0; i < size; ++i)
    {
      id_list[i] = current_link->value.ioopm_u_int;
      current_link = current_link->next;
    }
  return id_list;
}


static void list_id(ioopm_database_t *db) //
 {
   ioopm_list_t *id = ioopm_hash_table_keys(db->carts); // generate list
   size_t size = ioopm_linked_list_size(id);
   unsigned int *id_list = array_from_list_id(id);

   if(size == 0)
     {
       puts("\nNo carts in database!");
     }
   
   for (int i = 0; i < size; ++i)
     {
       if (((i+1) % 21) == 0)
	 {
	   bool quit = ask_continue_listing();
	   if (quit) break;
	 }
       printf("Cart ID: %u\n", id_list[i]);
     }
   printf("\n");
   ioopm_linked_list_destroy(id);
   free(id_list);
 }


static option_t choose_cart(ioopm_database_t *db)
{
  list_id(db);
    
  if(db->carts->size != 0)
    {
      int result = 0;
      option_t cart;
      
      do
	{
	  result = ask_question_int("Choose cart by cart ID: ");
	  cart = ioopm_hash_table_lookup(db->carts, int_elem(result));
      
	}
      while((result < 0) || (result > db->id_counter) || Unsuccessful(cart));
      return cart;
    }
  
  return Failure();
}

static void delete_cart(ioopm_database_t *db, cart_t *cart)
{
  ioopm_linked_apply_to_all(cart->basket, free_items_in_cart, NULL);
  ioopm_hash_table_remove(db->carts, unsigned_elem(cart->id));
  ioopm_linked_list_destroy(cart->basket);
  free(cart);
}

void ioopm_remove_cart(ioopm_database_t *db)
{
  option_t result = choose_cart(db);

  if (Successful(result))
    {
      bool cont = ask_to_continue();
      if (!cont) return;
      cart_t *cart = result.value.ioopm_cart;
      delete_cart(db, cart);
    }
}

///////////////////////////// ADD TO CART //////////////////////////////////

static item_t *item_create(merch_t *merch, int amount)
{
  item_t *item = calloc(1, sizeof(item_t));
  item->name = merch->name;
  item->amount = amount;
  item->price_per_unit = merch->price_per_unit;
  item->pointer = merch;
  return item;
} 

// TODO: Print available amount
void ioopm_add_to_cart(ioopm_database_t *db)
{
  option_t result_carts = choose_cart(db);
  if (Successful(result_carts))
    {
      cart_t *cart = result_carts.value.ioopm_cart;
      option_t result_merch = choose_merch(db);
      merch_t *merch = result_merch.value.ioopm_merch;

      if(Successful(result_merch))
	{      
	  if(merch->available_amount != 0)
	    {
	      int amount = 0;
	      do
		{
		  amount = ask_question_int("Amount: ");
		}
	      while((amount < 0) || (merch->available_amount < amount));
  
	      item_t *item = item_create(merch, amount);

  	      merch->available_amount = merch->available_amount - amount;

	      ioopm_linked_list_insert(cart->basket, 0, item_elem(item));
	    }
	  else
	    {
	      printf("\n%s not available in stock!\n", merch->name);
	    }
	}
    }
}
/////////////////////////// REMOVE FROM CART //////////////////////////////////

 static option_t choose_item_in_cart(cart_t *cart)
 {
   ioopm_link_t *current_link = cart->basket->first;
   if(current_link == NULL) return Failure();
   
   print_cart(cart);

   char *wanted_item = ask_question_string("Name of item: ");
   char *current_item = current_link->value.ioopm_item->name;

   while(current_link != NULL && strcmp(current_item, wanted_item) != 0)
     {
       current_item = current_link->value.ioopm_item->name; 
       current_link = current_link->next;
     }
   
   if(strcmp(current_item, wanted_item) == 0)
     {
       elem_t item = current_link->value;
       free(wanted_item);
       return Success(item);
     }
   else
     {
       printf("\n%s not in cart.\n", wanted_item);
       free(wanted_item);
       return Failure();
     }
   
}


void ioopm_remove_from_cart(ioopm_database_t *db)
{
  option_t result_carts = choose_cart(db);
  if (Successful(result_carts))
    {
      cart_t *cart = result_carts.value.ioopm_cart;
      
      option_t result = choose_item_in_cart(cart);

      if(Successful(result))
	{
	  merch_t *merch = result.value.ioopm_item->pointer;      
	  ioopm_link_t *current_link = cart->basket->first;
	  int index = 0;
	  while(current_link->value.ioopm_item->name != merch->name && current_link != NULL) 
	    {
	      current_link = current_link->next;
	      ++index;
	    }

	  if(current_link != NULL)
	    {
	      item_t *item = current_link->value.ioopm_item;
  
	      int amount = 0;
	      do
		{
		  amount = ask_question_int("Amount: ");
		}
	      while((amount < 1) || ( amount > item->amount));

	      bool cont = ask_to_continue();
	      if (!cont) return;

	      item->amount = item->amount - amount;
 
	      if(item->amount == 0)
		{
		  ioopm_linked_list_remove(cart->basket, index); 
		  free(item);
		}

	      merch->available_amount = merch->available_amount + amount;
	    }
	  else
	    {
	      printf("%s not in cart %d.\n", merch->name, cart->id);
	    }
	}
    }
}
//////////////////////////// CALCULATE COST ///////////////////////////////////
void ioopm_calculate_cost(ioopm_database_t *db)
{
  option_t result = choose_cart(db);
  if (Successful(result))
    {
      cart_t *cart = result.value.ioopm_cart;
      print_cart(cart);
    }
}
////////////////////////////// CHECKOUT ///////////////////////////////////////

static void remove_from_stock(ioopm_link_t *current_link)
{
  ioopm_link_t *current_shelf = current_link->value.ioopm_item->pointer->stock->first;
  while(current_shelf->value.ioopm_shelf->amount == 0)
    {
      current_shelf = current_shelf->next;
    }
  --current_link->value.ioopm_item->amount;
  --current_link->value.ioopm_item->pointer->stock->first->value.ioopm_shelf->amount;
}

void ioopm_checkout(ioopm_database_t *db)
{  
  option_t result = choose_cart(db);
  
  if (Successful(result))
    {
      cart_t *cart = result.value.ioopm_cart;
      ioopm_link_t *current_link = cart->basket->first;

      option_t exists;
      for(int i = 0; i < cart->basket->list_size; ++i)
	{
	  elem_t item = ioopm_linked_list_get(cart->basket, i);
	  exists = ioopm_hash_table_lookup(db->merch_ht, str_elem(item.ioopm_item->name));

	  if(Unsuccessful(exists))
	    {
	      puts("An item in your cart no longer exists. Aborting checkout.");
	      
	      break;
	    }
	}
      
      if(Successful(exists))
	{
	  print_cart(cart);
	  bool cont = ask_to_continue();
	  if (!cont) return;
	
	  while(current_link != NULL)
	    {
	      while(current_link->value.ioopm_item->amount > 0)
		{
		  remove_from_stock(current_link);
		}
	      current_link = current_link->next;
	    }
	  
	}
      delete_cart(db, cart);
    }
}
