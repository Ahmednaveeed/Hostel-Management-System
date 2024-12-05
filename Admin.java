package HostelManagementSystem;

import java.util.List;
import java.util.Scanner;

class Admin 
{
    private final String userID = "1132";
    private final String password = "12345";

    public boolean login(String enteredUserID, String enteredPassword) {
        return userID.equals(enteredUserID) && password.equals(enteredPassword);
}

    public void viewUsers(List<Users> users) {
        System.out.println("--- Registered Users ---");
        for (Users user : users) {
            System.out.println(user.getUserID() + " - " + user.getName());
        }
    }

    public void deleteUser(List<Users> users, String userID) {
        users.removeIf(user -> user.getUserID().equals(userID));
        System.out.println("User deleted successfully.");
    }

    // Method to manage payments
    public void managePayments(List<Payment> payments, List<Users> users, List<Hostel> hostels, Scanner scanner) {
        System.out.println("--- Payments ---");
        for (Payment payment : payments) {
            System.out.println(payment);
        }

        System.out.println("1) Refund a payment\n2) Go back to main menu");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.print("Enter Payment ID to refund: ");
            String paymentID = scanner.next();

            for (Payment payment : payments) 
            {
                if (payment.getPaymentID().equals(paymentID)) 
                {
                    if (payment.getStatus().equals("Processed")) 
                    {
                        // Refund the payment
                        payment.processPayment(); // Assuming we add refund logic in this method

                        // Cancel the associated booking
                        String bookingID = payment.getBookingID();
                        for (Users user : users) 
                        {
                            if (user instanceof NormalUser) 
                            {
                                NormalUser normalUser = (NormalUser) user;
                                for (Booking booking : normalUser.getBookings())
                                {
                                    if (booking.getBookingID().equals(bookingID)) 
                                    {
                                        // Free up the room
                                        for (Hostel hostel : hostels) 
                                        {
                                            for (Room room : hostel.getRooms()) 
                                            {
                                                if (room.getRoomID().equals(booking.getRoomID()))
                                                {
                                                    room.setAvailability(true);
                                                    System.out.println("Room " + room.getRoomID() + " is now available.");
                                                    break;
                                                }
                                            }
                                        }

                                        // Remove the booking from the user's list
                                        normalUser.getBookings().remove(booking);
                                        System.out.println("Booking cancelled successfully.");
                                        break;
                                    }
                                }
                            }
                        }

                        System.out.println("Refund processed for Payment ID: " + paymentID);
                        return;
                    }
                    else 
                    {
                        System.out.println("Payment not processed. Cannot refund.");
                    }
                }
            }

            System.out.println("Payment ID not found.");
        } 
        else 
        {
            System.out.println("Returning to main menu.");
        }
    }

}
