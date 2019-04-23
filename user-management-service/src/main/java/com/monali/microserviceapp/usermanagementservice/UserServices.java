package com.monali.microserviceapp.usermanagementservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Service
public class UserServices{

   @Autowired
   UserRepository userRepository;

    public boolean isAuthenticatedUser(String userName, String password){
        boolean flag=false;
        User user = userRepository.findByUserName(userName);
        try{
            if (userName.equalsIgnoreCase(user.getUserName())&& password.equals(user.getPassword())) {
                flag= true;
            }
        }catch(Exception e){
            return  flag;
        }
        return flag;
    }
}
