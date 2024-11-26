package com.example.team_management.models.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    private int user_id;
    private String user_name;
    private String password;
    private String email;
    private int permission_level;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private LocalDateTime delete_at;

}
