#include <stdio.h>
#include "utils.h"
#include <ctype.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

#define Free(ptr) {free(ptr); ptr = NULL;}



struct merchandise
{
  char *Name;
  char *Desc;
  int Price;
  char *Location;
  int Quantity;
};


merch_t input_merch()
{
  merch_t merch;
  merch.Name = ask_question_string("Name:");
  merch.Desc = ask_question_string("Description:");
  merch.Price = ask_question_int("Price:");
  merch.Location = ask_question_shelf("Location:");
  merch.Quantity = ask_question_int("Quantity:");
  
  return merch;
}

static char *location_of_merch(int no_merch)
{
  int length = snprintf(NULL, 0, "%d", no_merch);
  char *buf = calloc(1, length+3);

  merch_t merch;
  int which_merch_location = no_merch/100;
  char merch_location = 'A' + which_merch_location;
   
  
  if(no_merch < 10)
    {
      snprintf(buf, length+3, "%s%d","A0", no_merch);
      merch.Location = buf;
    }

  else if(merch_location > 90)
    {
      merch.Location = "No room left in stock";
    }

  else
    {
      snprintf(buf, length+2, "%c%d", merch_location, no_merch);
      merch.Location = buf;
    }
  
  Free(buf);

  return merch.Location;  
}

static merch_t add_merch_aux(char *name, int no_items)
{
  merch_t merch;
  merch.Name = name;
  merch.Desc = ask_question_string("Description:");
  merch.Price = ask_question_int("Price:");
  merch.Location = location_of_merch(no_items);
  merch.Quantity = 1;

  return merch;
}

static bool name_compare(merch_t *merch, char *name, int no_merch)
{
 for(int i = 0; i < no_merch; ++i)
    {
      if(strcmp(name, merch[i].Name) == 0) return true;
    }
 return false;
}

int ioopm_add_merch(merch_t *merch, int no_merch)
{
  char *name = ask_question_string("Name of merchandise:");
  if(input_compare(merch, name, no_items))  return no_items;
  
  
  
  merch[no_merch] = add_merch_aux(name, no_merch);
  ++no_merch;

  return no_merch;
}

void ioopm_list_merch(merch_t *merch, int no_merch)
{
  for(int i = 1; i  <= no_merch; ++i)
    {

      printf("%d. %s\n",i,merch[i-1].Name);
      
    }
  printf("\n");
}

