package HostelManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Support {
    private static final String ADMIN_EMAIL = "admin@hostelms.com";
    private static List<SupportMessage> supportMessages = new ArrayList<>();

    public static void contactSupport(Scanner scanner, String userID) {
        System.out.println("1) Email Admin at: " + ADMIN_EMAIL + "\n2) Send Admin a Message");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.println("Please email your issue to: " + ADMIN_EMAIL);
        } else if (choice == 2) {
            System.out.println("Enter your message:");
            String message = scanner.nextLine();
            SupportMessage supportMessage = new SupportMessage(userID, message);
            supportMessages.add(supportMessage);
            System.out.println("Your message has been sent to the admin. Support Ticket ID: " + supportMessage.getTicketID());
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public static List<SupportMessage> getSupportMessages() {
        return supportMessages;
    }

    public static void resolveSupportMessage(int ticketID) {
        supportMessages.removeIf(message -> message.getTicketID() == ticketID);
        System.out.println("Support ticket " + ticketID + " resolved.");
    }
}