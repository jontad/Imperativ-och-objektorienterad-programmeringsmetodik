#define _GNU_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "list_linked.h"
#include "hash_table.h"

#include "common.h"

#define Free(ptr) {free(ptr); ptr = NULL;}
#define Success(v)      (option_t) { .success = true, .value = v };

#define int_elem(i) (elem_t) {.ioopm_int=(i)}
#define str_elem(s) (elem_t) {.ioopm_str=(s)}
#define bool_elem(b) (elem_t) {.ioopm_bool=(b)}
#define void_elem(v) (elem_t) {.ioopm_void_ptr=(v)}

#define Delimiters "+-#@()[]{}.,:;!? \t\n\r"

struct link 
{
  elem_t value; //int
  ioopm_link_t *next;
};


struct list
{
  size_t list_size; //int
  ioopm_eq_function equal;
  ioopm_link_t *first;
  ioopm_link_t *last;
};

struct iter
{
  ioopm_link_t *current;
  ioopm_list_t *list;
};

struct option
{
  bool success;
  elem_t value;
};

typedef struct key_value key_value_t;
struct key_value
{
  char *key;
  int value;
};


static bool int_equal(elem_t fstInt, elem_t sndInt)
{
  return fstInt.ioopm_int == sndInt.ioopm_int;
}

static bool str_equal(elem_t fstStr, elem_t sndStr)
{
  return (strcmp(fstStr.ioopm_str, sndStr.ioopm_str) == 0);
}

static unsigned long str_extract(elem_t key)
{
  unsigned long result = 0;
  char *str = key.ioopm_str;
  do
    {
      result = result + *str;
    }
  while(*++str != '\0');
  
  return (result);
}




static int cmpfunc(const void *p1, const void *p2)
{
  return strcmp((*(key_value_t *)p1).key,(*(key_value_t *)p2).key);
}

void sort_keys(key_value_t keys[], size_t no_keys)
{
  qsort(keys, no_keys, sizeof(key_value_t), cmpfunc);
}

void process_word(char *word, ioopm_hash_table_t *ht)
{
  char *tmp_word = strdup(word);
  option_t lookup = ioopm_hash_table_lookup(ht, str_elem(tmp_word));
  if(lookup.success)
    {
      option_t opt = ioopm_hash_table_lookup(ht, str_elem(tmp_word));
      int value = (opt.value).ioopm_int + 1;
      ioopm_hash_table_insert(ht, str_elem(tmp_word), int_elem(value));
      Free(tmp_word);      
    }
  else
    {
      ioopm_hash_table_insert(ht, str_elem(tmp_word), int_elem(1));
      }
}
void process_file(char *filename, ioopm_hash_table_t *ht)
{
  FILE *f = fopen(filename, "r");

  while (true) 
    {
      char *buf = NULL;
      size_t len = 0;
      getline(&buf, &len, f);
      
      if (feof(f))
        {
          free(buf);
          break;
        }
      
      for (char *word = strtok(buf, Delimiters);
	   word && *word;
	   word = strtok(NULL, Delimiters))
        {
	  process_word(word, ht);
        }

      free(buf);
    }
  
  fclose(f);
}

int main(int argc, char *argv[])
{
  ioopm_hash_table_t *ht = ioopm_hash_table_create(str_extract, str_equal, int_equal);
  
  if (argc > 1)
    {
      for (int i = 1; i < argc; ++i)
        {
          process_file(argv[i], ht);
        }


      ioopm_list_t *tmp_keys = ioopm_hash_table_keys(ht);
      ioopm_list_t *tmp_val = ioopm_hash_table_values(ht);
      
      size_t size = ioopm_linked_list_size(tmp_keys);
      key_value_t key_val[size];

      ioopm_link_t *key = tmp_keys->first;
      ioopm_link_t *val = tmp_val->first;
      
      int counter = 0;

      while (key && val) {
	key_val[counter].key = (key->value).ioopm_str;
	key_val[counter].value = (val->value).ioopm_int;
	key = key->next;
	val = val->next;
	++counter;
      }

      ioopm_linked_list_destroy(tmp_keys);
      ioopm_linked_list_destroy(tmp_val);
      sort_keys(key_val, size);

      for (int i = 0; i < size; ++i)
	{
	  printf("%s [%d]\n", key_val[i].key, key_val[i].value);
	  Free(key_val[i].key);
	}
    }
  else
    {
      puts("Usage: freq-count file1 ... filen");
    }
  ioopm_hash_table_destroy(ht);
}
