#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <errno.h>
#include <string.h>
#include "hash_table.h"
#include "list_linked.h"
#include "common.h"


#define No_Buckets 17
#define Load 1


#define Free(ptr) {free(ptr); ptr = NULL;}


#define int_elem(i) (elem_t) {.ioopm_int=(i)}
#define str_elem(s) (elem_t) {.ioopm_str=(s)}
#define bool_elem(b) (elem_t) {.ioopm_bool=(b)}
#define void_elem(v) (elem_t) {.ioopm_void_ptr=(v)}

#define Success(v)      (option_t) { .success = true, .value = v };
#define Failure()       (option_t) { .success = false };
#define Successful(o)   (o.success == true)
#define Unsuccessful(o) (o.success == false)


struct entry
//Entry in a hash table (ht)
{
  elem_t key;    /// key for entry
  elem_t value;  /// value for entry
  entry_t *next; /// points to next entry in ht (possibly NULL)
};

struct link
/// Link between elements in ht
{
  elem_t value;       /// value for entry
  ioopm_link_t *next; /// PTR to next link
};


struct list
{
  size_t list_size;        /// amount of entries in ht
  ioopm_eq_function equal; /// PTR to function that compares elements
  ioopm_link_t *first;     /// PTR to first link in ht
  ioopm_link_t *last;      /// PTR to last link in ht
};


struct hash_table
{
  ioopm_hash_function hash; /// function to hash key
  ioopm_eq_function eq_key; /// PTR to function that compares keys
  ioopm_eq_function eq_val; /// PTR to function that compares values
  entry_t *buckets;         
  int no_buckets;
  float load_factor;        /// how often buckets increases in size
};

struct option
{
  bool success;
  elem_t value;
};

///////////////// ENTRY //////////////////////////

static entry_t *entry_create(elem_t key, elem_t value, entry_t *next)
{
  entry_t *entry = calloc(1, sizeof(entry_t));
  entry->key = key;
  entry->value = value;
  entry->next = next;
  return entry;
}

static void entry_destroy(entry_t *entry)
{
  free(entry);
}

static entry_t *find_previous_entry_for_key(ioopm_hash_table_t *ht, entry_t *entry, elem_t key)
{
  if (!entry) return NULL;
  
  while (entry->next != NULL && ht->hash(entry->next->key) < ht->hash(key))
    {
      entry = entry->next;
    }
  return entry;
}


////////////////////// EQUALITY //////////////////////////// 

static bool key_equiv(ioopm_hash_table_t *ht, elem_t key, elem_t value, void *x)
{
  elem_t elem = *(elem_t *)x;
 
  return ht->eq_key(elem, key);
}


static bool value_equiv(ioopm_hash_table_t *ht, elem_t key_ignored, elem_t value, void *x)
{
  elem_t elem = *((elem_t *)x);

  return ht->eq_val(elem, value);
}


////////////////////////////////////////////////////////////////

char *ioopm_strdup(char *str)
{
  size_t len = strlen(str);
  char *result = calloc(len + 1, sizeof(char));
  strncpy(result, str, len);
  return result;
}


ioopm_hash_table_t *ioopm_hash_table_create(ioopm_hash_function hash, ioopm_eq_function eq_key, ioopm_eq_function eq_val) 
{
  ioopm_hash_table_t *result = calloc(1, sizeof(ioopm_hash_table_t));
  result->buckets = calloc(No_Buckets, sizeof(entry_t));
  result->eq_key = eq_key;
  result->eq_val = eq_val;
  result->hash = hash;
  result->no_buckets = No_Buckets;
  result->load_factor = Load;
  return result;
}


void ioopm_hash_table_clear(ioopm_hash_table_t *ht)
{
  entry_t *entry = NULL;
  entry_t *tmp = NULL;
  
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry = &ht->buckets[i];
      while(entry->next != NULL)
	{
	  tmp = entry->next;
	  entry->next = entry->next->next;
	  entry_destroy(tmp);
	} 
    }
 
}


size_t ioopm_hash_table_size(ioopm_hash_table_t *ht)
{
  size_t counter = 0;
  entry_t *entry = NULL;
  
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry = &ht->buckets[i];
      while(entry->next != NULL)
	{
	  entry = entry->next;
	  ++counter;
	} 
    }
  return counter;
}


bool ioopm_hash_table_is_empty(ioopm_hash_table_t *ht)
{
  entry_t *entry;
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry = &ht->buckets[i];
      if (entry->next != NULL)
	{
	  return false;
	}
      
    }
  return true;
}


ioopm_list_t *ioopm_hash_table_keys(ioopm_hash_table_t *ht)
{
  ioopm_list_t *arrKeys = ioopm_linked_list_create(ht->eq_key);
  entry_t *entry = NULL;
  
   
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry = &ht->buckets[i];
      while(entry->next != NULL)
	{
	  ioopm_linked_list_append(arrKeys, entry->next->key);
	  entry = entry->next;
	} 
    }
  
  return arrKeys;
}


