package com.deltaa.superrduperr.item;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.deltaa.superrduperr.SuperrDuperrAppApplication;
import com.deltaa.superrduperr.generic.Status;
import com.deltaa.superrduperr.list.ToDoList;
import com.deltaa.superrduperr.list.ToDoListRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperrDuperrAppApplication.class)
@WebAppConfiguration
public class ToDoItemRestControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ToDoListRepo toDoListRepo;

	@Autowired
	private ToDoItemRepo toDoItemRepo;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		List<ToDoItem> toDoItems = (List<ToDoItem>) toDoItemRepo.findAll();
		toDoItemRepo.delete(toDoItems);
		List<ToDoList> toDoLists = (List<ToDoList>) toDoListRepo.findAll();
		toDoListRepo.delete(toDoLists);

	}

	@After
	public void clear() throws Exception {
		List<ToDoItem> toDoItems = (List<ToDoItem>) toDoItemRepo.findAll();
		toDoItemRepo.delete(toDoItems);
		List<ToDoList> toDoLists = (List<ToDoList>) toDoListRepo.findAll();
		toDoListRepo.delete(toDoLists);
	}

	@Test
	public void emptyItems() throws Exception {
		ToDoList list = new ToDoList();
		list.setName("Home ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);
		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType));
		Integer maxId = getMaxListIdAvailable();
		mockMvc.perform(get("/lists/" + maxId + "/items").contentType(contentType)).andExpect(status().isNoContent());
	}

	@Test
	public void updateNonExistentItem() throws Exception {
		ToDoList list = new ToDoList();
		list.setName("Home ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);
		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType));

		ToDoItem item = new ToDoItem();
		item.setName("update nonexistent item");
		item.setDescription("updating non existent item desription");
		item.setId(1);
		Integer maxId = getMaxListIdAvailable();
		mockMvc.perform(put("/lists/" + maxId + "/items/1").content(json(list)).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteNonExistentItem() throws Exception {

		ToDoList list = new ToDoList();
		list.setName("Home ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);
		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType));

		ToDoList item = new ToDoList();
		item.setName("delete nonexistent");
		item.setDescription("updating non existent list");
		item.setId(1);
		Integer maxId = getMaxListIdAvailable();
		mockMvc.perform(delete("/lists/" + maxId + "/items/1").contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void addItem() throws Exception {

		ToDoList list = new ToDoList();
		list.setName("new ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);

		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType)).andExpect(status().isOk());

		Integer maxId = getMaxListIdAvailable();

		ToDoItem item = new ToDoItem();
		item.setName("new item in Home ToDo list");
		item.setDescription("new item in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

	}

	@Test
	public void updateItem() throws Exception {

		ToDoList list = new ToDoList();
		list.setName("Home ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);
		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType));

		Integer maxId = getMaxListIdAvailable();

		ToDoItem item = new ToDoItem();
		item.setName("item 1 in Home ToDo list");
		item.setDescription("item 1 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		item = new ToDoItem();
		item.setName("item 2 in Home ToDo list");
		item.setDescription("item 2 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		ToDoItem updatedItem = new ToDoItem();
		updatedItem.setName("item 2 in Home ToDo list updated");
		updatedItem.setDescription("item 2 in Home ToDo list description updated");
		updatedItem.setId(1);

		Integer maxItemId = getMaxItemIdAvailable(maxId);

		mockMvc.perform(
				put("/lists/" + maxId + "/items/" + maxItemId).content(json(updatedItem)).contentType(contentType))
				.andExpect(status().isOk());

		MvcResult result = mockMvc.perform(get("/lists/" + maxId + "/items").contentType(contentType)).andReturn();
		String listStr = result.getResponse().getContentAsString();

		Assert.assertTrue(listStr.contains("updated"));
	}

	@Test
	public void addTag() throws Exception {
		ToDoList list = new ToDoList();
		list.setName("Home ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);
		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType));

		Integer maxId = getMaxListIdAvailable();

		ToDoItem item = new ToDoItem();
		item.setName("item 1 in Home ToDo list");
		item.setDescription("item 1 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		item = new ToDoItem();
		item.setName("item 2 in Home ToDo list");
		item.setDescription("item 2 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		Integer maxItemId = getMaxItemIdAvailable(maxId);

		mockMvc.perform(put("/lists/" + maxId + "/items/" + maxItemId).param("tag", "tag1").contentType(contentType))
				.andExpect(status().isOk());

		MvcResult result = mockMvc.perform(get("/lists/" + maxId + "/items").contentType(contentType)).andReturn();
		String listStr = result.getResponse().getContentAsString();

		Assert.assertTrue(listStr.contains("tag1"));
	}

	@Test
	public void addReminder() throws Exception {
		ToDoList list = new ToDoList();
		list.setName("Home ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);
		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType));

		Integer maxId = getMaxListIdAvailable();

		ToDoItem item = new ToDoItem();
		item.setName("item 1 in Home ToDo list");
		item.setDescription("item 1 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		item = new ToDoItem();
		item.setName("item 2 in Home ToDo list");
		item.setDescription("item 2 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		Integer maxItemId = getMaxItemIdAvailable(maxId);

		mockMvc.perform(put("/lists/" + maxId + "/items/" + maxItemId).param("reminder", "2017-03-29T08:00:00Z")
				.contentType(contentType)).andExpect(status().isOk());

		MvcResult result = mockMvc.perform(get("/lists/" + maxId + "/items/" + maxItemId).contentType(contentType))
				.andReturn();
		String listStr = result.getResponse().getContentAsString();

		ToDoItem toDoItem = mapper.readValue(listStr, ToDoItem.class);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		String dateInString = "2017-03-29T08:00:00Z";

		try {

			Date date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));

			Assert.assertTrue(toDoItem.getReminder().compareTo(date) == 0);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void changeStatus() throws Exception {
		ToDoList list = new ToDoList();
		list.setName("Home ToDo List");
		list.setDescription("New ToDo list for home chores");
		list.setId(0);
		mockMvc.perform(post("/lists").content(json(list)).contentType(contentType));

		Integer maxId = getMaxListIdAvailable();

		ToDoItem item = new ToDoItem();
		item.setName("item 1 in Home ToDo list");
		item.setDescription("item 1 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		item = new ToDoItem();
		item.setName("item 2 in Home ToDo list");
		item.setDescription("item 2 in Home ToDo list description");
		item.setId(1);

		mockMvc.perform(post("/lists/" + maxId + "/items").content(json(item)).contentType(contentType))
				.andExpect(status().isOk());

		Integer maxItemId = getMaxItemIdAvailable(maxId);

		mockMvc.perform(
				put("/lists/" + maxId + "/items/" + maxItemId).param("status", "ACTIVE").contentType(contentType))
				.andExpect(status().isOk());

		MvcResult result = mockMvc.perform(get("/lists/" + maxId + "/items/" + maxItemId).contentType(contentType))
				.andReturn();
		String listStr = result.getResponse().getContentAsString();

		ToDoItem toDoItem = mapper.readValue(listStr, ToDoItem.class);

		Assert.assertTrue(toDoItem.getStatus().equals(Status.ACTIVE));

	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	protected Integer getMaxListIdAvailable() {
		Integer maxId = 0;
		try {

			MvcResult result = null;
			result = mockMvc.perform(get("/lists").contentType(contentType)).andReturn();

			String listStr = result.getResponse().getContentAsString();

			List<ToDoList> toDoLists = mapper.readValue(listStr, new TypeReference<List<ToDoList>>() {
			});

			for (ToDoList a : toDoLists) {
				if (a.getId() > maxId) {
					maxId = a.getId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxId;
	}

	protected Integer getMaxItemIdAvailable(Integer listId) {
		Integer maxId = 0;
		try {

			MvcResult result = null;
			result = mockMvc.perform(get("/lists/" + listId + "/items").contentType(contentType)).andReturn();

			String listStr = result.getResponse().getContentAsString();

			List<ToDoItem> toDoItems = mapper.readValue(listStr, new TypeReference<List<ToDoItem>>() {
			});

			for (ToDoItem a : toDoItems) {
				if (a.getId() > maxId) {
					maxId = a.getId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxId;
	}
}
