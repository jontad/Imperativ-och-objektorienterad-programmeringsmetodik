#pragma once
#include "common.h"
#include "list_linked.h"

/**
 * @file hash_table.h
 * @author Fredrik Yngve & Jonathan Tadese
 * @date 1 Sep 2019
 * @brief Hash table that maps keys to values.
 *
 * Header file contains every function necessary to operate on a hash table.
 * These are further explained down below.
 * Valid keys and values are specified in the common header file.
 *
 * @see http://wrigstad.com/ioopm19/assignments/assignment1.html
 */

typedef unsigned long(*ioopm_hash_function)(elem_t key);

/// @brief Create a new hash table
/// @param hash: PTR to function that hashes key 
/// @param eq_key: PTR to function that compares keys
/// @param eq_val: PTR to function that compares values
/// @return A new empty hash table
ioopm_hash_table_t *ioopm_hash_table_create(ioopm_hash_function hash, ioopm_eq_function eq_key, ioopm_eq_function eq_val);

/// @brief Delete a hash table and free its memory
/// @param ht: a hash table to be deleted
void ioopm_hash_table_destroy(ioopm_hash_table_t *ht);

/// @brief add key => value entry in hash table ht
/// @param ht: hash table operated upon
/// @param key: key to insert
/// @param value: value to insert
void ioopm_hash_table_insert(ioopm_hash_table_t *ht, elem_t key, elem_t value);

/// @brief lookup value for key in hash table ht
/// @param ht: hash table operated upon
/// @param key: value to lookup.
/// @return boolean to indicate that value exits. Alse return the value mapped to by key if true
option_t ioopm_hash_table_lookup(ioopm_hash_table_t *ht, elem_t key);

/// @brief remove any mapping from key to a value
/// @param ht: hash table operated upon
/// @param key: entry bound to key to remove
/// @return the value mapped to key 
elem_t ioopm_hash_table_remove(ioopm_hash_table_t *ht, elem_t key);

/// @brief returns the number of key => value entries in the hash table
/// @param ht: hash table operated upon
/// @return the number of key => value entries in the hash table
size_t ioopm_hash_table_size(ioopm_hash_table_t *ht);

/// @brief checks if the hash table is empty
/// @param ht: hash table operated upon
/// @return true if size of ht is 0, else false
bool ioopm_hash_table_is_empty(ioopm_hash_table_t *ht);

/// @brief clear all the entries in a hash table
/// @param ht: hash table operated upon
void ioopm_hash_table_clear(ioopm_hash_table_t *ht);

/// @brief return the keys for all entries in a hash map (in no particular order, but same as ioopm_hash_table_values)
/// @param ht: hash table operated upon
/// @return a linked list of keys for hash table ht
ioopm_list_t *ioopm_hash_table_keys(ioopm_hash_table_t *ht);

/// @brief return the values for all entries in a hash map (in no particular order, but same as ioopm_hash_table_keys)
/// @param ht: hash table operated upon
/// @return a linked list of values for hash table ht
ioopm_list_t *ioopm_hash_table_values(ioopm_hash_table_t *ht);

/// @brief check if a hash table has an entry with a given key
/// @param ht: hash table operated upon
/// @param key: the key sought
bool ioopm_hash_table_has_key(ioopm_hash_table_t *ht, elem_t key);

/// @brief check if a hash table has an entry with a given value
/// @param ht: hash table operated upon
/// @param value: the value sought
bool ioopm_hash_table_has_value(ioopm_hash_table_t *ht, elem_t value);

/// @brief check if a predicate is satisfied by all entries in a hash table
/// @param ht: hash table operated upon
/// @param pred: the predicate
/// @param arg: extra argument to pred
bool ioopm_hash_table_all(ioopm_hash_table_t *ht, ioopm_predicate pred, void *arg);

/// @brief check: if a predicate is satisfied by any entry in a hash table
/// @param ht: hash table operated upon
/// @param pred: the predicate
/// @param arg: extra argument to pred
bool ioopm_hash_table_any(ioopm_hash_table_t *ht, ioopm_predicate pred, void *arg);

/// @brief apply a function to all entries in a hash table
/// @param ht: hash table operated upon
/// @param apply_fun: the function to be applied to all elements
/// @param arg: extra argument to apply_fun
void ioopm_hash_table_apply_to_all(ioopm_hash_table_t *ht, ioopm_apply_function apply_fun, void *arg);

/// @brief duplicate str to other place in memory
/// @param str: string to be duplicate
/// @return a duplicate of str 
char *ioopm_strdup(char *str);
