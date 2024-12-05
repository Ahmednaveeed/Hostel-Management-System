package HostelManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class NormalUser extends Users 
{
    private String city;
    private List<Booking> bookings;
    private List<Payment> payments;

    public NormalUser(String userID, String name, String email, String phoneNumber, String password, String city) 
    {
        super(userID, name, email, phoneNumber, password);
        this.city = city;
        this.bookings = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    // Search for available hostels and display them
    public void searchHostel(List<Hostel> hostels) 
    {
        System.out.println("Available Hostels:");
        for (Hostel hostel : hostels)
        {
            System.out.println(hostel);
        }
    }
    
    @Override
    public void viewAccountDetails() {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("City: " + this.city);
    }

    @Override
    public void editAccountDetails(Scanner scanner) {
        System.out.println("Current Name: " + name);
        System.out.print("Enter new name (or press Enter to keep current): ");
        scanner.nextLine(); // Consume leftover newline
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            name = newName;
        }

        System.out.println("Current Phone: " + phone);
        System.out.print("Enter new phone (or press Enter to keep current): ");
        String newPhone = scanner.nextLine();
        if (!newPhone.isEmpty()) {
            phone = newPhone;
        }

        System.out.println("Current Email: " + email);
        System.out.print("Enter new email (or press Enter to keep current): ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) {
            email = newEmail;
        }

        System.out.println("City: " + city);
        System.out.print("Enter new city (or press Enter to keep current): ");
        String newCity = scanner.nextLine();
        if (!newCity.isEmpty()) {
            city = newCity;
        }

        System.out.println("Account details updated successfully.");
    }

    
    // Review a specific hostel by ID
    public void reviewHostel(int hostelID, List<Hostel> hostels, String review) 
    {
        for (Hostel hostel : hostels) {
            if (hostel.getHostelID() == hostelID) {
                Review newReview = new Review(this.getUserID(), hostelID, review);
                hostel.addReview(newReview);
                System.out.println("Review added successfully. Your review ID is: " + newReview.getReviewID());
                return;
            }
        }
        System.out.println("Hostel not found.");
    }


    public void deleteReview(String reviewID, List<Hostel> hostels) 
    {
        for (Hostel hostel : hostels) {
            for (Review review : hostel.getReviews()) {
                // Check if review is not null and reviewID is not null
                if (review != null &&
                    review.getReviewID() != null &&
                    review.getReviewID().equals(reviewID) &&
                    review.getUserID().equals(this.getUserID())) {
                    
                    hostel.removeReview(review);
                    System.out.println("Review deleted successfully.");
                    return;
                }
            }
        }
        System.out.println("Review not found or you do not have permission to delete this review.");
    }

    // View all bookings made by the user
    public void viewBookings()
    {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }
    
    public void updateBookings(String roomID)
    {
        bookings.removeIf(booking -> booking.getRoomID().equals(roomID));
        System.out.println("Removed bookings for Room ID: " + roomID);
    }

    // Add a booking to the user's list of bookings
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    // Getter for bookings
    public List<Booking> getBookings() {
        return bookings;
    }

    // Getter for payments
    public List<Payment> getPayments() {
        return payments;
    }

    // Make a payment for a booking
    public void makePayment(Payment payment) {
        payments.add(payment);
    }

    // Book a room (this method is similar to addBooking)
    public void bookRoom(Booking booking, Payment payment) 
    {
    	bookings.add(booking);
    	payments.add(payment); 
    }

    // Cancel a booking and process the refund
    public void cancelBooking(String bookingID, List<Hostel> hostels) {
        // Try to find the booking
        for (Booking booking : bookings) {
            if (booking.getBookingID().equals(bookingID)) {
                // Refund 50% of the amount
                double refundAmount = booking.getCost() * 0.5;
                System.out.println("Refund amount: $" + refundAmount);

                // Free up the room by setting its availability to true
                for (Hostel hostel : hostels) {
                    for (Room room : hostel.getRooms()) {
                        if (room.getRoomID().equals(booking.getRoomID())) {
                            room.setAvailability(true);
                            System.out.println("Room " + room.getRoomID() + " is now available.");
                            break;
                        }
                    }
                }

                // Remove the booking from the list
                bookings.remove(booking);
                System.out.println("Booking cancelled successfully.");
                return; // Exit after cancellation
            }
        }
        System.out.println("Booking not found.");
    }
}