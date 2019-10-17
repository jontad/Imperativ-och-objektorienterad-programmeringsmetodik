#pragma once
#include <stdbool.h>

/**
 * @file iterator.h
 * @author Fredrik Yngve & Jonathan Tadese
 * @date 11 Oct 2019
 * @brief Simple iterator that iterates over elements in a linked list elements.
 *
 * http://wrigstad.com/ioopm19/assignments/assignment1.html
*/

typedef struct iter ioopm_list_iterator_t;

/// @brief Create an iterator for a given list
/// @param list: the list to be iterated over
/// @return an iterator positioned at the start of list
ioopm_list_iterator_t *ioopm_list_iterator(ioopm_list_t *list);

/// @brief Checks if there are more elements to iterate over
/// @param iter: the iterator
/// @return true if the iterator have more elements to iterate over, else false
bool ioopm_iterator_has_next(ioopm_list_iterator_t *iter);

/// @brief Step the iterator forward one step
/// @param iter: the iterator
/// @return the next element
elem_t ioopm_iterator_next(ioopm_list_iterator_t *iter);

/// @brief Reposition the iterator at the start of the underlying list
/// @param iter: the iterator
void ioopm_iterator_reset(ioopm_list_iterator_t *iter);

/// @brief Retrives the current element from the underlying list
/// @param iter: the iterator
/// @return the current element
elem_t ioopm_iterator_current(ioopm_list_iterator_t *iter);

/// @brief Destroy the iterator and return its resources
/// @param iter: the iterator
void ioopm_iterator_destroy(ioopm_list_iterator_t *iter);

