package com.monali.microserviceapp.webserver.webapp;

import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebUserController {

	@Autowired
	protected WebUserService userService;


	public WebUserController(WebUserService userService) {
		this.userService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("username", "searchText");
	}

//	@RequestMapping("/")
//	public String goHome() {
//		return "index";
//	}
	@RequestMapping("/users/{username}")
	public User byUserName(Model model, @PathVariable("username") String username) {

		User user = userService.findByUserName(username);
		//model.addAttribute("User", user);
		return user;
	}

	@RequestMapping("/users")
	public List<User> allUsers(Model model) {

		List<User> users = userService.allUsers();
		if (users != null)
			model.addAttribute("users", users);
		return users;
	}

	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public ResponseEntity<String> registerUser(Model model, @RequestBody User user) {
		userService.registerUser(user);
		//model.addAttribute("User", user);
		return ResponseEntity.status(HttpStatus.OK).body("Registerd Successfully");
	}

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ResponseEntity<String>login(@RequestHeader(value="username") String username,
									   @RequestHeader (value="password") String password){
		String resp= userService.login(username,password);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
}
