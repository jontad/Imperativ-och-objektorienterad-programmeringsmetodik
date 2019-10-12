#include <stdio.h>
#include "utils.h"
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


/// #include <stdio.h>
//Hjälpfunktion till ask_question_string
bool not_empty(char *str)
{
  return strlen(str) > 0;
}


int print(char argv[])
{
  int counter = 0;
  {
    do
      {
	putchar(argv[counter]);
	counter = counter + 1;
      }
    while (argv[counter] != '\0');
      }
  return 0;
}


int println(char argv[])
{
  {
    print(argv);
    putchar('\n');
  }
  return 0;
}



bool is_number(char *str)
{
  bool a = true; // Vi utgår från att det är ett nummer, och försöker motbevisa det.
  for (int i = 0; i < (strlen(str)); ++i)
    
    {
      //  printf("hej");
      if (
	  ((isdigit(str[i]) == false) && (str[i] != '-')) ||
	  ((strlen(str) == 1) && (str[i] == '-'))
	  ) {
	a = false;
      } //letar efter fall som inte är nummer/'-'
    }

  return a;
}

void clear_buf()
{
  int c;
  do
    {
      c = getchar();
    }
  while (c != '\n' && c != EOF);
}




int read_string(char *buf, int buf_siz)
{
  int counter = 0;
  int b;
  {
    do
      {
	b = getchar();
	if (b != '\n')
	  {
	    buf[counter] = b;
	    counter = counter + 1;
	  }
	else
	  {
	    buf[counter] = '\0';
	  }	
      }
    
    while ((counter < (buf_siz)) && (b != '\n'));

     if (counter >= buf_siz)
      {
	clear_buf();
      }
     
    if (buf[counter] != '\0')
      {
	buf[counter] = '\0';
	clear_buf();
      }
    
    return counter;
  }
  
}


answer_t ask_question(char *question, check_func check, convert_func convert)
{
  int buf_size = 255;
  char buf[buf_size];
  do
    {
      printf("%s\n", question);
      read_string(buf, buf_size);
    }
  while ((!check(buf)) || (buf[0] == '\0'));
  answer_t answer = convert(buf);
  return answer; 
}


int ask_question_int(char *question)
{
  answer_t answer = ask_question(question, is_number, (convert_func) atoi);
  return answer.int_value; // svaret som ett heltal
}


char *ask_question_string(char *question)
{
  return ask_question(question, not_empty, (convert_func) strdup).string_value;
}


char *ask_question_shelf(char *question)
{
  answer_t answer = ask_question(question, is_shelf, (convert_func) strdup);
  return answer.string_value;
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
  
  for(int i = 0; *firstword != '\0'; ++i)
    {
      buf[counter] = *firstword;
      ++firstword;
      ++counter;
    }
  
  buf[counter] = '-';
  ++counter;
  
  for(int i = 0; *secondword != '\0'; ++i)
     {
       buf[counter] = *secondword;
       ++secondword;
       ++counter;
     }
   
    buf[counter] = ' ';
    ++counter;

    for(int i = 0; *thirdword != '\0'; ++i)
      {  
	buf[counter] = *thirdword;
	++thirdword;
	++counter;
      }
    
  buf[counter] = '\0';

  return strdup(buf);
}



static void print_menu(void)
{
  printf("[L]ägga till en vara\n[T]a bort en vara\n[R]edigera en vara\nÅn[g]ra senaste ändringen\nLista [h]ela varukatalogen\n[A]vsluta\n");

}


static bool answer_valid(char *letter)
{
  char *validLetter = "LTRGHA";
  bool a = false;

  if (strlen(letter) > 1)
    {
      a = false;
    }

  else
    {
      for(int i = 0; i < strlen(validLetter) ; ++i)
	{
	  if (toupper(letter[0]) == validLetter[i])
	    {
	      a = true; break;
	    }
	}
    }
  
  return a;
}




char ask_question_menu(char *question)
{
  print_menu();

  char *answ;
 do
   {
     answ = ask_question_string(question);
   }
 while (!answer_valid(answ));

 char answer = toupper(answ[0]);
 return answer;
}





