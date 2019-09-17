#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
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




int main(int argc, char *argv[])

{
  for (int i = 1; i <= 2; ++i)
    {
      if (argc > 1 && is_number(argv[i]))
	{
	  printf("%s is a number\n", argv[i]);
	  if ((i == 2) && (is_number(argv[1]) == true)) 
	    {
	    int a = abs(atoi(argv[1]));
	    int b = abs(atoi(argv[2]));

	    while (a!=b)
	      {
	      if (a>b){a = a - b;}
	      else {b = b - a;}
	      }
	    printf("%d\n",a);
	    }
	}
      else
	{
	  if (argc > 1)
	    {
	      printf("%s is not a number\n", argv[i]);
	    }
	  else
	    {
	      printf("Please provide a command line argument!\n");
	    }
	}
    }
  

  
  return 0;
}

