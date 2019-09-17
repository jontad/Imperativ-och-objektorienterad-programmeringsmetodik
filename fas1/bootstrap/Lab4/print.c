void print(char *str)
{

  while (*str != '\0')
    {
      putchar(*str);
      str = str + 1;
    }

}


int main(void)
{
  char *string = "hejsan svejsan";
  print (string);
}
/*
int print(char str*)
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
*/
