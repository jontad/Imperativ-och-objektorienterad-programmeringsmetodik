# Inlupp 1

I denna fil, _skall_ du skriva instruktioner för
- hur man bygger programmet
- hur man kör programmet
- hur man kör testerna till programmet
- övrig information att behöva känna till för bygg/körning

Idealet _bör_ vara att samtliga bygg-/körsteg kan kontrolleras mha `make`:
- `make` för att bygga programmet
- `make test` för att bygga och köra testerna i `valgrind`
- `make run` för att köra programmet (samt först bygga, om så inte är gjort)

Kom slutligen ihåg deadlines. Dessa hittar du på kurshemsidan: [klicka
här](http://wrigstad.com/ioopm19/#org2090f8e).

Happy hacking!
/ Tobias & labassarna
# How to run
- `make` för att bygga programmet
- `make test` för att köra testerna med Valgrind
- `make run1k-long` för att köra programmet med 1k-long-words.txt
- `make run10k` för att köra programmet med 10k-words.txt
- `make run16k` för att köra programmet med 16k-words.txt

###### Code coverage
####### Line coverage:  
- hash_table.c		100%  
- linked_list.c		97.8%
- freq_count.c		92.5%

####### Branch coverage:
# Initial Profiling Results
- hash_table.c		62.50% of 64
- linked_list.c		15.00% of 40
- freq_count.c		100% of 20
###### Top 3 functions:  
####### 1k-long-words.txt  
- 42 301	mcount  
- 42 301	__mcount_internal  
- 12 100	__strcmp_ssse3  
  
####### 10k-words.txt  
- 2 087 541	mcount  
- 2 087 541	__mcount_internal  
- 957 944 	__strcmp_ssse3

####### 16k-words.txt
- 24 964 955	mcount
- 24 964 955	__mcount_internal
- 12 358 353	__strcmp_ssse3

###### Top 3 functions in our code:
####### 1k-long-words.txt
- 45 494	entry_create
- 45 494	ioopm_linked_list_append
- 11 831	str_extract

####### 10k-words.txt
- 3 372 742	ioopm_linked_list_get
- 3 352 744	find_previous_entry_for_index
- 20 002	ioopm_linked_list_clear

####### 16k-words.txt
- 36 890 452	entry_create
- 36 887 666 	ioopm_linked_list_get
- 36 853 684 	find_previous_entry_for_index

###### Konsekvent kod  
Vår kod är inte konsekvent för de olika filerna då funktionerana tar olika lång tid beroende på om ordet är långt eller om det är många ord.
###### Trend  
När det är många ord så behöver find_previous_entry_for_index köras många gånger eftersom vi behöver kolla efter ett element varje gång vi läser ett ord vilket leder till att den körs många gånger.
###### Förväntningar  
Resultatet stämmer överens med våra förväntingar då vi vet att vissa funktioner behöver köras mer ju fler ord vi har.
###### Förbättringar  
Vi ser att entry_create och find_previous_entry_for_index körs ofta. Om vi optimerar dem kan vi göra programmet snabbare och så att det använder mindre minne.

