package com.enlume.rest.test;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.enlume.entities.Accounts;
import com.enlume.repositories.AccountRepository;
import com.enlume.rest.controllers.UserController;
import com.enlume.rest.test.utils.TestUtil;

public class UserTest {

	private MockMvc mockMvc;

	@Mock
	private AccountRepository userRepository;

	@InjectMocks
	private UserController userController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testUserCreatNotOk() throws Exception {

		Accounts user = new Accounts();
		user.setId(Integer.MAX_VALUE);
		user.setPassword("admin");
		user.setUsername("Admin");

		when(userRepository.findOne(Integer.MAX_VALUE)).thenReturn(null);

		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(TestUtil.asJsonString(user)))
				.andExpect(status().isNoContent());

	}

	@Test
	public void testUserCreatOk() throws Exception {
		Accounts user = new Accounts();
		user.setId(101);
		user.setUsername("admin");

		// when(userRepository.findOne(101)).thenThrow(new RuntimeException());
		when(userRepository.save(user)).thenReturn(user);

		this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(TestUtil.asJsonString(user)))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testLoginOk() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("noSession", "Hello");
		
		Accounts user = new Accounts();
		user.setId(101);
		user.setUsername("Admin");
		user.setPassword("admin");
		
		when(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON).content(TestUtil.asJsonString(user)))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.username").exists()).andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.username").value("Admin"))
				.andExpect(jsonPath("$.password").value("admin")).andDo(print());
	}
	
	@Test
	public void testLoginNotOk() throws Exception {
		Accounts user = new Accounts();
		
		when(userRepository.findByUsernameAndPassword(null, null)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON).content(TestUtil.asJsonString(user)))
				.andDo(print()).andExpect(status().isNoContent());
	}

}
