#include "utils.h"
#include <stdio.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>


#define Free(ptr) {free(ptr); ptr = NULL;}

#define No_Buckets 17
#define Load 1


///////////////////// STRUCTS ////////////////////////////



struct hash_table //ioopm_hash_table_t 
{
  ioopm_hash_function hash; /// function to hash key
  ioopm_eq_function eq_key; /// PTR to function that compares keys
  ioopm_eq_function eq_val; /// PTR to function that compares values
  merch_t *buckets;
  int no_buckets;
  float load_factor;        /// how often buckets increases in size
};


struct merchandise //merch_t 
{
  char *name;    //key
  char *desc;
  int price;
  ioopm_list_t *location;  //points to location of first element for key
};

struct list //ioopm_list_t
{
  size_t list_size;        /// amount of entries in ht
  ioopm_eq_function equal; /// PTR to function that compares elements
  ioopm_link_t *first;     /// PTR to first link in ht
  ioopm_link_t *last;      /// PTR to last link in ht
};


struct link //ioopm_link_t
{
  shelf_t value;       /// value for entry
  ioopm_link_t *next; /// PTR to next link
};

struct shelf //shelf_t 
{
  char *shelf;
  int quantity;
};

struct store_db
{
  // merch->name to merch
  ioopm_hash_table_t *merch;

  // location->name to location
  ioopm_hash_table_t *location;
};



////////////////////// MISC FUNCTIONS ////////////////////////////


/// Calculates which shelf merch is put on
static char *which_shelf(shelf_t shelf, int no_merch)
{
  int length = snprintf(NULL, 0, "%d", no_merch);
  char *buf = calloc(1, length+3);

  merch_t merch;
  int which_shelf = no_merch/100;
  char shelf_letter = 'A' + which_shelf;  
  
  if(no_merch < 10)
    {
      snprintf(buf, length+3, "%s%d","A0", no_merch);
      shelf = buf;
    }

  else if(shelf_letter > 90)
    {
      shelf = "No more room in stock";
    }

  else
    {
      snprintf(buf, length+2, "%c%d", shelf_letter, no_merch);
      shelf = buf;
    }
  
  Free(buf);

  return shelf;  
}



static merch_t *merch_create(char *name, char *desc, int price, ioopm_list_t loc)
{
  merch_t *merch = calloc(1, sizeof(merch_t));
  merch->name = name;
  merch->desc = desc;
  merch->price = price;
  merch->location = loc;

  while(merch->location->first != NULL)
    {
      merch->location->first = merch->location->first->next;
    }

  merch->location->first->value->shelf = which_shelf(no_merch);
  ++merch->location->first->value->quantity;
  
  return merch;
}

static void merch_destroy(merch_t *merch)
{
  Free(entry);
}


static merch_t *find_prev_merch_for_name(ioopm_hash_table_t *ht, char *name)
{
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      merch_t *prev_merch = &ht->buckets[i];
      merch_t *merch = &ht->buckets[i+1];

      if(merch == NULL || prev_merch == NULL|| name < merch->name) return prev_merch; //osäker på logiken
    }
  return NULL;
}

/////////////////////////// ADD ///////////////////////////////////


/// Checks if param name already is in stock
static bool merch_compare(merch_t *merch, char *name, int no_merch)
{
  for(int i = 0; i < no_merch; ++i)
    {
      if(strcmp(name, merch[i].Name) == 0) return true;
    }
  return false;
}


/// Adds new merch to stock
int ioopm_add_merch(ioopm_hash_table_t *ht,  int no_merch)
{  
  char *name = ask_question_string("Name of merchandise:");

  merch_t *merch = find_prev_merch_for_name(ht, name)
  
  if(merch != NULL)
    {    
      puts("Merch already in stock");
      return no_merch;
    }
  

  return no_merch;
}



