package com.example.team_management.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_table")
@Data
public class User {
    @Id
    private int user_id;
    private String user_name;
    private String password;
    private String email;
    private int permission_level;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private LocalDateTime delete_at;

}
