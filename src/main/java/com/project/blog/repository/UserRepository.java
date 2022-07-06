package com.project.blog.repository;

import com.project.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    //SELECT * FROM user WHRER username = 1?;
    Optional<User> findByUsername(String username);

}

//예전에 쓰던 로그인 방식이라 안 씀
// Select * from user Where username = ? and password =?;
//    User findByUsernameAndPassword(String username, String password);
//네이티브 쿼리
//    @Query(value = "Select * from user Where username = ?1 and password =?1", nativeQuery = true)
//    User login(String name, String password);