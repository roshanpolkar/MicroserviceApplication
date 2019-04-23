package com.monali.microserviceapp.usermanagementservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
    public User findByUserName(String username);

    public List<User> findAll();

    @Query("Select u from User u where u.userName=:#{#username} and u.password = :#{#password}")
    public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
