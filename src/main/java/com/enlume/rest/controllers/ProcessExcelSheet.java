package com.enlume.rest.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.enlume.entities.Student;
import com.enlume.entities.Uploaded_Files;
import com.enlume.repositories.FilesRepository;
import com.enlume.repositories.StudentRepository;

@RestController
public class ProcessExcelSheet {

	@Autowired
	private StudentRepository studentDao;
	
	@Autowired
	private Student student;

	@Autowired
	private FilesRepository filesDao;


	@RequestMapping(value = "/processExcel", method = RequestMethod.POST)
	@ResponseBody
	public String processExcel(Model model, MultipartHttpServletRequest mRequest) {

		System.out.println("Hello..");

		try {

			mRequest.getParameterMap();

			Iterator<String> itr = mRequest.getFileNames();

			String file_name = (String) itr.next();

			MultipartFile excelfile = mRequest.getFile(file_name);

			String fileName = excelfile.getOriginalFilename();

			System.out.println(fileName);

			List<Student> lstStudents = student.processExcelStudents(excelfile);

			studentDao.save(lstStudents);
			
			System.out.println("Hello2:: " + lstStudents.size());

			Uploaded_Files file = new Uploaded_Files();
			file.setFile_name(fileName);
			file.setRecords(lstStudents.size());
			file.setDate(new SimpleDateFormat("dd-MMM-yyyy hh:mm a").format(new Date()));
			file.setUplaodBy("Admin");
			
			
			file = filesDao.save(file);
			
			System.out.println("file::"+file);
			
			if(file != null) {
				return "File uploaded successfully";
			} else {
				return "Error occured at file uploading..";
			}

			// model.addAttribute("lstUser", lstUser);
		} catch (Exception e) {

			e.printStackTrace();
			return "Please upload valid Excel file consists Student Records.";
		}
	}

}
