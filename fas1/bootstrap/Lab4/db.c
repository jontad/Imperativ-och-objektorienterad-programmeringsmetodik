#include <stdio.h>
#include "utils.h"
#include <ctype.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

struct item
{
char *Name;
char *Desc;
int Price;
char *Shelf;
};

typedef struct item item_t;

item_t make_item(char *input_name, char *input_desc, int input_price, char *input_shelf)
{
  item_t item = {item.Name = input_name, item.Desc = input_desc, item.Price = input_price, item.Shelf = input_shelf};
    return item;
}


bool is_shelf(char *string)
{
  bool a = true;
  if (isalpha(string[0]) == false || strlen(string) <= 1)
    {
      a = false;
    }
  else
    {
      for(int i = 1; i < strlen(string); ++i)
	{
	  if (isdigit(string[i]) == false)
	    {
	      a = false;
	    }
	  
	}
    }
  return a;
}


char *ask_question_shelf(char *question)
{
  answer_t answer = ask_question(question, is_shelf, (convert_func) strdup);
  return answer.string_value;
  
}


item_t input_item()
{
  item_t item;
  item.Name = ask_question_string("Name:");
  item.Desc = ask_question_string("Description:");
  item.Price = ask_question_int("Price:");
  item.Shelf = ask_question_shelf("Shelf:");
  return item;
}


void print_item(item_t *input)
{
  int kronor = ((input->Price) / 100);
  int ore = ((input->Price) % 100);
  printf("Name:%s\nDesc:%s\nPrice:%d.%d SEK\nShelf:%s\n", input->Name, input->Desc, kronor, ore,  input->Shelf);
}


char *magick(char *arr1[], char *arr2[], char *arr3[], int n)
{
  int counter = 0;
  char buf[255];

  int r1 = rand() % n;
  int r2 = rand() % n;
  int r3 = rand() % n;

  char *firstword = arr1[r1];
  char *secondword = arr2[r2];
  char *thirdword = arr3[r3];
  
  for(; *firstword != '\0';)
    {
      buf[counter] = *firstword;
      ++firstword;
      ++counter;
    }
  
  buf[counter] = '-';
  ++counter;
  
  for(; *secondword != '\0'; )
     {
       buf[counter] = *secondword;
       ++secondword;
       ++counter;
     }
   
    buf[counter] = ' ';
    ++counter;

    for(; *thirdword != '\0'; )
      {  
	buf[counter] = *thirdword;
	++thirdword;
	++counter;
      }
    
  buf[counter] = '\0';
  return strdup(buf);

}

void list_db(item_t *items, int no_items)
{
  for (int i = 1; i <= no_items; ++i)
    {
      printf("%d. %s\n", i, items[i-1].Name);
    }
}



void edit_db(item_t *items)
{
  int number = 0;
  do
  {
    number = ask_question_int("Item to edit:");
  }
  while (number < 1 || number > 16);
  print_item(&items[number-1]);
  items[number-1] = input_item();
}

void print_menu()
{
  printf("[L]ägga till en vara\n[T]a bort en vara\n[R]edigera en vara\nÅn[g]ra senaste ändringen\nLista [h]ela varukatalogen\n[A]vsluta\n");
}


	
int main(int argc, char *argv[])
{
  
  srand(time(NULL));
  char *array1[] = { "Laser",        "Polka",    "Extra" }; 
  char *array2[] = { "förnicklad",   "smakande", "ordinär" }; 
  char *array3[] = { "skruvdragare", "kola",     "uppgift" }; 

  if (argc < 2)
  {
    printf("Usage: %s number\n", argv[0]);
  }
  else
  {
    item_t db[16]; // Array med plats för 16 varor
    int db_siz = 0; // Antalet varor i arrayen just nu

    int items = atoi(argv[1]); // Antalet varor som skall skapas

    if (items > 0 && items <= 16)
    {
      for (int i = 0; i < items; ++i)
      {
        // Läs in en vara, lägg till den i arrayen, öka storleksräknaren
        item_t item = input_item();
        db[db_siz] = item;
	++db_siz;
      }
    }
    else
    {
      puts("Sorry, must have [1-16] items in database.");
      return 1; // Avslutar programmet!
    }

    for (int i = db_siz; i < 16; ++i)
      {
        char *name = magick(array1, array2, array3, 3); 
        char *desc = magick(array1, array2, array3, 3); 
        int price = random() % 200000;
        char shelf[] = { random() % ('Z'-'A') + 'A',
                         random() % 10 + '0',
                         random() % 10 + '0',
                         '\0' };
        item_t item = make_item(name, desc, price, shelf);

        db[db_siz] = item;
        ++db_siz;
      }

     // Skriv ut innehållet
       list_db(db, db_siz);
       edit_db(db);
       list_db(db, db_siz);

  }
  return 0;
}

