package com.deltaa.superrduperr.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deltaa.superrduperr.exceptions.ItemNotFoundException;
import com.deltaa.superrduperr.exceptions.NoItemsException;
import com.deltaa.superrduperr.generic.Status;

/**
 * @author denigeorge
 * Service class for ToDoItem operations.
 */
@Service
public class ToDoItemService {
	
	static Logger logger = Logger.getLogger(ToDoItemService.class.getName());
	
	@Autowired
	private ToDoItemRepo toDoItemRepo;
		
	/**
	 * get all the items in a list with id: listId
	 * @param listId
	 * @return
	 */
	public List<ToDoItem> getAllItems(Integer listId){
		List<ToDoItem> toDoItems = new ArrayList<>();
		toDoItemRepo.findByToDoListId(listId).forEach(toDoItems::add);
		if (toDoItems.isEmpty()){
			throw new NoItemsException();
		}
		return toDoItems;
	}
	
	/**
	 * Get item with a particular id
	 * @param id
	 * @return
	 */
	public ToDoItem getItem(Integer id){
		return toDoItemRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
	}
	
	/**
	 * Save an item in a list. Id of the corresponding list is set at the controller.
	 * @param item
	 */
	public void addItem(ToDoItem item){
		toDoItemRepo.save(item);
	}
	
	/**
	 * Delete a particular item from the list.
	 * @param id
	 */
	public void deleteItem(Integer id){
		toDoItemRepo.delete(id);
	}
	
	/**
	 * Update a particular item.
	 * @param item
	 */
	public void updateItem(ToDoItem item){
		toDoItemRepo.save(item);
	}

	/**
	 * Adding tag to an Item.
	 * @param itemId
	 * @param tag
	 */
	public void addTagToItem(Integer itemId, String tag) {
		ToDoItem item = toDoItemRepo.findOne(itemId);
		item.setTag(tag);
		toDoItemRepo.save(item);
	}

	/**
	 * Get all items with a particular tag <tag>
	 * @param listId
	 * @param tag
	 * @return
	 */
	public List<ToDoItem> getAllItems(Integer listId, String tag) {
		List<ToDoItem> toDoItems = new ArrayList<>();
		toDoItemRepo.findByTagAndToDoListId(tag, listId).forEach(toDoItems::add);
		if (toDoItems.isEmpty()){
			throw new NoItemsException();
		}
		return toDoItems;
	}

	/**
	 * Update the status of an item. (ACTIVE, COMPLETED, DELETED)
	 * @param itemId
	 * @param status
	 */
	public void updateItem(Integer itemId, Status status) {
		ToDoItem item = toDoItemRepo.findOne(itemId);
		item.setStatus(status);
		toDoItemRepo.save(item);
	}

	/**
	 * Add reminder to an Item.
	 * @param itemId
	 * @param reminder
	 */
	public void addRemindeToItem(Integer itemId, Date reminder) {
		ToDoItem item = toDoItemRepo.findOne(itemId);
		item.setReminder(reminder);;
		toDoItemRepo.save(item);
	}
	
}
