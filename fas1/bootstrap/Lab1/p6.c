#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>


int main(int argc, char *argv[])

{
  
  int a = atoi(argv[1]);
  int b = atoi(argv[2]);

  while (a!=b) {if (a>b) {a = a - b;} else {b = b - a;}}
  printf("%d\n",a);

  
  return 0;
}
