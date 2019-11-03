#ifndef __UTILS_H__
#define __UTILS_H__

#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <ctype.h>




extern char *strdup(const char *);

typedef union {
  int   int_value;
  float float_value;
  char *string_value;
  char *char_value;
} answer_t;

typedef bool(*check_func)(char *);
typedef answer_t(*convert_func)(char *);

bool not_empty(char *str);

answer_t ask_question(char *question, check_func check, convert_func convert);

bool is_number(char *str);

bool is_shelf(char *str);

bool is_menu_input(char *str);

int ask_question_int(char *question);

int read_string(char *buf, int buf_siz);

char *ask_question_string(char *question);

char *ask_question_shelf(char *question);

char *ask_question_menu(char *question);

int print(char *str);

int println(char *str);





#endif
