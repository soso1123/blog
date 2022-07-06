package com.project.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment 사용
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content; //내용이 엄청 길 수도 있음.
                            //섬머 노트 라이브러리를 사용하면 용량이 엄청 길 수도 있음

    @ColumnDefault("0")
    private int count; //조회수

    @ManyToOne(fetch = FetchType.EAGER) //한명의 유저는 여러개의 게시물을 쓸 수 있음. Many = Board, User = One
                                        //유저 정보는 한 건 밖에 없으니까 바로 조인해서 가져옴
    @JoinColumn(name="userId") //실제로 할 DB에 들어갈 때는 userId로 들어가게 될 것이다.
    private User user; //FK를 사용함. DB는 오브젝트가 저장할 수 없는데 자바는 오브젝트를 저장할 수 있음.
                        //자동으로 값을 변환 시켜 줌


    @CreationTimestamp //현재 시간이 자동 삽입
    private Timestamp createDate;


}
