#include <stdlib.h>
#include <stdio.h>



int main(int argc, char *argv[])

{
  for (int i = 1; i <= atoi(argv[1]); ++i)
    {
    if (i != 1)
      {
	printf(", ");
      }
    {
      if ((i % 15) == 0)
	{
	  printf("Fizz Buzz");
	}
      else
	{
	  if ((i % 5) == 0)
	    {
	      printf("Buzz");
	    }
	  else
	    {
	      if ((i % 3) == 0)
		{
		  printf("Fizz");
		}
	      else
		{
		  printf("%d", i);
		}
	    }
	}
      
	      
    }
    }



  printf("\n");
  return 0;
}
  
