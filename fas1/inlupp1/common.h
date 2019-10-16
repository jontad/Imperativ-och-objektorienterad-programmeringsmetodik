#pragma once

typedef union elem elem_t;

//valid values and keys for the packaged linked list- and hash table functions. 
union elem
{
  int ioopm_int;
  unsigned int ioopm_u_int;
  bool ioopm_bool;
  char *ioopm_str;
  void *ioopm_void_ptr;
};

//type definitions for linked list

typedef struct list ioopm_list_t;
typedef struct link ioopm_link_t;

typedef bool(*ioopm_char_predicate)(ioopm_link_t *element, void *extra);
typedef void(*ioopm_apply_char_function)(ioopm_link_t **element, void *extra);

//type definitions for hash table

typedef struct entry entry_t;
typedef struct hash_table ioopm_hash_table_t;
typedef struct option option_t;

typedef bool(*ioopm_predicate)(ioopm_hash_table_t *ht, elem_t key, elem_t value, void *extra);
typedef void(*ioopm_apply_function)(ioopm_hash_table_t *ht, elem_t key, elem_t *value, void *extra);



