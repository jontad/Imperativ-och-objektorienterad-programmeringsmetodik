#include <stdio.h>

#include "backend.h"
#include "common.h"
#include "utils.h"

/////////////////////////////////EVENT_LOOP/////////////////////////////////


void event_loop(ioopm_database_t *db)
{
  char *temp = NULL;
  char answer = ' ';
  while (answer != 'Q')
    {
      free(temp);
      temp = ask_question_menu("");
      answer = toupper(temp[0]);
      
      if (answer == 'A') //Add merch
	{
	  ioopm_add_merch(db); 
	}
      if (answer == 'L') //List merch
	{
	  ioopm_list_merch(db);
	}
      if (answer == 'R') //Remove merch
	{
	  ioopm_remove_merch(db);
	}
      if (answer == 'E') //Edit merch
	{
	  ioopm_edit_merch(db);
	}
      if (answer == 'S') //Show stock
	{
	  ioopm_show_stock(db);
	}
      if (answer == 'P')//Replenish stock
	{
	  ioopm_replenish_stock(db);
	}
      if (answer == 'C') //Create cart
	{
	  ioopm_create_cart(db);
	}
      if (answer == 'M') //Remove cart
	{
	  ioopm_remove_cart(db);
	}
      if (answer == 'T') //Add to cart
	{
	  ioopm_add_to_cart(db);
	}
      if (answer == 'F') //Remove from cart
	{
	  ioopm_remove_from_cart(db);
	}
      if (answer == 'O') //Calculate cost
	{
	  ioopm_calculate_cost(db);
	} 
      if (answer == 'H') //Checkout
	{
	  ioopm_checkout(db);
	}
       
    }
  free(temp);
  return;
}



///////////////////////////////MAIN/////////////////////////////////
int main()
{
  ioopm_database_t *db = ioopm_create_database();
  event_loop(db);
  ioopm_destroy_database(db);
  return 0;
}