/*
//////////////////// LIST MERCH ////////////////////////////

static int strcompare(const void *merch1, const void *merch2)
{
  return strcmp(*(char * const *) merch1, *(char * const *) merch2);
}


static void sort_merch(merch_t *merch, int no_merch)
{
  char *merch_list[no_merch];
  int counter = 0;
  
  while(counter != no_merch)
    {
      merch_list[counter] = merch[counter].Name;
      ++counter;
    }
  qsort(merch_list, no_merch, sizeof(char *), strcompare);
}


void ask_continue_listing(int no_merch)
{
  char *answer;
  bool cont;
  bool quit;
  
  do
    {
      answer = ask_question_string("Enter C to continue listing items\nEnter Q to quit");

      cont = (toupper(answer[0]) == 'C' || strcmp(answer, "Continue") ==  0 || strcmp(answer, "continue") == 0);
      quit = (toupper(answer[0]) == 'Q' || strcmp(answer,"Quit") == 0 || strcmp(answer,"quit") == 0);
      
      if(quit) break;

      if(cont && no_merch % 20 == 1 )
	{
	  puts("No more items available");
	  break;
	}
    }

  while(strlen(answer) != 1 || !cont);
  
}

void ioopm_list_merch(merch_t *merch, int no_merch)
{
  sort_merch(merch, no_merch); //Not sure if sorted list is used
  
  for(int i = 1; i  <= no_merch; ++i)
    {
      
      printf("%d. %s\n",i,merch[i-1].Name);
      if((i % 20) == 0)
	{
	  ask_continue_listing(no_merch);
	}
      
    }
  printf("\n");
}


////////////////////////// REMOVE ////////////////////////////

void remove_aux(merch_t *merch, int no_merch, int merch_to_remove)
{
  int amount_times = no_merch - merch_to_remove;
  for(int i = amount_times; i != 0; --i)
    {
      merch[merch_to_remove] = merch[merch_to_remove + 1];
      ++merch_to_remove;
    }
}

/// VAD MENAS MED ALL STOCK?
int ioopm_remove_merch(merch_t *merch, int no_merch)
{
  ioopm_list_merch(merch, no_merch);
  int merch_to_remove;
  do
    {
      merch_to_remove = ask_question_int("Which merchandise do you want to remove?") - 1;
    }
  while (merch_to_remove < 0  || merch_to_remove > no_merch);

  remove_aux(merch, no_merch, merch_to_remove);
 
  return --no_merch;
}


///////////////// EDIT ////////////////////////////

merch_t edit_aux(int no_merch)
{
  merch_t merch;
  merch.Name = ask_question_string("Name");
  merch.Desc = ask_question_string("Description:");
  merch.Price = ask_question_int("Price");
  merch.Location = location_of_merch(no_merch); //STUB
  merch.Quantity =  100; //STUB
  return merch;
}

void ioopm_edit_merch(merch_t *merch, int no_merch)
{
  int edit_merch;
  ioopm_list_merch(merch, no_merch);

  do
    {
      edit_merch =  ask_question_int("Which merchandise do you want to edit?");
    }
  while((1 > edit_merch) || (edit_merch > no_merch));

  if(!merch_compare(merch, merch[edit_merch].Name, no_merch))
    {    
      //PRINT_MERCH  
      merch[edit_merch-1] = edit_aux(no_merch);
    }
  
}

/////////////// SHOW STOCK ////////////////////////

void ioopm_show_stock(merch_t *merch, int no_merch)
{
  
}


int eventloop(merch_t *merch, int no_merch)
{
  char answer;
  
  do
    {  
      answer = ask_question_menu("What do you want to do?");
      if (answer == 'L')
	{
	 no_merch =  ioopm_add_merch(merch, no_merch);
	}
      if (answer == 'T')
	{
	  no_merch = ioopm_remove_merch(merch, no_merch);
	}
      if (answer == 'R')
	{
	  puts("Not yet implemented!\n");
	  //edit_db(items, no_items);
	}
      if(answer == 'G')
	{
	  puts("Not yet implemented!\n");
	}
      if (answer == 'H')
	{
	  ioopm_list_merch(merch, no_merch);
	}
      
    }
  while (answer != 'A');
  return 0;
}
*/


