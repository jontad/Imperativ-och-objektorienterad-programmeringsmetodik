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

static merch_t location_of_merch(int no_merch)
{
  merch_t merch;

  int length = snprintf(NULL, 0, "%d", no_merch);
  char *buf = calloc(1, length+3);

  int which_merch_location = no_merch/100;
  char merch_loc = 'A' + which_merch_location;
   
  
  if(no_merch < 10) merch.Location = snprintf(buf, length+3, "%s%d","A0", no_merch);
  else return merch.Location = snprintf(buf, length+2, "%c%d", merch_loc, no_merch);

  return merch;
  Free(buf);
}

static merch_t add_merch_aux()
{
  merch_t merch;
  merch.Name = ask_question_string("Name:");
  merch.Desc = ask_question_string("Description:");
  merch.Price = ask_question_int("Price:");
 
}

int ioopm_add_merch(merch_t *merch, int no_merch)
{



}
