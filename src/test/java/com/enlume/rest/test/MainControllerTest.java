package com.enlume.rest.test;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.enlume.entities.Student;
import com.enlume.entities.Uploaded_Files;
import com.enlume.repositories.FilesRepository;
import com.enlume.repositories.StudentRepository;
import com.enlume.rest.controllers.MainController;

public class MainControllerTest {

	MockMvc mockMvc;

	@Mock
	private StudentRepository studentRepository;
	
	@Mock
	private FilesRepository filesRepository;

	@InjectMocks
	private MainController mainController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
	}

	@Test
	public void testGetAllStdsOk() throws Exception {
		List<Student> list = new ArrayList<Student>();
		list.add(new Student(111, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)0));
		list.add(new Student(122, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)0));
		list.add(new Student(131, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)0));
		
		when(studentRepository.findByStatus((byte)0)).thenReturn(list);
		
		mockMvc.perform(get("/students").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(3)));
		
		
	}
	
	@Test
	public void testGetAllStdsReturnEmpty() throws Exception {
			
		List<Student> list = new ArrayList<Student>();
		
		when(studentRepository.findByStatus((byte)0)).thenReturn(list);
		
		mockMvc.perform(get("/students").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(0)));	
		
	}
	
	@Test
	public void testGetStatusAcceptedStdsOk() throws Exception {
		List<Student> list = new ArrayList<Student>();
		list.add(new Student(111, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)1));
		list.add(new Student(122, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)1));
		list.add(new Student(131, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)1));
		
		when(studentRepository.findByStatus((byte)1)).thenReturn(list);
		
		mockMvc.perform(get("/studentsOk").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(3)));
		
		
	}
	
	@Test
	public void testGetStatusAcceptedStdsEmpty() throws Exception {	
		List<Student> list = new ArrayList<Student>();
		
		when(studentRepository.findByStatus((byte)1)).thenReturn(list);
		
		mockMvc.perform(get("/studentsOk").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(0)));	
		
	}
	
	
	@Test
	public void testGetAllFilesOk() throws Exception {
		List<Uploaded_Files> list = new ArrayList<Uploaded_Files>();
		list.add(new Uploaded_Files());
		list.add(new Uploaded_Files());
		
		
		when(filesRepository.findAll()).thenReturn(list);
		
		mockMvc.perform(get("/uploadedFiles").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)));
		
		
	}
	
	@Test
	public void testGetAllFilesEmpty() throws Exception {	
		List<Uploaded_Files> list = new ArrayList<Uploaded_Files>();
		
		when(filesRepository.findAll()).thenReturn(list);
		
		mockMvc.perform(get("/uploadedFiles").accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(0)));	
		
	}
	
	@Test
	public void testStatusUpdateIsOk() throws Exception  {
		
		Student student = new Student();
		student.setId(101);
		student.setStatus((byte)0);
		
		when(studentRepository.findOne(101)).thenReturn(student);
		
		student.setStatus((byte)1);
		
		when(studentRepository.save(student)).thenReturn(student);
		
		
		mockMvc.perform(post("/status").accept(MediaType.APPLICATION_JSON).param("stdid", "101")).andExpect(status().isOk()).
		andExpect(content().string("ok"));
	}
	
	@Test
	public void testStatusUpdateIsEmpty() throws Exception  {
		
		Student student = new Student();
		student.setId(101);
		student.setStatus((byte)0);
		
		when(studentRepository.findOne(101)).thenReturn(student);
		
		when(studentRepository.save(student)).thenReturn(null);
		
		
		mockMvc.perform(post("/status").accept(MediaType.APPLICATION_JSON).param("stdid", "101")).andDo(print()).andExpect(status().isOk()).
		andExpect(content().string("error"));
	}
	
	@Test
	public void testDeleteStudentIsOk() throws Exception  {
		
		Student student = new Student();
		student.setId(101);
		student.setStatus((byte)0);
		
		when(studentRepository.findOne(101)).thenReturn(student);
		
		
		mockMvc.perform(post("/delete").accept(MediaType.APPLICATION_JSON).param("stdid", "101")).andExpect(status().isOk()).
		andExpect(content().string("Student record deleted successfully."));
	}
	
	@Test
	public void testDeleteStudentIsEmpty() throws Exception  {
		
		
		when(studentRepository.findOne(Integer.MAX_VALUE)).thenReturn(null);		
		
		mockMvc.perform(post("/delete").accept(MediaType.APPLICATION_JSON).param("stdid", "10109019")).andExpect(status().isOk()).
		andExpect(content().string("No record found with given student Id"));
	}
	
	

}
