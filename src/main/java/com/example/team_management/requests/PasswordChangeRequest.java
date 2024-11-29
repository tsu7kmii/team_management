package com.example.team_management.requests;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordChangeRequest {

    @NotBlank
    @Size(min = 6, max = 20, message = "6文字以上,20文字以下")
    @Pattern(regexp = "^(?!.*(password|PASSWORD|Password)).*(?=.*[a-zA-Z])(?=.*\\d)(?!.*(.)\\1{2,}).{6,20}$", message = "パスワードに全角文字は使用できません。また、アルファベット/数字を1文字以上必要であり、同じ文字の繰り返しは使用できません")
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 20, message = "6文字以上,20文字以下")
    @Pattern(regexp = "^(?!.*(password|PASSWORD|Password)).*(?=.*[a-zA-Z])(?=.*\\d)(?!.*(.)\\1{2,}).{6,20}$", message = "パスワードに全角文字は使用できません。また、アルファベット/数字を1文字以上必要であり、同じ文字の繰り返しは使用できません")
    private String againNewPassword;

    @AssertTrue(message = "パスワードが一致しません")
    public boolean isPasswordValid(){
        return newPassword != null && newPassword.equals(againNewPassword);
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAgainNewPassword() {
        return againNewPassword;
    }

    public void setAgainNewPassword(String againNewPassword) {
        this.againNewPassword = againNewPassword;
    }
}
