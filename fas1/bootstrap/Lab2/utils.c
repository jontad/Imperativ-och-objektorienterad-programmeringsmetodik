#include <stdio.h>
#include "utils.h"
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

bool is_number(char *str)
{
  bool a = true; // Vi utgår från att det är ett nummer, och försöker motbevisa det.
  for (int i = 0; i <= (strlen(str) - 1); ++i)
    
    {
      if (
	  ((isdigit(str[i]) == false) && (str[i] != '-')) ||
	  ((strlen(str) == 1) && (str[i] == '-'))
	  ) {
	a = false;
      } //letar efter fall som inte är nummer/'-'
    }

  return a;
}


int ask_question_int(char *question)
{

  int result = 0;
  int conversions = 0;  
  do
    {
      printf("%s\n", question);
      conversions = scanf("%d", &result);
      int c;
      do
        {
          c = getchar();
        }
      while (c != '\n' && c != EOF);
      putchar('\n');
    }
  while (conversions < 1);
  return result;
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
    return counter;
  }
 
}

char *ask_question_string(char *question, char *buf, int buf_siz)
{
  int read;
  do
    {
      printf("%s", question);
      read = read_string(buf, buf_siz);
    }
  while (read < 1);
  return buf;
}


/*
int main(void)
{
    int buf_siz = 255;
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