ioopm_list_t *ioopm_hash_table_values(ioopm_hash_table_t *ht)
{
  ioopm_list_t *arrValues = ioopm_linked_list_create(ht->eq_val);
  entry_t *entry = NULL;
  
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry = &ht->buckets[i];
      while(entry->next != NULL)
	{
	  ioopm_linked_list_append(arrValues, entry->next->value);
	  entry = entry->next;
	} 
    }
  return arrValues;
}


elem_t ioopm_hash_table_remove(ioopm_hash_table_t *ht, elem_t key)
{
  entry_t *tmp = find_previous_entry_for_key(ht, &ht->buckets[ht->hash(key) % No_Buckets], key);
  entry_t *next = tmp->next;
   
  if (next && ht->hash(next->key) == ht->hash(key))
    {
      elem_t value = next->value;
      tmp->next = next->next;       
      entry_destroy(next);
      return value;
    }
  return str_elem("Entry doesn't exist!");
}


void ioopm_hash_table_destroy(ioopm_hash_table_t *ht)
{
  entry_t *entry = NULL;
  entry_t *tmp = NULL;
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry = &ht->buckets[i];
      while(entry->next != NULL)
	{
	  tmp = entry->next;
	  entry->next = entry->next->next;
	  entry_destroy(tmp);
	} 
    }
  Free(ht->buckets);
  Free(ht);
}


option_t ioopm_hash_table_lookup(ioopm_hash_table_t *ht, elem_t key)
{
  entry_t *tmp = find_previous_entry_for_key(ht, &ht->buckets[ht->hash(key) % ht->no_buckets], key);
  entry_t *next = tmp->next;
  
  if (next && ht->hash(next->key) == ht->hash(key))
    {
      return Success(next->value);
    }
  else
    {
      return Failure();
    }
}

/// Auxiliary function: Increases number of buckets according to set load size and amount of elements in ht
static void extend_buckets(ioopm_hash_table_t *ht)
{
  ioopm_list_t *keys = ioopm_hash_table_keys(ht);
  ioopm_list_t *values = ioopm_hash_table_values(ht);

  ioopm_hash_table_clear(ht);
  free(ht->buckets);
  ht->no_buckets = ht->no_buckets * 2;
  ht->buckets = calloc(ht->no_buckets, sizeof(entry_t));

  ioopm_link_t *link_keys = keys->first;
  ioopm_link_t *link_values = values->first;

  while (link_keys && link_values) {
    ioopm_hash_table_insert(ht, link_keys->value, link_values->value);
    link_keys = link_keys->next;
    link_values = link_values->next;
  }

  ioopm_linked_list_destroy(keys);
  ioopm_linked_list_destroy(values);
}

void ioopm_hash_table_insert(ioopm_hash_table_t *ht, elem_t key, elem_t value)
{
  
  int bucket = ht->hash(key) % ht->no_buckets;
    entry_t *entry = find_previous_entry_for_key(ht, &ht->buckets[bucket], key);
  entry_t *next = entry->next;
  
  
  if (next != NULL && ht->hash(next->key) == ht->hash(key))
    {
      next->value = value;
    }
  else
    {
      entry->next = entry_create(key, value, next);

      size_t threshold = (size_t) (ht->no_buckets * ht->load_factor);
      if(ioopm_hash_table_size(ht) >= threshold)
      	{
	  extend_buckets(ht);
      	}
    }
}


bool ioopm_hash_table_has_key(ioopm_hash_table_t *ht, elem_t key)
{
  return ioopm_hash_table_any(ht, key_equiv, &key);
}

bool ioopm_hash_table_has_value(ioopm_hash_table_t *ht, elem_t value)
{
  return ioopm_hash_table_any(ht, value_equiv, &value);
}


bool ioopm_hash_table_any(ioopm_hash_table_t *ht, ioopm_predicate pred, void *arg)
{
  bool result = false;

  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry_t *entry = &ht->buckets[i];
      while(entry->next != NULL)
	{
	  if(pred(ht, entry->next->key, entry->next->value, arg))
	    {
	      return true;
	    }
	  entry = entry->next;
	} 
    }
    return result;
}


bool ioopm_hash_table_all(ioopm_hash_table_t *ht, ioopm_predicate pred, void *arg)
{
  bool result = true;

  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry_t *entry = &ht->buckets[i];
      while(entry->next != NULL)
	{
	  if(!pred(ht, entry->next->key, entry->next->value, arg))
	    {
	      return false;
	    }
	  entry = entry->next;
	} 
    }
  return result;
}

void ioopm_hash_table_apply_to_all(ioopm_hash_table_t *ht, ioopm_apply_function apply_fun, void *arg)
{
  entry_t *entry;
  for(int i = 0; i < ht->no_buckets; ++i)
    {
      entry = &ht->buckets[i];
      while (entry->next != NULL)
	{
	  apply_fun(ht, entry->next->key, &entry->next->value, arg);
	  entry = entry->next; 
	}
    }
}

