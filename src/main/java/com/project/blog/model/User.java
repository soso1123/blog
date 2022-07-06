package com.project.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴

//ORM -> JAVA(다른언어 포함)에 있는 Object -> 테이블로 매핑해 주는 기술
//object만 만들면 jpa가 table로 만들어준다.
@Entity //User 클래스가 데이터베이스에 테이블이 생성된다.
public class User {

    @Id //primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략에 따라감

    private int id; //시퀀스, auto_increment, 자동으로 들어가게 될 것임

    @Column(nullable = false, length = 30)
    private String username; //사용자 아이디

    @Column(nullable = false, length = 100) //패스워드를 해쉬로 변경해서 암호화 할 거라서 크게 함
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("'user'")
    @Enumerated(EnumType.STRING) //DB는 RoleType이라는 게 없으니 써줌
    private RoleType role; //admin이나 user, manager처럼 권한을 줄 수 있음.
                        // 역할 중에 하나만 들어갈 수 있게 도메인 설정 해야 함
                        //"" 안에 문자열로 인식하려면 '' 하나 더 해 줘야 함

    @CreationTimestamp //시간이 자동으로 입력된다.
    private Timestamp createDate; //회원 정보 수정하는 updateDate도 필요하긴 한데 일단 create만 사용
}
