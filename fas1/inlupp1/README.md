# Inlupp 1
# How to run
- `make` to build the program
- `make test` to run the tests together with Valgrind
- `make run1k-long` to run the program with 1k-long-words.txt
- `make run10k` to run the program with 10k-words.txt
- `make run16k` to run the program with 16k-words.txt
- `./freq_count_test` followed by atleast one .txt-fil to run the program with an optional .txt-fil

##### Code coverage
The following numbers were retrived using gprof and callgrind.

###### Line coverage:  
- hash_table.c		100%  
- linked_list.c		97.8%
- freq_count.c		92.5%

###### Branch coverage:
- hash_table.c		62.50% of 64
- linked_list.c		15.00% of 40
- freq_count.c		100% of 20

# Initial Profiling Results
##### Top 3 functions:  
###### 1k-long-words.txt  
- 42 301	mcount  
- 42 301	__mcount_internal  
- 12 100	__strcmp_ssse3  
  
###### 10k-words.txt  
- 2 087 541	mcount  
- 2 087 541	__mcount_internal  
- 957 944 	__strcmp_ssse3

###### 16k-words.txt
- 24 964 955	mcount
- 24 964 955	__mcount_internal
- 12 358 353	__strcmp_ssse3

##### Top 3 functions in our code:
###### 1k-long-words.txt
- 45 494	entry_create
- 45 494	ioopm_linked_list_append
- 11 831	str_extract

###### 10k-words.txt
- 3 372 742	ioopm_linked_list_get
- 3 352 744	find_previous_entry_for_index
- 20 002	ioopm_linked_list_clear

###### 16k-words.txt
- 36 890 452	entry_create
- 36 887 666 	ioopm_linked_list_get
- 36 853 684 	find_previous_entry_for_index

Our code is inconsistent because of the different inputs. Depending on the words that are used as input, different functions take more or less time and are called a different number of times. For example, if the words are long but few find_previous_entry_for_index don't have to be called as many times because we don't have to look up so many entries. Instead str_extract takes up more time because all the words are long and to extrack the hashed key we have to loop through all the characters in every word which takes alot of time.

The results matches our expectations because vi know that some functions takes more time the more entries we have in our linked list or hashtable.

To increase the performance of the program it would make most sense if we would optimize entry_create and/or find_previous_entry_for_index because they are called the most.
