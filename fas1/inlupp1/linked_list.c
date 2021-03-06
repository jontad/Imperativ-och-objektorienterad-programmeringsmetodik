#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include "list_linked.h"
#include "common.h"
#include "iterator.h"


#define int_elem(i) (elem_t) {.ioopm_int=(i)}
#define str_elem(s) (elem_t) {.ioopm_str=(s)}
#define bool_elem(b) (elem_t) {.ioopm_bool=(b)}
#define void_elem(v) (elem_t) {.ioopm_void_ptr=(v)}

#define Free(ptr) {free(ptr); ptr = NULL;}

// STRUCTS ///////////////////////////////////////////////////////////////

struct link 
{
  elem_t value;
  ioopm_link_t *next;
};


struct list
{
  size_t list_size;
  ioopm_eq_function equal;
  ioopm_link_t *first;
  ioopm_link_t *last;
};

struct iter
{
  ioopm_link_t *current;
  ioopm_list_t *list;
  int index;
};

// FUNCTIONS //////////////////////////////////////////////////////////////

static ioopm_link_t *entry_create(ioopm_list_t* list, ioopm_link_t *next, elem_t value)
{
  ioopm_link_t *link = calloc(1, sizeof(ioopm_link_t));
  link->value = value;
  link->next = next;
  ++list->list_size;
  return link;
}


static ioopm_link_t *find_previous_entry_for_index(ioopm_list_t *list, int index)
{
  ioopm_link_t *entry = list->first;
  for(int i = 0; i < index-1; ++i) 
    {
      entry = entry->next;
    }
  return entry;
}


static void list_destroy(ioopm_list_t *list)
{
  Free(list);
}

static void link_destroy(ioopm_link_t *link)
{
  Free(link);
}

// CREATE //////////////////////////////////////////////////////////////////

ioopm_list_t *ioopm_linked_list_create(ioopm_eq_function equal) 
{
  ioopm_list_t *linked_list = calloc(1, sizeof(ioopm_list_t));
  linked_list->first = NULL;
  linked_list->last = NULL;
  linked_list->equal = equal;
  linked_list->list_size = 0;
  return linked_list;
}

// PREPEND ////////////////////////////////////////////////////////////////

void ioopm_linked_list_prepend(ioopm_list_t *list, elem_t value)
{
  ioopm_link_t *new_entry = entry_create(list, list->first, value);

  
  if(list->first == NULL)
    {
      list->last = new_entry;
    }
  list->first = new_entry;
}

// APPEND ////////////////////////////////////////////////////////////////

void ioopm_linked_list_append(ioopm_list_t *list, elem_t value)
{
  if(list->last == NULL)
    {
      ioopm_linked_list_prepend(list, value);
    }
  else
    {
      ioopm_link_t *new_entry = entry_create(list, NULL, value);
      list->last->next = new_entry;
      list->last = new_entry;
    }
}


// INSERT ////////////////////////////////////////////////////////////////

static void insert_aux(ioopm_list_t *list, int index, elem_t value)
{
  ioopm_link_t *previous_entry = find_previous_entry_for_index(list, index);
  ioopm_link_t *entry = entry_create(list, previous_entry->next, value);
  previous_entry->next = entry;
}

void ioopm_linked_list_insert(ioopm_list_t *list, int index, elem_t value)
{
  
  if(list->list_size == 0 || index < 1)
    {
      ioopm_linked_list_prepend(list, value);
    }
  else if(index > 0 && index < list->list_size) 
    {
      insert_aux(list, index, value);
    }
 
  else
    {
      ioopm_linked_list_append(list, value);
    }
}

// REMOVE ////////////////////////////////////////////////////////////////

static elem_t remove_aux(ioopm_list_t *list, int index, ioopm_link_t *prev, ioopm_link_t *elem)
{
  elem_t value = elem->value;

  prev->next = elem->next;
  link_destroy(elem);

  --list->list_size;
  
  return value;
}

