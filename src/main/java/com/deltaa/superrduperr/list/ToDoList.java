package com.deltaa.superrduperr.list;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author denigeorge
 * 
 * Model for the ToDo lists. We can have multiple lists of ToDo items.
 * 
 */
@Entity
public class ToDoList {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String description;
	
	public ToDoList() {
	}

	public ToDoList(Integer id, String listName, String desc) {
		super();
		this.id = id;
		this.name = listName;
		this.description = desc;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
}
