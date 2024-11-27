package com.example.team_management.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NameChangeValidation {
    
    @NotBlank
    @Size(min = 3, max = 10, message = "3文字以上,10文字以下")
    private String newName;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
