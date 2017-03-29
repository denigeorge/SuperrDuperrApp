package com.deltaa.superrduperr.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * @author denigeorge
 * Repository for working with ToDoItem Entity.
 */
public interface ToDoItemRepo extends CrudRepository<ToDoItem, Integer> {
	/**
	 * Finding the list of items in a list with id: listId.
	 * @param listId
	 * @return
	 */
	public List<ToDoItem> findByToDoListId(Integer listId);
	
	/**
	 * finding an Item by itemId.
	 * @param itemId
	 * @return
	 */
	public Optional<ToDoItem> findById(Integer itemId);
	
	/**
	 * finding the list of items tagged with <tag>
	 * @param tag
	 * @param listId
	 * @return
	 */
	public List<ToDoItem> findByTagAndToDoListId(String tag, Integer listId);
	
}
