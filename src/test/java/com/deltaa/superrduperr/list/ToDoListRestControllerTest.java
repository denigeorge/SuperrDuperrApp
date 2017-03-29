package com.deltaa.superrduperr.list;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperrDuperrAppApplication.class)
@WebAppConfiguration
public class ToDoListRestControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    
	private ObjectMapper mapper = new ObjectMapper();
    
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private ToDoListRepo toDoListRepo;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        List<ToDoList> toDoLists = (List<ToDoList>) toDoListRepo.findAll();
        toDoListRepo.delete(toDoLists);
    }

    @After
    public void clear() throws Exception {
        List<ToDoList> toDoLists = (List<ToDoList>) toDoListRepo.findAll();
        toDoListRepo.delete(toDoLists);
    }
    
    
    @Test
    public void emptyLists() throws Exception {
        mockMvc.perform(get("/lists")
                .contentType(contentType))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateNonExistentList() throws Exception {
    	
    	ToDoList list = new ToDoList();
    	list.setName("update nonexistent");
    	list.setDescription("updating non existent list");
    	list.setId(1);
        mockMvc.perform(put("/lists/1")
        		.content(json(list))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNonExistentList() throws Exception {
    	
    	ToDoList list = new ToDoList();
    	list.setName("delete nonexistent");
    	list.setDescription("updating non existent list");
    	list.setId(1);
        mockMvc.perform(delete("/lists/1")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void addList() throws Exception {
    	
    	ToDoList list = new ToDoList();
    	list.setName("new ToDo List");
    	list.setDescription("New ToDo list for home chores");
    	list.setId(0);
    	
        mockMvc.perform(post("/lists")
        		.content(json(list))
                .contentType(contentType))
                .andExpect(status().isOk());
    }
    
    @Test
    public void updateList() throws Exception {
    	
    	ToDoList list = new ToDoList();
    	list.setName("Home ToDo List");
    	list.setDescription("New ToDo list for home chores");
    	list.setId(0);
    	mockMvc.perform(post("/lists")
        		.content(json(list))
                .contentType(contentType));

    	list = new ToDoList();
    	list.setName("Office ToDo List");
    	list.setDescription("New ToDo list for office assignments");
    	list.setId(0);
    	mockMvc.perform(post("/lists")
        		.content(json(list))
                .contentType(contentType));

    	list = new ToDoList();    	
    	list.setName("General ToDo List");
    	list.setDescription("New ToDo list for general activities");
    	list.setId(0);
    	mockMvc.perform(post("/lists")
        		.content(json(list))
                .contentType(contentType));

    	
    	ToDoList updatedlist = new ToDoList();
    	updatedlist.setName("new ToDo List Updated");
    	updatedlist.setDescription("New ToDo list for home chores description updated");
    	updatedlist.setId(3);
    	
    	Integer maxId = getMaxListIdAvailable();
    	
        mockMvc.perform(put("/lists/" + maxId)
        		.content(json(updatedlist))
                .contentType(contentType))
                .andExpect(status().isOk());
        
    	MvcResult result = mockMvc.perform(get("/lists")
        		.content(json(list))
                .contentType(contentType))
    			.andReturn();
   	
    	String listStr = result.getResponse().getContentAsString();
    	Assert.assertTrue(listStr.contains("updated"));
    }
    
    protected String json(Object o) throws IOException {
    	MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
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
				if (a.getId() > maxId){
					maxId = a.getId();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxId;
	}
}
