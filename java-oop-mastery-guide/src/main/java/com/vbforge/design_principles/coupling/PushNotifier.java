package com.vbforge.design_principles.coupling;

public class PushNotifier implements NotificationService {
    @Override
    public void sendNotification(String recipient, String subject, String message) {
        System.out.println("Sending Push Notification to: " + recipient);
        System.out.println("Push Title: " + subject);
        System.out.println("Push Body: " + message);
    }
}
