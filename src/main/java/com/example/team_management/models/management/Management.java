package com.example.team_management.models.management;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Management {
    private int management_id;
    private int user_id;
    private String subject;
    private String link;
    private int status;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private LocalDateTime delete_at;
    private Date completion_schedule;
}
