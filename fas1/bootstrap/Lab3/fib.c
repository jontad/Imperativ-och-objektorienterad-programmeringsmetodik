#include <stdio.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdlib.h>



/// Den intressanta delen av programmet

int fib_rec(int num, int fib1, int fib2, int index) // Den rekursiva delen
{
  int temp;
  if (index < num)
    {
      temp = fib1;
      fib1 = fib1 + fib2;
      fib2 = temp;
      return fib_rec(num, fib1, fib2, index +1);
    }
  else
    {
      return fib1;
    }
}

int fib(int num)
{
  return fib_rec(num, 0, 1, 0);
}





/// Den ointressanta main()-funktionen
int main(int argc, char *argv[])
{
  if (argc != 2)
  {
    printf("Usage: %s number\n", argv[0]);
  }
  else
  {
    int n = atoi(argv[1]);
    if (n < 2)
    {
      printf("fib(%d) = %d\n", n, n);
    }
    else
    {
      printf("fib(%s) = %d\n", argv[1], fib(n));
    }
  }
  return 0;
}
