package com.deltaa.superrduperr.item;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.deltaa.superrduperr.generic.Status;
import com.deltaa.superrduperr.list.ToDoList;

/**
 * @author denigeorge
 * Model for the ToDo Items in a list. We can have multiple items in a ToDo list.
 */
@Entity
public class ToDoItem {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	private Status status;
	private Date reminder;
	private String tag;
	
	@ManyToOne
	private ToDoList toDoList;
	
	public ToDoItem() {
		
	}

	public ToDoItem(Integer id, String listName, String desc, Integer listId) {
		super();
		this.id = id;
		this.name = listName;
		this.description = desc;
		this.toDoList = new ToDoList(listId, "", "");
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public ToDoList getToDoList() {
		return toDoList;
	}
	public void setToDoList(ToDoList toDoList) {
		this.toDoList = toDoList;
	}
	public Date getReminder() {
		return reminder;
	}
	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
