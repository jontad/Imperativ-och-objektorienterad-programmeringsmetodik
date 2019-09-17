#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>


bool is_number(char *str)
{
  bool a = true;
  for (int i = 0; i <= (strlen(str) - 1); ++i)

  {if (isdigit(str[i]) == false) {a = false;}}

  return a;
}
 


int main(int argc, char *argv[])
{

  if (argc > 1 && is_number(argv[1])) {printf("%s is a number\n", argv[1]);}

  else
    {
    
  if (argc > 1)  {printf("%s is not a number\n", argv[1]);}

  else {printf("Please provide a command line argument!\n");}
    }

  return 0;
}
