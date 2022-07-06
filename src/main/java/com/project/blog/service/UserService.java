package com.project.blog.service;

import com.project.blog.model.RoleType;
import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user){

        String rawPassword = user.getPassword(); //비밀번호 원문
        String encPassword = encoder.encode(rawPassword); //비밀번호 해쉬
        user.setPassword(encPassword); //패스워드가 해쉬로 바뀌어서 들어가서 저장이 됨.
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

}

//예전에 쓰던 로그인 방식이라 삭제
//    @Transactional(readOnly = true) //select할 때 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료하는데 그때까지의 정합성 유지
//    public User 로그인(User user){
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }