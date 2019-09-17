#include <string.h>
#include <stdio.h>

int string_length(char *argv)

{
  int counter = 0;
  while (argv[counter] != '\0')
    {
      counter = counter + 1;
    }
  return counter;
}



int main(int argc, char *argv[])
{
  if (argc < 2)
  {
    printf("Usage: %s words or string", argv[0]);
  }
  else
  {
    for (int i = 1; i < argc; ++i)
    {
      int expected = strlen(argv[i]);
      int actual   = string_length(argv[i]);
      printf("strlen(\"%s\")=%d\t\tstring_length(\"%s\")=%d\n",
             argv[i], expected, argv[i], actual);
    }
  }
  return 0;
}
