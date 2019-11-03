# Inlupp 2
# How to run
- `make compile_webstore` or `make` to build the program.
- `make run` to build and run the program.
- `make test` to build the program and run the tests in valgrind.
- `make mem` to build and run the program in valgrind. 
- `make only_test` to build and run the tests.
- `make lcov` to get line coverage.

##### Code coverage
The following numbers were retrived using lcov.

## Line coverage:

-backend.c:
For some reason lcov only worked for tests.c. We have not been able to make it show line coverage for the other files no matter how much we tried, and we have asked our classmates for help. We will ask for help during the next lab and hopefully solve it then.

-tests.c: Lines: 97.7% (208 of 213 lines)
Coverage is not 100% as the test file includes code that only runs when the test fails. 

## Files needed to run and build program 

- `frontend.c`
- `backend.c`and `backend.h`
- `common.h`

- `hash_table.c` and `hash_table.h`
- `linked_list.c`and `linked_list.h` 
- `utils.c` and `utils.h`

- `tests.c` and `test.txt` 
