package com.monali.microserviceapp.webserver.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
public class WebUserService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	public WebUserService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	public User findByUserName(String username) {
		return restTemplate.getForObject(serviceUrl + "/users/{username}",
				User.class, username);
	}

	public List<User> allUsers() {
		User[] users = null;
		try {
			users = restTemplate.getForObject(serviceUrl + "/users", User[].class);
		}
		catch (HttpClientErrorException e) {
		}
		if (users == null || users.length == 0)
			return null;
		else
			return Arrays.asList(users);
	}

	public User registerUser(User user){
		User currentUser=restTemplate.postForObject(serviceUrl+"/register",user,User.class);
		return currentUser;
	}

	public String login(String username, String password){
		HttpHeaders headers = new HttpHeaders();
		headers.set("username", username);
		headers.set("password", password);

		final HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(serviceUrl+"/login", HttpMethod.GET, entity, String.class);
		String resp=response.getBody();
		return resp;
	}
}
