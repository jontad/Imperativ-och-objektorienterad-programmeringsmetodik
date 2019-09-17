#ifndef __UTILS_H__
#define __UTILS_H__


#include <stdbool.h>
int read_string(char *buf, int buf_siz);
bool is_number(char *str);
int ask_question_int(char *question);
char *ask_question_string(char *question, char *buf, int buf_siz);
int print(char argv[]);
int println(char argv[]);

#endif
