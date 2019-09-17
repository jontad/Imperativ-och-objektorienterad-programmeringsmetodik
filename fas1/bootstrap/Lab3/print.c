#include <stdio.h>



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


int main(int argc, char *argv[])
{
    {
      printf("puts: ");
      puts(argv[1]);
      printf("println: ");
      println(argv[1]);
    }
  return 0;
}
