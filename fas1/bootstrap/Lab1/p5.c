#include <stdio.h>
#include <stdlib.h>

int main(int argc , char *argv[])

  
{
  int bs = 1;                      //0 betyder inte primtal, 1 betyder primtal
  int N = atoi(argv[1]);

  for (int x = 2; x <= N; ++x)
    {
      for (int y = 2; y <= N; ++y)
	{
	  if (((x*y) == N) || (N == 2)) 
           {
	     bs = 0;
           }
	}
     
    }
      if (bs == 1)
   	{
       	  puts("Primtal");
       	}
      else
	{
	  puts("Inte primtal");
	}    
   
  
  return 0;
}
