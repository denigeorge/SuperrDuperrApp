package com.deltaa.superrduperr.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deltaa.superrduperr.exceptions.IncorrectFormatException;
import com.deltaa.superrduperr.generic.Status;
import com.deltaa.superrduperr.list.ToDoList;
import com.deltaa.superrduperr.list.ToDoListService;

/**
 * @author denigeorge
 *	Controller for ToDo Item related operations.
 */
@RestController
public class ToDoItemController {

	static Logger logger = Logger.getLogger(ToDoItemController.class.getName());
	
	@Autowired
	private ToDoListService listService;
	
	@Autowired
	private ToDoItemService itemService;
	
	/**
	 * Get all the items in a list.
	 * 
	 * @param listId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/lists/{listId}/items", params={"!tag"})
	public List<ToDoItem> getAllItems(@PathVariable Integer listId) {
		this.validateList(listId);
		return itemService.getAllItems(listId);
	}

	/**
	 * Get all the items in a list with particular tag.
	 * 
	 * @param listId
	 * @param tag
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/lists/{listId}/items")
	public List<ToDoItem> getItemsWithTag(@PathVariable Integer listId, @RequestParam String tag) {
		this.validateList(listId);
		return itemService.getAllItems(listId, tag);
	}
	
	/**
	 * 	 * Get all the item in a list with particular Id.
	 * @param listId
	 * @param itemId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/lists/{listId}/items/{itemId}")
	public ToDoItem getItem(@PathVariable Integer listId, @PathVariable Integer itemId) {
		return itemService.getItem(itemId);
	}


	/**
	 * Add Item to a list with list id: listId.
	 * @param newitem
	 * @param listId
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/lists/{listId}/items")
	public void addItem(@RequestBody ToDoItem newitem, @PathVariable Integer listId) {
		this.validateList(listId);
		newitem.setStatus(Status.ACTIVE);
		newitem.setToDoList(new ToDoList(listId, "", ""));
		itemService.addItem(newitem);
	}

	/**
	 * update an item in the list
	 * 
	 * @param item
	 * @param listId
	 * @param itemId
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/lists/{listId}/items/{itemId}", params={"!tag", "!status", "!reminder"})
	public void updateItem(@RequestBody ToDoItem item, @PathVariable Integer listId, @PathVariable Integer itemId) {
		this.validateList(listId);
		this.validateItem(itemId);
		item.setToDoList(new ToDoList(listId, "", ""));
		itemService.updateItem(item);
	}
	
	/**
	 *	Update the status of an item: Status (ACTIVE, COMPLETED, DELETED)	
	 *
	 * @param listId
	 * @param itemId
	 * @param status
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/lists/{listId}/items/{itemId}", params={"!tag", "!reminder"})
	public void updateItem(@PathVariable Integer listId, @PathVariable Integer itemId, @RequestParam Status status) {
		itemService.updateItem(itemId, status);
	}	

	/**
	 * 	Add tag to an item.
	 * 
	 * @param listId
	 * @param itemId
	 * @param tag
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/lists/{listId}/items/{itemId}", params={"!status", "!reminder"})
	public void addItemTag(@PathVariable Integer listId, @PathVariable Integer itemId, @RequestParam String tag) {
		logger.info("adding tag to an item");
		itemService.addTagToItem(itemId, tag);
	}

	/**
	 * 	add reminder to an Item.
	 * 
	 * @param listId
	 * @param itemId
	 * @param reminder
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/lists/{listId}/items/{itemId}", params={"!status", "!tag"})
	public void addItemReminder(@PathVariable Integer listId, @PathVariable Integer itemId, @RequestParam String reminder) {
		logger.info("adding reminder to an item");

		this.validateList(listId);
		this.validateItem(itemId);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            Date date = formatter.parse(reminder.replaceAll("Z$", "+0000"));
    		itemService.addRemindeToItem(itemId, date);
        } catch (ParseException e) {
            throw new IncorrectFormatException();
        }
		
	}
	
	/**
	 * 
	 * delete an item
	 * 
	 * @param itemId
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/lists/{listId}/items/{itemId}")
	public void deleteItem(@PathVariable Integer listId, @PathVariable Integer itemId) {
		this.validateList(listId);
		this.validateItem(itemId);
		itemService.deleteItem(itemId);
	}
	
	
	/**
	 * For validating whether a List is present.
	 * @param listId
	 */
	private void validateList(Integer listId){
		listService.checkListAvailable(listId);
	}

	/**
	 * For validating whether an item is present.
	 * 
	 * @param itemId
	 */
	private void validateItem(Integer itemId){
		itemService.getItem(itemId);
	}

}