elem_t ioopm_linked_list_remove(ioopm_list_t *list, int index)
{
  ioopm_link_t *prev_element = find_previous_entry_for_index(list, index);
  ioopm_link_t *element = prev_element->next;
  elem_t value;
  
  if (index == 0)
    {
      value = prev_element->value;
      list->first = prev_element->next;
      link_destroy(prev_element);
      --list->list_size;
    }
  else if(element != NULL)
    {
      value = remove_aux(list, index, prev_element, element);
    }
  return value;
}
// GET ///////////////////////////////////////////////////////////////////
elem_t ioopm_linked_list_get(ioopm_list_t *list, int index)
{
  if(index == 0)
    {
      return list->first->value;
    }

  ioopm_link_t *previous_element = find_previous_entry_for_index(list,index);
  elem_t value = previous_element->next->value;
  return value;
}

// CONTAINS /////////////////////////////////////////////////////////////
bool ioopm_linked_list_contains(ioopm_list_t *list, elem_t value)
{
  ioopm_link_t *link = list->first;
  for(int i = 0; i < list->list_size; ++i)
    {
      if(list->equal((link->value), value))
	{
	  return true;
	}
      link = link->next;
      
    }
  return false;
}

// SIZE /////////////////////////////////////////////////////////////////
size_t ioopm_linked_list_size(ioopm_list_t *list)
{
  return list->list_size;
}

// IS EMPTY ////////////////////////////////////////////////////////////
bool ioopm_linked_list_is_empty(ioopm_list_t *list)
{
  if(list->list_size == 0)
    {
      return true;
    }
  else
    {
      return false;
    }
}

// CLEAR ///////////////////////////////////////////////////////////////
void ioopm_linked_list_clear(ioopm_list_t *list)
{
  ioopm_link_t *link = list->first;
  ioopm_link_t *tmp = NULL;
  
  while(link != NULL)
    {
      tmp = link;
      link = link->next;
      link_destroy(tmp);
    }
}

// DESTROY /////////////////////////////////////////////////////////////
void ioopm_linked_list_destroy(ioopm_list_t *list)
{
  ioopm_linked_list_clear(list);
  list_destroy(list);
}

// ALL ////////////////////////////////////////////////////////////////
bool ioopm_linked_list_all(ioopm_list_t *list, ioopm_char_predicate prop,  void *extra)
{
  ioopm_link_t *link = list->first;
  for (int i = 0; i < ioopm_linked_list_size(list); ++i)
    {
      if(!(prop(link, extra))) return false;
      link = link->next;
    }
  return true;
}

// ANY ///////////////////////////////////////////////////////////////
bool ioopm_linked_list_any(ioopm_list_t *list, ioopm_char_predicate prop, void *extra)
{
  ioopm_link_t *link = list->first;
  for (int i = 0; i < ioopm_linked_list_size(list); ++i)
    {
      if(prop(link, extra)) return true;
      link = link->next;
    }
  return false;
}

// APPLY TO ALL //////////////////////////////////////////////////////  
void ioopm_linked_apply_to_all(ioopm_list_t *list, ioopm_apply_char_function fun, void *extra)
{
  ioopm_link_t *link = list->first;

  while(link != NULL)
    {
      fun(&link, extra);
      link = link->next;
    }
}

// ITERATOR /////////////////////////////////////////////////////////
ioopm_list_iterator_t *ioopm_list_iterator(ioopm_list_t *list)
{
  ioopm_list_iterator_t *iter = calloc(1,sizeof(ioopm_list_iterator_t));
  iter->current = list->first;
  iter->list = list;
  iter->index = 0;
  return iter;
}

// HAS NEXT /////////////////////////////////////////////////////////
bool ioopm_iterator_has_next(ioopm_list_iterator_t *iter)
{
  if(iter->current->next != NULL)
    {
      return true;
    }
  return false;
}

// NEXT ////////////////////////////////////////////////////////////
elem_t ioopm_iterator_next(ioopm_list_iterator_t *iter)
{
  iter->current = iter->current->next;
  return iter->current->value;
}

// RESET ///////////////////////////////////////////////////////////
void ioopm_iterator_reset(ioopm_list_iterator_t *iter)
{
  iter->current = iter->list->first;
}

// CURRENT /////////////////////////////////////////////////////////
elem_t ioopm_iterator_current(ioopm_list_iterator_t *iter)
{
  return iter->current->value;
}

// DESTROY ////////////////////////////////////////////////////////
void ioopm_iterator_destroy(ioopm_list_iterator_t *iter)
{
  Free(iter);
}
