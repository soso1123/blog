package com.project.blog.config.auth;

import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service //빈 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    //스프링이 로그인 요청을 가로챌 때 username이 DB에 있는지  확인하는 부분
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User principal = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. :"+username));

        return new PrincipalDetail(principal); //시큐리티의 세션에 유저 정보가 저장
    }

}
