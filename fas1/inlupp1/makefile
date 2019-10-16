comp: hash_table.c freq_count.c linked_list.c
	gcc --coverage -g -ggdb3 -Wall -std=c11 hash_table.c freq_count.c linked_list.c -o freq_count_test -lcunit -pg

comp_test: hash_table.c linked_list.c unit_tests.c
	gcc -g -ggdb3 -Wall -std=c11 hash_table.c linked_list.c unit_tests.c -o tests -lcunit -pg

test:
	make comp_test
	valgrind --leak-check=full ./tests

run1k-long:
	make comp
	./freq_count_test  1k-long-words.txt

run10k:
	make comp
	./freq_count_test 10k-words.txt

run16k:
	make comp
	./freq_count_test 16k-words.txt
	
hashlcov: hash_table.c linked_list.c unit_tests.c
	lcov -c -d . -o tests.info

hashhtml: tests.info
	genhtml tests.info -o hash-lcov

freqlcov: hash_table.c linked_list.c freq_count.c
	lcov -c -d . -o freq_count_test.info

freqhtml: freq_count_test.info
	genhtml freq_count_test.info -o freq-lcov

freqTest:
	make run16k
	make freqlcov
	make freqhtml

runTest: 
	make comp_test
	./tests
	make hashlcov
	make hashhtml

memtest: hash_table_test
	valgrind --leak-check=full ./hash_table_test

hash: hash_table.c linked_list.c hash_table_tests.c
	gcc --coverage -g -ggdb -Wall -std=c11 hash_table.c linked_list.c hash_table_tests.c -o hash -lcunit


testHash:
	make hash
	./hash
	valgrind --leak-check=full ./hash
	make hashlcov
	make hashhtml	

linked: linked_list.c test_linked_list.c
	gcc --coverage -g -ggdb -Wall -std=c11 linked_list.c test_linked_list.c -o linked -lcunit		

linkedlcov: linked_list.c test_linked_list.c
	lcov -c -d . -o linked.info

linkedhtml: linked.info
	genhtml linked.info -o linked-lcov

testLinked:
	make linked
	./linked
	valgrind --leak-check=full ./linked
	make linkedlcov
	make linkedhtml


