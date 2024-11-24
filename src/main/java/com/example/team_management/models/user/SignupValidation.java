package com.example.team_management.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupValidation {
    
    @NotBlank
    @Size(min = 3, max = 10, message = "3文字以上,10文字以下")
    private String name;

    @NotBlank
    @Size(min = 5)
    @Pattern(regexp = ".*@.*", message = "メールアドレスには@が必要です")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20, message = "6文字以上,20文字以下")
    @Pattern(regexp = "^[\\x00-\\x7F]+$", message = "パスワードに全角文字は使用できません")
    private String password;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
