
int ioopm_linked_list_remove(ioopm_list_t *list, int index)
{
  ioopm_link_t *previous_element = find_previous_entry_for_index(index);/*if entry does not exist: return "Element does not exist*/
  //Adjust for param list
  ioopm_link_t *element = previous_element->next;
  if (element != NULL)
    {
      int *value = element->value;
      previous_element->next = element->next;
      link_destroy(element);
      return value;
    }
}

int ioopm_linked_list_get(ioopm_list_t *list, int index);
{
  ioopm_link_t *previous_element = find_previous_entry_for_index(index);
  ioopm_link_t *value = previous_element->next->value;
  return value;
}

bool ioopm_linked_list_contains(ioopm_list_t *list, int element)
{
  for(int i = 0; i < list->list_size; ++i)
    {
      if(list->first == element)
	{
	  return true;
	}
      list->first = list->first->next;
    }
  else
    {
      return false;
    }
}

int ioopm_linked_list_size(ioopm_list_t *list)
{
  return list->list_size;
}

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

void ioopm_linked_list_clear(ioopm_list_t *list)
{
  
}
