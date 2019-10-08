#ifndef __UTILS_H__
#define __UTILS_H__

#include <stdbool.h>

extern char *strdup(const char *);

typedef struct merchandise merch_t;
typedef union { 
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

#endif
