package com.example.team_management.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * メール送信サービス
 */
@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    private final String fromMailAddress = "team_management_web_app@a.a";

    private final String signatureLine = "---------------------------------- \n ";

    private final String signature = " team management : team_management@a.a \n ";

    /**
     * パスワードリセットメール送信
     * 
     * @param toMailAddress 送信先メールアドレス
     * @param url パスワードリセットURL
     */
    public void sendResetPasswordMail(String toMailAddress, String url){
        // 新しいメッセージ作成
        var newMessage = javaMailSender.createMimeMessage();
        try{
            var newMessageHelper = new MimeMessageHelper(newMessage, true);

            StringBuilder messageText = new StringBuilder();
            messageText.append("1時間以内に下記URLへアクセスし、パスワードの変更を完了してください。 \n ")
                        .append(url).append(" \n\n")
                        .append("パスワードリセットリンク送信されました。この操作を行っていない場合は、直ちに下記メールにご連絡ください。 \n")
                        .append("当メールに心当たりがある場合は、このメールを無視してください。 \n")
                        .append("引き続きシステムをご利用下さい。 \n")
                        .append(signatureLine)
                        .append(signature)
                        .append(signatureLine);

            newMessageHelper.setTo(toMailAddress);
            newMessageHelper.setFrom(fromMailAddress);
            newMessageHelper.setSubject("パスワードリセットリンク送信のお知らせ");
            newMessageHelper.setText(messageText.toString());

            javaMailSender.send(newMessage);

        } catch (MessagingException e){
            throw new RuntimeException("送信エラー");
        }

    }
}
