package com.deltaa.superrduperr.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deltaa.superrduperr.exceptions.NoListException;
import com.deltaa.superrduperr.exceptions.ListNotFoundException;

/**
 * @author denigeorge
 * Service class for ToDoList.
 */
@Service
public class ToDoListService {
	
	@Autowired
	private ToDoListRepo toDoListRepo;
	
	/**
	 * Getting all the ToDo lists.
	 * @return
	 */
	public List<ToDoList> getAllLists(){
		List<ToDoList> toDoLists = new ArrayList<>();
		toDoListRepo.findAll().forEach(toDoLists::add);
		if (toDoLists.isEmpty()){
			throw new NoListException();
		}
		return toDoLists;
	}
	
	/**
	 * Getting a list with particular id.
	 * @param id
	 * @return
	 */
	public ToDoList getList(Integer id){
		//return toDoListRepo.findOne(id);
		return toDoListRepo.findById(id).orElseThrow(() -> new ListNotFoundException(id));
	}
	
	/**
	 * Checking if the List with id is available.
	 * @param id
	 */
	public void checkListAvailable(Integer id){
		toDoListRepo.findById(id).orElseThrow(() -> new ListNotFoundException(id));
	}
	
	/**
	 * Add a list.
	 * @param list
	 */
	public void addList(ToDoList list){
		toDoListRepo.save(list);
	}
	
	/**
	 * delete a list with particular id.
	 * @param id
	 */
	public void deleteList(Integer id){
		toDoListRepo.delete(id);
	}
	
	/**
	 * update a list with particular id.
	 * @param id
	 * @param list
	 */
	public void updateList(Integer id, ToDoList list){
		toDoListRepo.save(list);
	}
	
}
