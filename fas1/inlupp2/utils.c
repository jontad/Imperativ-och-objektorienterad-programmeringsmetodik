#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <ctype.h>
#include "utils.h"




bool not_empty(char *str)
{
  return strlen(str) > 0;
}

answer_t ask_question(char *question, check_func check, convert_func convert)
  {
    int buf_siz = 255;
    char buf[buf_siz];
    int num = 0;
    do
      {
        puts(question);
	num = read_string(buf, buf_siz);
      }
    while (!check(buf) || num == 0);
    return convert(buf);
  }


bool is_number(char *str)
{
  int x = 0;
  int length = strlen(str);

  if (str[0] == '-')
    {
      if (length == 1)
	{
	  return false;
	}
      else
	{
	  x++;
	}
    }
  else if (length == 0)
    {
      return false;
    }
  
  for (int i = x; i < length; i++)
    {
      if (isdigit(str[i]) == false)
      {
	return false;
      
      }

    }
  return true;
}

bool is_shelf(char *str)
{
  if ((str[0] >= 'A' && str[0] <= 'Z') || (str[0] >= 'a' && str[0] <= 'z'))
    {
      if(strlen(str) == 3) return (is_number(str+1));
      else return false;
    }
  else
    {
      return false;
    }
}



bool is_menu_input(char *str)
{
  char *check = {"ALRESPCMTFOHUQalrespcmtfohuq"};
  if (strlen(str) == 1)
    {
      for (int i = 0 ; check[i] != '\0' ; i++)
	{
	  if (str[0] == check[i])
	    {
	      return true;
	    }
	}
    }
  return false;
}




int ask_question_int(char *question)
  {
    answer_t answer = ask_question(question, is_number, (convert_func) atoi);
    return answer.int_value; // svaret som ett heltal
  }


int read_string(char *buf, int buf_siz)
{
  int i = 0;
  char c = ' ';
  do
    {
      c = getchar();
      buf[i] = c;
      i++;
    }
  while (i < (buf_siz - 1) && c != '\n' && c != EOF);
  i --;
  buf[i] = '\0';
  return i;
}


void print_menu()
{
  printf("\n[A]dd merchandise\n[L]ist merchandise\n[R]emove merchandise\n[E]dit merchandise\n[S]how stock\nRe[p]lenish stock\n[C]reate cart\nRe[m]ove cart\nAdd [t]o cart\nRemove [f]rom cart\nCalculate c[o]st\nC[h]eckout\n[Q]uit\n");
}

char *convert_string(char *str)
{
  char *result = strdup(str);
  return result;
}

char *ask_question_string(char *question)
{
  char *result = ask_question(question, not_empty, (convert_func) strdup).string_value;
  return result;  
}


char *ask_question_shelf(char *question)
{
  char *result = ask_question(question, is_shelf, (convert_func)strdup).string_value;
  return result;
}


char *ask_question_menu(char *question)
{
  print_menu();
  char *result = ask_question(question, is_menu_input, (convert_func)strdup).string_value;
  return result;
}


int print(char *str)
{
  char *end = str;
  while (*end != '\0')
    {
      putchar(*end++);
    }
  return 0;
}


int println(char *str)
{
  char *end = str;
  while (*end != '\0')
    {
      putchar(*end++);
    }
  putchar('\n');
  return 0;
}
