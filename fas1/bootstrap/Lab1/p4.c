#include <stdio.h>
#include <stdlib.h>

int main(int argc , char *argv[])
 
{
   
  int counter = 0;
  for (int x = 1; x <= atoi(argv[1]); ++x)
    {
      for (int i = 1; i <= x * atoi(argv[2]);  ++i)
	{
	  printf ("*");
	}
      counter = counter + x * atoi(argv[2]);

      printf("\n");

    }
  printf ("Totalt: %d\n", counter);
  return 0;
}
