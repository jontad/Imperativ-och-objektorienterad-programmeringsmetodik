#ifndef _BACKENDH_
#define _BACKENDH_

#include "common.h"


/**
 * @file backend.h
 * @author Emanuel Jansson & Jonathan Tadese
 * @date 1 Nov 2019
 * @brief Backend for public functions that run webstore
 */




typedef struct merch merch_t;
typedef struct hash_table ioopm_hash_table_t;

/// @brief Creates a database with hash tables of merch, shelves and carts
/// @return a new empty database
ioopm_database_t *ioopm_create_database();
  
/// @brief Add merch into the database
/// @param db database to add merch to
void ioopm_add_merch(ioopm_database_t *db);

/// @brief print entries to the terminal 20 at a time 
/// @param db database containing merch to list
void ioopm_list_merch(ioopm_database_t *db);

/// @brief remove merch from database
/// @param db database to remove merch from
void ioopm_remove_merch(ioopm_database_t *db);

/// @brief edit name, description or price of merch
/// @param db database containing merch to edit
void ioopm_edit_merch(ioopm_database_t *db);

/// @brief print shelf and amount of merch
/// @param db database containing merch
void ioopm_show_stock(ioopm_database_t *db);

/// @brief increase stock of merch at a particular shelf
/// @param db database of merch
void ioopm_replenish_stock(ioopm_database_t *db);

/// @brief creates a new empty shopping cart
/// @param db database of merch
/// @return pointer to cart 
cart_t *ioopm_create_cart(ioopm_database_t *db);

/// @brief removes a shopping cart from database and frees its memory
/// @param db database of merch
void ioopm_remove_cart(ioopm_database_t *db);

/// @brief adds an item to a specific cart
/// @param db database of merch
void ioopm_add_to_cart(ioopm_database_t *db);

/// @brief removes an item from a specific cart
/// @param db database of merch
void ioopm_remove_from_cart(ioopm_database_t *db);

/// @brief calculates cost of items in a specific cart
/// @param db database in cart
void ioopm_calculate_cost(ioopm_database_t *db);

/// @brief remove a specific cart and decrease the stock for merch in the cart
/// @param db database in cart
void ioopm_checkout(ioopm_database_t *db);

/// @brief free the entire database
/// @param db database in cart
void ioopm_destroy_database(ioopm_database_t *db);


/// @brief prints menu and asks for input to choose an operation
/// @param db database operated on
/// @return 0 to end the event loop (quit the program)
void event_loop(ioopm_database_t *db);

#endif
