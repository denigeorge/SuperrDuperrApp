package com.deltaa.superrduperr.list;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author denigeorge
 *	Controller for ToDo list related operations. Like adding a list, listing all the ToDo lists.
 */
@RestController
public class ToDoListController {
	
	static Logger logger = Logger.getLogger(ToDoListController.class.getName());

	@Autowired
	private ToDoListService listService;
	
	/**
	 * Get all the lists in the system.
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/lists")
	public List<ToDoList> getAllLists(){
		return listService.getAllLists();
	}
	
	/**
	 * Getting a particular list with an id.
	 * @param id
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/lists/{id}")
	public ToDoList getList(@PathVariable Integer id){
		return listService.getList(id);
	}
	
	/**
	 * Adding a list to the system.
	 * @param newlist
	 */
	@RequestMapping(method=RequestMethod.POST, value="/lists")
	public void addList(@RequestBody ToDoList newlist){
		listService.addList(newlist);
	}
	
	
	/**
	 * Updating a particular list.
	 * @param list
	 * @param id
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/lists/{id}")
	public void updateList(@RequestBody ToDoList list, @PathVariable Integer id){
		this.validateList(id);
		listService.updateList(id, list);
	}
	
	/**
	 * Deleting a particular list.
	 * @param id
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/lists/{id}")
	public void deleteList(@PathVariable Integer id){
		this.validateList(id);
		listService.deleteList(id);
	}
	
	
	/**
	 * For validating whether a list with listId is available in the system.
	 * @param listId
	 */
	private void validateList(Integer listId){
		listService.checkListAvailable(listId);
	}
	
}
