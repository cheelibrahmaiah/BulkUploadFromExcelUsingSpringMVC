package com.enlume.rest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enlume.entities.Accounts;
import com.enlume.repositories.AccountRepository;

@RestController
public class UserController {

	@Autowired
	AccountRepository userDao;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Accounts> createUser(@RequestBody Accounts user) {
		// System.out.println(req.getParameterNames().nextElement());
		Accounts userDb = userDao.save(user);

		System.out.println(userDb);

		if (userDb == null) {
			return new ResponseEntity<Accounts>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Accounts>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Accounts> loginValidateUser(HttpSession session, Accounts user) {
		// System.out.println(req.getParameterNames().nextElement());
		Accounts dbUser = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());

		if (dbUser != null) {
			session.setAttribute("username", user.getUsername());
			return new ResponseEntity<Accounts>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<Accounts>(HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	@ResponseBody
	public String logoutUser(HttpServletRequest req, HttpSession session) {
		System.out.println(req.getParameter("username"));
		session.invalidate();

		return "Successfully, Logged out.";
	}

	@RequestMapping(value = "/currentUser", method = RequestMethod.POST)
	@ResponseBody
	public String currentUser(HttpSession session) {

		String username = (String) session.getAttribute("username");

		if (username == null) {
			username = "Anonymous";
		}

		return username;
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<Accounts> getUser(@PathVariable Integer id) {

		Accounts user = userDao.findOne(id);

		if (user == null) {
			return new ResponseEntity<Accounts>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Accounts>(user, HttpStatus.OK);
	}

}
