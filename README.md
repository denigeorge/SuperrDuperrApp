# SuperrDuperrApp - ToDo List Application

Requirements:
- Add items to a list
- Mark an item as Completed
- Ability to delete items
- Ability to restore items
- Support for multiple lists
- Ability to tag items within a list 
- Ability to add reminders to items

Technologies Used:
- Spring Boot
- DB: Apache Derby

Structure:
src/main: 
 SuperrDuperrAppApplication.java 	-- Spring boot application class
 
 list:					-- ToDo List related classes
    ToDoList.java				-- Model class
    ToDoListController.java			-- Controller class
    ToDoListRepo.java				-- Repository for the model
    ToDoListService.java			-- Business Service Class

 item: 					-- ToDo Item related classes
    ToDoItem.java				-- Model class
    ToDoItemController.java     		-- Controller class
    ToDoItemRepo.java           		-- Repository for the model
    ToDoItemService.java        		-- Business Service Class

 exceptions:				-- Custom Exceptions 
    IncorrectFormatException.java
    ItemNotFoundException.java
    ListNotFoundException.java
    NoItemsException.java
    NoListException.java

 generic:				-- Enum for the ToDo Item status
    Status.java

 resources:				-- Application properties
    application.properties

 test:  					-- Test classes for Junit.
    SuperrDuperrAppApplicationTests.java
    item/ToDoItemRestControllerTest.java
    list/ToDoListRestControllerTest.java
