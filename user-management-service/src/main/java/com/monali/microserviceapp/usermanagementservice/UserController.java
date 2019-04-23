package com.monali.microserviceapp.usermanagementservice;

import com.monali.microserviceapp.usermanagementservice.User;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.HeaderParam;
import java.util.Base64;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServices userServices;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<User>addUser(@RequestBody User user) {
        try{
            userRepository.save(user);
            System.out.println("User with usename: "+user.getUserName()+" added");}
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @RequestMapping(value="/users", method= RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return  ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @RequestMapping(value = "/edituser",method = RequestMethod.PUT)
    public ResponseEntity< User > editUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping(value = "/users/{username}",method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String username){
       User user= userRepository.findByUserName(username);
       return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public ResponseEntity<String> login(@RequestHeader(value="username") String username,
                                        @RequestHeader(value="password") String password) {
        if (null == username || null == password)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Username or password");
        boolean isAuthenticated = userServices.isAuthenticatedUser(username,password);
        if (!isAuthenticated){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Username or password");
        }
        String token=username+password;
        ResponseEntity<String> responseEntity=ResponseEntity.status(HttpStatus.OK).body(token);
        return responseEntity;
    }
}
