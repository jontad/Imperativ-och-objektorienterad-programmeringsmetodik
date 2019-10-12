#ifndef UTILS_H_
#define UTILS_H_

#include <stdbool.h>

extern char *strdup(const char *);

/// Typedefs used in webstore.c
typedef struct merchandise merch_t;
typedef struct shelf shelf_t;

typedef struct hash_table ioopm_hash_table_t;
typedef struct list ioopm_list_t;
typedef struct link ioopm_link_t;

/// Typedef used in db.c
typedef struct item item_t;



typedef union
{ 
  int   int_value;
  float float_value;
  char *string_value;
} answer_t;


typedef bool(*check_func)(char *);
typedef answer_t(*convert_func)(char *);


int read_string(char *buf, int buf_siz);
bool is_number(char *str);
bool not_empty(char *str);
answer_t ask_question(char *question, check_func check, convert_func convert);
int ask_question_int(char *question);
char *ask_question_string(char *question);
char *ask_question_shelf(char *question);
int print(char argv[]);
int println(char argv[]);

/// for testing webstore
char *magick(char *arr1[], char *arr2[], char *arr3[], int n);
char ask_question_menu(char *question);

#endif
