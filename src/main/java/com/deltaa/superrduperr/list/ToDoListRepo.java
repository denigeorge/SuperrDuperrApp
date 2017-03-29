package com.deltaa.superrduperr.list;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

/**
 * @author denigeorge
 * Repository for working with ToDoList Entity.
 */
public interface ToDoListRepo extends CrudRepository<ToDoList, Integer> {
	/**
	 * Finding a list by Id. 
	 * 
	 * @param id
	 * @return
	 */
	public Optional<ToDoList> findById(Integer id);
}

