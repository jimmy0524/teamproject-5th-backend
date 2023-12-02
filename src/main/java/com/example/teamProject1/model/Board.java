package com.example.teamProject1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content;

    @ElementCollection
    private List<String> imageUrls;

    @ManyToOne //글=many , User=One
    @JoinColumn(name = "userId")
    private User user; //DB는 오브젝트를 저장할 수 x, FK자바는 오브젝트를 저장할 수 있다.

    @ManyToOne
    @JoinColumn(name = "subwayStationId")
    private SubwayStation subwayStation;

    @OneToMany(mappedBy = "board",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("board")
    @OrderBy("id asc")
    private List<Reply> replies;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Report> reports;

    @CreationTimestamp
    private Timestamp createDate;

    private Integer likeCount=0; //글 추천수

}
