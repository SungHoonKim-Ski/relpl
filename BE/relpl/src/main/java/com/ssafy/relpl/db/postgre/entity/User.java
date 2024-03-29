package com.ssafy.relpl.db.postgre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ruser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_uid", nullable = false, length = 30)
    private String userUid;

    @Column(name = "user_nickname", nullable = false, length = 30)
    private String userNickname;

    @Column(name = "user_password", nullable = false, length = 200)
    private String userPassword;

    @Column(name = "user_phone", nullable = false, length = 50)
    private String userPhone;

    @Column(name = "user_image", length = 255)
    private String userImage;

    @Column(name = "user_isactive", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean userIsActive;

    // 다대일(N:1) 양방향 관계에서 여러 개의 Report 엔티티를 갖도록 설정
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Report> report;
}
