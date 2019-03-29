package com.seam.messages;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seam.messages.pojo.Message;

/**
 * Here we can use WebTestClient or TestRestTemplate to check web component i
 * don't think we need to use these two for business as such so i used simple
 * mockMVC & we can use Mock and power mock but we really don't have any DB so i
 * provided test case for only controller
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageReaderControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void getLatestMessageTest() throws Exception {
		String subscriberId = "9020be9e-958a-4536-8164-4a8bbc17c3da";
		MvcResult result = mockMvc.perform(
				get("/messages/latest").param("subscriberId", subscriberId).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Message response = om.readValue(resultContent, Message.class);
		Assert.assertTrue(response.getBody().isEmpty() == Boolean.FALSE);

	}

	@Test
	public void getFirstUnreadMessageTest() throws Exception {
		String subscriberId = "9020be9e-958a-4536-8164-4a8bbc17c3da";
		MvcResult result = mockMvc.perform(
				get("/messages/unread").param("subscriberId", subscriberId).content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		Message response = om.readValue(resultContent, Message.class);
		Assert.assertTrue(response.getBody().isEmpty() == Boolean.FALSE);

	}

}
