package com.example.teamProject1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 100, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 123456 => 해쉬로 변경(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //	@ColumnDefault("user")
    // DB는 RoleType이라는게 없다
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는게 좋다.
    // admin, user, manager에 따라 권한을 줌 근데 String 타입이면 실수로 managerrr등 넣을 수 있다
    // 하지만 Enum을 쓰면 3개 값 중 하나를 넣을 수 있도록 도메인 설정 할 수 있음
    @CreationTimestamp // 시간 자동 입력
    private Timestamp createDate;
}