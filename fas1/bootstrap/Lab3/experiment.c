#include "utils.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


typedef union { 
  int   int_value;
  float float_value;
  char *string_value;
} answer_t;

typedef bool(check_func)(char *);

typedef answer_t(convert_func)(char *);


answer_t ask_question(char *question, check_func check, convert_func convert)





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

/*
int main(void)
{
  int buf_size = 255;
  char buf[buf_size];
  char *svar;
  do
    {
     svar = ask_question_string("Mata in ett tal\n", buf, buf_size);
    }
  while ((strlen(svar) < 1) || (is_number(svar) == false));
  int tal = atoi(svar); 
  
  return tal;
}

*/
