package com.monali.microserviceapp.usermanagementservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(UserConfiguration.class)
public class UserManagementServiceApplication implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        System.setProperty("spring.config.name","user-server");
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) throws Exception {

        //System.out.println("DATASOURCE = " + userRepository.);

        System.out.println("\n1.findAllUsers()...");
        for (User user : userRepository.findAll()) {
            System.out.println(user.getUserName());
        }

        System.out.println("\n2.findByUserName(String email)...");
        User user = userRepository.findByUserName("monalishinde");
        System.out.println(user.getUserName());

        System.out.println("\n3.findByUsernameandPassword()...");
        User customuser = userRepository.findByUsernameAndPassword("monalishinde","monalishinde");
        System.out.println(customuser.getUserName());
        System.out.println("Done!");



    }

}