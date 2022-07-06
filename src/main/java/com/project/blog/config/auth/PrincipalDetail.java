package com.project.blog.config.auth;

import com.project.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//스프링시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션 저장소에 저장 해 준다.
@Getter
public class PrincipalDetail implements UserDetails {
    private User user; //컴포지션(객체를 품고 있는 것을 말함)

    public PrincipalDetail(User user){
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //계정이 만료되지 않았는지를 리턴한다.(true : 만료가 안 됨)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정이 잠겨있는지 리턴한다. (true : 잠기지 않음)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호가 만료되지 않았는지 리턴한다. (true : 만료안됨)
        return true;
    }

    @Override
    public boolean isEnabled() { //계정 활성화인지 리턴한다. (true : 활성화)
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //계정의 권한 목록을 리턴한다.(권한이 여러개면 for문 사용)

        Collection<GrantedAuthority> collectors = new ArrayList<>();


        collectors.add(()-> "ROLE_"+user.getRole() );
        //스프링에서 롤을 반환할 때의 규칙이라 "ROLE_"을 적어줘야 한다.
        //위의 문법을 람다식으로 표현한 것

        return collectors;
    }

}

//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_"+user.getRole();
//            }
//        });