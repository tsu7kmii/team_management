package com.example.team_management.models.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    private int user_id;
    private String user_name;
    private String access_token;
    private String refresh_token;
    private LocalDateTime access_expires_at;
    private LocalDateTime refresh_expires_at;
    private String password;
    private String email;
    private int permission_level;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private LocalDateTime delete_at;

}
