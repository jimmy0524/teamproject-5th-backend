package com.example.teamProject1.Dto;

import com.example.teamProject1.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPasswordDto {
    private int id;
    private String username;
    private String nickname;
    private RoleType role;
    private Timestamp createDate;
}
