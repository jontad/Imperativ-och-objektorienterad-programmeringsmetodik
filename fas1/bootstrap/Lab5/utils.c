#include <stdio.h>
#include "utils.h"
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>


/// Hjälpfunktion till ask_question_string
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


/*
int main(void)
{
    int buf_siz = 10;
    int read = 0;
    char buf[buf_siz];

    puts("Läs in en sträng:");
    read = read_string(buf, buf_siz);
    printf("'%s' (%d tecken)\n", buf, read);

    puts("Läs in en sträng till:");
    read = read_string(buf, buf_siz);
    printf("'%s' (%d tecken)\n", buf, read);

    return 0;
}

*/










