package com.enlume.rest.test;

import static org.mockito.Mockito.*;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.enlume.entities.Student;
import com.enlume.entities.Uploaded_Files;
import com.enlume.repositories.FilesRepository;
import com.enlume.repositories.StudentRepository;
import com.enlume.rest.controllers.ProcessExcelSheet;

public class ProcessExcelTest {
	
	private MockMvc mockMvc;

	@Mock
	private FilesRepository filesRepository;
	
	@Mock
	private Student student;
	
	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private ProcessExcelSheet excelController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(excelController).build();
	}
	
	/*
	 * 
	 * 
	 */
	
	@Test
	public void uploadExcelFileIsOk() throws Exception {
		List<Student> list = new ArrayList<Student>();
		list.add(new Student(111, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)1));
		list.add(new Student(122, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)1));
		list.add(new Student(131, "xyz11", "xyz12", "xyz@gmail.com", "Java", (byte)1));
		
		 
		MockMultipartFile myFile = new MockMultipartFile("file", "Students.xlsx", "multipart/form-data", "some data".getBytes());		
		
		Uploaded_Files up_file = new Uploaded_Files();
		up_file.setFile_name("students.xlsx");
		up_file.setDate("somedate");
		up_file.setUplaodBy("Admin");
		
		when(filesRepository.save(up_file)).thenReturn(new Uploaded_Files());
		when(student.processExcelStudents(myFile)).thenReturn(list);
		
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/processExcel").file(myFile).contentType(MediaType.MULTIPART_FORM_DATA)
	            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void uploadExcelFileIsError() throws Exception {
		//when(student.processExcelStudents(myFile)).thenReturn(list);
		
		List<Student> list = new ArrayList<Student>();
		
		MockMultipartFile myFile = new MockMultipartFile("file", "", "", "some data".getBytes());
		
		when(student.processExcelStudents(any(MockMultipartFile.class))).thenReturn(list);
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/processExcel").file(myFile).contentType(MediaType.MULTIPART_FORM_DATA)
	            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("Error occured at file uploading.."));;
	}
	
	@Test
	public void uploadExcelFileIsException() throws Exception {
		//when(student.processExcelStudents(myFile)).thenReturn(list);
		
		MockMultipartFile myFile = new MockMultipartFile("file", "", "", "some data".getBytes());
		
		when(student.processExcelStudents(any(MockMultipartFile.class))).thenThrow(new RuntimeException());
		
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/processExcel").file(myFile).contentType(MediaType.MULTIPART_FORM_DATA)
	            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("Please upload valid Excel file consists Student Records."));;
	}
	
	
}
