package com.enlume.rest.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enlume.entities.Student;
import com.enlume.entities.Uploaded_Files;
import com.enlume.repositories.FilesRepository;
import com.enlume.repositories.StudentRepository;

@RestController
public class MainController {

	@Autowired
	FilesRepository filesDao;

	@Autowired
	StudentRepository studentDao;

	@RequestMapping(value = "/students", method = RequestMethod.GET)
	@ResponseBody
	public List<Student> getAllStds() {

		List<Student> list = studentDao.findByStatus((byte) 0);

		return list;

	}

	@RequestMapping(value = "/studentsOk", method = RequestMethod.GET)
	@ResponseBody
	public List<Student> getAcceptedStds() {

		List<Student> list = studentDao.findByStatus((byte) 1);

		return list;

	}

	@RequestMapping(value = "/uploadedFiles", method = RequestMethod.GET)
	@ResponseBody
	public List<Uploaded_Files> getAllFiles() {

		List<Uploaded_Files> files = filesDao.findAll();

		return files;

	}

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(@RequestParam(value = "stdid", required = true) Integer stdid) {

		Student st = studentDao.findOne(stdid);

		st.setStatus((byte) 1);

		Student student = studentDao.save(st);

		// System.out.println(student);

		if (student != null)

			return "ok";
		else
			return "error";

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteStudent(@RequestParam("stdid") Integer stdid) {

		Student student = studentDao.findOne(stdid);

		if (student != null) {
			studentDao.delete(student);
			return "Student record deleted successfully.";
		} else {
			return "No record found with given student Id";
		}

	}

}
