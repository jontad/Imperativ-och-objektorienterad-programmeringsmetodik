compile_webstore: frontend.c backend.c utils.c hash_table.c linked_list.c backend.h utils.h hash_table.h linked_list.h
	gcc -Wall -pedantic -g frontend.c backend.c utils.c hash_table.c linked_list.c backend.h utils.h hash_table.h linked_list.h

run: compile_webstore
	./a.out

compile_tests: tests.c backend.c utils.c hash_table.c linked_list.c backend.h utils.h hash_table.h linked_list.h
	gcc --coverage -Wall -pedantic -g tests.c backend.c utils.c hash_table.c linked_list.c backend.h utils.h hash_table.h linked_list.h -lcunit

only_test: compile_tests
	./a.out < test.txt

mem: compile_webstore
	valgrind -v --leak-check=full --track-origins=yes ./a.out

test: compile_tests
	valgrind -v --leak-check=full --track-origins=yes ./a.out < test.txt

compile_lcov: test
	lcov -c -d . -o tests.info 

lcov: compile_lcov
	genhtml tests.info -o tests-lcov
