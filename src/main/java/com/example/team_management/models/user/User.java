package com.example.team_management.models.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    private int user_id;
    private String user_name;
    private String access_token;
    private String refresh_token;
    private LocalDate access_expires_at;
    private LocalDate refresh_expires_at;
    private String password;
    private String password_salt;
    private String email;
    private int permission_level;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private LocalDateTime delete_at;

}
