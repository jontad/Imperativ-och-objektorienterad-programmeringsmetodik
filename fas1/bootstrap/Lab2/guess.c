#include "utils.h"
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>

int main(void)

{
  int mittNummer = random() % 1024;
  int buf_siz = 255;
  char buf[buf_siz];
  char *namn = ask_question_string("Skriv ditt namn:", buf, buf_siz);
  printf("Du %s, jag tänker på ett tal ... kan du gissa vilket?\n",namn);

  bool klart = false;
  
  for (int i = 0; ((i < 15) || (klart == true)); ++i)
  {
    int gissning = ask_question_int("");
    
    if (mittNummer == gissning)
      {
       	klart = true;
	puts("Bingo!");
	printf("Det tog %s %d gissningar att komma fram till %d\n", namn, i + 1, mittNummer);

      }
    else
      {
	if (i >= 14)
	  {
	    printf("Nu har du slut på gissnigar! Jag tänkte på %d!\n", mittNummer);
	  }
	else
	  {
	    if (mittNummer < gissning)
	      {
		puts("För stort!");
	      }
	    else
	      {
		puts("För litet!");
	      }

	  }
      }
    

  }
  return 0;


}

