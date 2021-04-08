/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.repository;

import com.javatechie.report.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author herli
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, String>{
    
    @Query(value="SELECT * FROM users where user_id = ?1", nativeQuery = true)
    public Users findUserById(@Param ("id") String id);
    
}
