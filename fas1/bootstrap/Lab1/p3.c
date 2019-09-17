#include <stdio.h>

int main(void)
 
{
  int N = 10;
  int counter = 0;
  for (int x = 1; x <= N; ++x)
    {
      for (int i = 1; i != x; ++i)
	{
	  printf ("*");
	}
      counter = counter + x;
      
      printf("\n");
    }
  printf ("Totalt: %d\n", counter);
  return 0;
}
