#include <stdlib.h>
#include <stdio.h>


typedef int(*int_fold_func)(int, int);

/// En funktion som tar en array av heltal, arrayens längd och
/// en pekare till en funktion f av typen Int -> Int -> Int
int foldl_int_int(int numbers[], int numbers_siz, int_fold_func f)
{
  int result = 0;

  // Loopa över arrayen och för varje element e utför result = f(result, e)
  for (int i = 0; i < numbers_siz; ++i)
  {
    result = f(result, numbers[i]);
  }

  return result;
}



int add(int a, int b)
{
  return a + b;
}


long sum(int numbers[], int numbers_siz)
{
  int summa = foldl_int_int(numbers, numbers_siz, add);
  return summa;
}



int main(int argc, char *argv[])
{
  if (argc = 2)
    {
      int size = (sizeof(argv)/sizeof(argv[0]));
  int result = sum(argv, size);
  printf("%d", result);
    }
  return 0;
}
