package com.vbforge.design_principles.coupling;

public class EmailNotifier {

    public void sendEmail(String recipient, String subject, String body) {
        System.out.println("Sending email to: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }

}
