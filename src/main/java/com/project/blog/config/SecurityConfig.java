package com.project.blog.config;

import com.project.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //빈등록 (IoC관리)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //스프링 시큐리티 핑터가 스프링 필터체인에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean //IoC가 됨 => 리턴 값을 스프링이 관리함 => 필요할 때마다 가져와서 쓰기만 하면 된다.
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }
    //함수를 호출하면 객체 리턴 받음
    //BCryptPasswordEncoder가 들어오는 값을 암호화 해서 넣어주는 역할을 함

    //시큐리티가 대신 로그인해주는데, password를 가로채기를 하는데
    //해당 password가 뭘로 해쉬가 돼서 회원가입이 되었는지 알려줘야 같은 해쉬로 암호화 해서 DB에서 비교함
    //security config에서는 기본적으로 지원해 준다.


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //csrf 토큰 비활성화는 .csrf().disable()로 걸어두면 됨. 테스트 때는 걸어두는 게 좋음
        http
                .authorizeRequests()
                    .antMatchers("/","/auth/**", "/js/**", "/css/**", "/image/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm")
                    .loginProcessingUrl("/auth/loginProc")//Spring Seurity가 로그인을 가로채서 대신 로그인
                    .defaultSuccessUrl("/")
                    .failureUrl("/auth/loginForm")
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
