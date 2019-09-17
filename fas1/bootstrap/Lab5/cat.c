#include <stdio.h>

void cat(char *filename)
{
  int counter = 1;
  FILE *f = fopen(filename, "r");
  int c = fgetc(f);

  printf("%d", counter);
  
  while (c != EOF)
  {
    fputc(c, stdout);
    if (c == '\n')
      {
	++counter;
	printf("%d", counter);
      }
    c = fgetc(f);
  }

  fclose(f);
}

int main(int argc, char *argv[])
{
  if (argc < 2)
  {
    fprintf(stdout, "Usage: %s fil1 ...\n", argv[0]);
  }
  else
  {
    for (int i = 1; i < argc; ++i)
    {
      cat(argv[i]);
    }
  }

  return 0;
}
