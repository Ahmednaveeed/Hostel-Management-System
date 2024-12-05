package HostelManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Main Class
public class main
{
	 public static void main(String[] args) 
	 {
	     Scanner scanner = new Scanner(System.in);
	     List<Users> users = new ArrayList<>();
	     List<Hostel> hostels = new ArrayList<>();
	     List<Payment> payments = new ArrayList<>();
	
	     Admin admin = new Admin();
	
	     while (true) 
	     {
	         System.out.println("Welcome to Hostel Management System");
	         System.out.println("A) Hostel Owner\nB) Normal User\nC) Admin\nD) Exit");
	         System.out.print("Choose an option: ");
	         char choice = scanner.next().toUpperCase().charAt(0);
	
	         switch (choice) {
	         case 'A':
                 System.out.println("1) Login\n2) Register");
                 int ownerChoice = scanner.nextInt();

                 if (ownerChoice == 1) 
                 {
                     // Login
                     System.out.print("Enter ID: ");
                     String ownerID = scanner.next();
                     System.out.print("Enter Password: ");
                     String password = scanner.next();

                     boolean userFound = false;
                     for (Users user : users) {
                         if (user instanceof HostelOwner && user.getUserID().equals(ownerID)) 
                         {
                             userFound = true;
                             if (user.login(password))
                             {
                                 System.out.println("Login successful.");
                                 HostelOwner owner = (HostelOwner) user;
                                 // Owner Menu
                                 System.out.println("1) List Hostels\n2) View Listed Hostels\n3) Contact Support\n4) View/Edit Account Details\n5) Delete Account\n6) Update Hostel Details\n7) Delete room");
                              
                                 int ownerAction = scanner.nextInt();

                                 switch (ownerAction) {
                                     case 1:
                                         String hostelName = readAlphabeticString(scanner, "Enter Hostel Name: ");
                                          int numRooms = readPositiveInt(scanner, "Enter Number of Rooms: ");

                                         List<Room> rooms = new ArrayList<>();
                                         for (int i = 1; i <= numRooms; i++) {
                                            
                                             String roomName = readAlphabeticString(scanner,  "Enter Room Name for Room " + i + ": ");
                                double price = readPositiveDouble(scanner, "Enter Price for Room " + i + ": ");
                                             rooms.add(new Room("R" + i, roomName, price, true));
                                         }
                                         owner.listHostel(hostelName, numRooms, rooms, hostels);
                                         break;

                                     case 2:
                                         owner.viewListedHostels();
                                         break;

                                     case 3: 
                                    	 Support.contactSupport(scanner, owner.getUserID());
                                    	 break;
                                    	 
                                     case 4:
                                         // View/Edit Account Details
                                         System.out.println("1) View Account Details\n2) Edit Account Details");
                                         int accountAction = scanner.nextInt();
                                         if (accountAction == 1) {
                                             owner.viewAccountDetails();
                                         } else if (accountAction == 2) {
                                        
                                            
                                             String newName = readValidName(scanner, "Enter Name: ");
                                             
                                        	 String newEmail = readValidEmail(scanner, "Enter Email: ");
                                          
                                             owner.editAccountDetails(scanner);
                                         }
                                         else 
                                         break;
                                         break;
                                     case 5:
                                    	    // Delete Account
                                    	    System.out.println("Are you sure you want to delete your account? (yes/no)");
                                    	    String confirmDelete = scanner.next().toLowerCase();
                                    	    if (confirmDelete.equals("yes")) {
                                    	        // Remove all hostels associated with this owner
                                    	        for (Hostel hostel : new ArrayList<>(hostels)) {
                                    	            if (hostel.getOwnerID().equals(owner.getUserID())) {
                                    	                hostels.remove(hostel);
                                    	                System.out.println("Hostel " + hostel.getHostelID() + " deleted.");
                                    	            }
                                    	        }

                                    	        users.remove(owner);
                                    	        System.out.println("Account deleted.");
                                    	    }
                                    	    break;
                                    	    
                                     case 6: // Update Hostel Details
                                    	    System.out.println("1) Update Hostel Name\n2) Update Room Details");
                                    	    int updateAction = scanner.nextInt();

                                    	    if (updateAction == 1) {
                                    	        owner.updateHostelDetails(scanner, hostels);
                                    	    } else if (updateAction == 2) {
                                    	        owner.updateRoomDetails(scanner, hostels);
                                    	    } else {
                                    	        System.out.println("Invalid choice.");
                                    	    }
                                    	    break;
                                     case 7:
                                         // Delete Room
                                         System.out.println("Enter Hostel ID:");
                                         int hostelID = scanner.nextInt();
                                         System.out.println("Enter Room ID to delete:");
                                         String roomID = scanner.next();

                                         Hostel targetHostel = null;
                                         for (Hostel hostel : hostels) {
                                             if (hostel.getHostelID() == hostelID) {
                                                 targetHostel = hostel;
                                                 break;
                                             }
                                         }

                                         if (targetHostel != null) {
                                             boolean removed = targetHostel.removeRoom(roomID);
                                             if (removed) {
                                                 // Update all user bookings
                                                 for (Users u : users) {
                                                     if (u instanceof NormalUser) {
                                                         ((NormalUser) u).updateBookings(roomID);
                                                     }
                                                 }
                                             }
                                         } else {
                                             System.out.println("Hostel ID not found.");
                                         }
                                         break;

                                    	default:
                                    	    System.out.println("Invalid choice.");
                                    	    break;
                                 }
                             } 
                             else {
                                 System.out.println("Incorrect password.");
                             }
                             break;
                         }
                     }

                     if (!userFound) {
                         System.out.println("User not found. Please register first.");
                     }
                 } else if (ownerChoice == 2) {
                     // Register
                     System.out.print("Enter ID: ");
                     String ownerID = scanner.next();
                     String name = readValidName(scanner, "Enter Name: ");
                     String email = readValidEmail(scanner, "Enter Email: ");
                     String phoneNumber = readValidPhoneNumber(scanner, "Enter Phone Number: ");
                     System.out.print("Enter Password: ");
                     String password = scanner.next();

                     users.add(new HostelOwner(ownerID, name, email, phoneNumber, password));
                     System.out.println("Registration successful.");
                 } else {
                     System.out.println("Invalid choice.");
                 }
                 break;

	         case 'B':
	             System.out.println("1) Login\n2) Register");
	             int userChoice = scanner.nextInt();
	             
	             if (userChoice == 1) {
	                 // Login Process
	                 System.out.print("Enter ID: ");
	                 String userID = scanner.next();
	                 System.out.print("Enter Password: ");
	                 String password = scanner.next();

	                 boolean userFound = false;

	                 for (Users user : users) {
	                     if (user instanceof NormalUser && user.getUserID().equals(userID)) {
	                         userFound = true;
	                         if (user.login(password)) {
	                             System.out.println("Login successful.");
	                             NormalUser normalUser = (NormalUser) user;

	                              
	                             // Display Normal User Menu
	                             System.out.println("1) Search Hostels\n2) View Bookings\n3) Write a Review\n4) Contact Support\n5) View/Edit Account Details\n6) Delete Review\n7) Delete Account");
	                             int userAction = readValidInt(scanner, "Enter action: ", 1, 7);

	                             switch (userAction) {
	                             case 1:
	                            	    // Handle Hostel Search and Booking
	                            	    if (hostels.isEmpty()) {
	                            	        System.out.println("No hostels available.");
	                            	        break;
	                            	    }

	                            	    System.out.println("Available Hostels:");
	                            	    for (int i = 0; i < hostels.size(); i++) {
	                            	        System.out.println((i + 1) + ") " + hostels.get(i).getName());
	                            	    }

	                            	    int hostelChoice = readValidInt(scanner, "Enter hostel number: ", 1, hostels.size());
	                            	    Hostel selectedHostel = hostels.get(hostelChoice - 1);

	                            	    System.out.println("1) View Reviews\n2) View Rooms");
	                            	    int viewChoice = readValidInt(scanner, "Enter your choice: ", 1, 2);

	                            	    if (viewChoice == 1) {
	                            	        // View Reviews
	                            	        List<Review> reviews = selectedHostel.getReviews();
	                            	        if (reviews.isEmpty()) 
	                            	        {
	                            	            System.out.println("No reviews available for this hostel.");
	                            	        } 
	                            	        else 
	                            	        {
	                            	            System.out.println("Reviews for " + selectedHostel.getName() + ":");
	                            	            for (Review review : reviews)
	                            	            {
	                            	                System.out.println(review);
	                            	            }

	                            	            System.out.println("1)Go back to main menu");
	                            	            int reviewAction = readValidInt(scanner, "Enter your choice: ", 1, 2);
	                            	            if (reviewAction == 1) 
	                            	            {
	                            	                System.out.print("Returning to main menu. ");
	                            	            }
	                            	        }
	                            	    } else if (viewChoice == 2) {
	                            	        // View Rooms
	                            	        List<Room> rooms = selectedHostel.getRooms();
	                            	        System.out.println("Available Rooms in " + selectedHostel.getName() + ":");
	                            	        for (int i = 0; i < rooms.size(); i++) {
	                            	            if (rooms.get(i).isAvailable()) {
	                            	                System.out.println((i + 1) + ") " + rooms.get(i).getRoomName());
	                            	            }
	                            	        }

	                            	        int roomChoice = readValidInt(scanner, "Enter room number: ", 1, rooms.size());
	                            	        if (!rooms.get(roomChoice - 1).isAvailable()) {
	                            	            System.out.println("Room not available. Choose another.");
	                            	            break;
	                            	        }

	                            	        System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
	                            	        String checkInDate = readValidDate(scanner);
	                            	        System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
	                            	        String checkOutDate = readValidDate(scanner);

	                            	        double cost = rooms.get(roomChoice - 1).getPrice();
	                            	        System.out.println("Booking Summary: Total Cost: $" + cost);

	                            	        String confirmation = readYesNo(scanner, "Confirm booking? (yes/no): ");
	                            	        if (confirmation.equals("yes")) {
	                            	            // Generate Booking ID and Payment ID
	                            	            String bookingID = "B" + (normalUser.getBookings().size() + 1);
	                            	            String paymentID = "P" + (payments.size() + 1);

	                            	            Booking booking = new Booking(
	                            	                bookingID,
	                            	                rooms.get(roomChoice - 1).getRoomID(),
	                            	                userID,
	                            	                checkInDate,
	                            	                checkOutDate,
	                            	                cost
	                            	            );

	                            	            Payment payment = new Payment(
	                            	                paymentID,
	                            	                userID,
	                            	                bookingID,
	                            	                cost,
	                            	                "2023-01-01" // Placeholder for payment date
	                            	            );

	                            	            if (payment.processPayment()) {
	                            	                normalUser.bookRoom(booking, payment);
	                            	                rooms.get(roomChoice - 1).setAvailability(false);
	                            	                payments.add(payment);
	                            	                System.out.println("Booking successful! Your payment ID is: " + paymentID);
	                            	            } else {
	                            	                System.out.println("Payment failed. Booking not completed.");
	                            	            }
	                            	        } else {
	                            	            System.out.println("Booking cancelled.");
	                            	        }

	                            	    } else {
	                            	        System.out.println("Invalid choice.");
	                            	    }
	                            	    break;


	                                 case 2: // View Bookings
	                                	    normalUser.viewBookings();
	                                	    System.out.println("1) Cancel a booking\n2) Go back to main menu");
	                                	    int bookingAction = scanner.nextInt();

	                                	    if (bookingAction == 1) {
	                                	        System.out.print("Enter Booking ID to cancel: ");
	                                	        String bookingID = scanner.next();
	                                	        normalUser.cancelBooking(bookingID, hostels);
	                                	    } else {
	                                	        System.out.println("Returning to main menu.");
	                                	    }
	                                	    break;

	                                 case 3:
	                                     // Handle Writing a Review
	                                    
	                                     int hostelID = readValidInt(scanner, "Enter Hostel ID: ", 1, hostels.size());
	                                     System.out.print("Enter your review: ");
	                                     scanner.nextLine(); // Consume newline
	                                     String review = scanner.nextLine();
	                                     normalUser.reviewHostel(hostelID, hostels, review);
	                                     break;

	                                 case 4: // Updated contact support function 
	                                	 Support.contactSupport(scanner, normalUser.getUserID());
	                                	 break;

	                                 case 5:
	                                     // View/Edit Account Details
	                                     System.out.println("1) View Account Details\n2) Edit Account Details");
	                                     int accountAction = scanner.nextInt();
	                                     if (accountAction == 1) {
	                                         normalUser.viewAccountDetails();
	                                     } else if (accountAction == 2) {
	                                    	 
	                                      
	                                         String newName = readValidName(scanner, "Enter Name: ");
	                                         
	                                         String newEmail = readValidEmail(scanner, "Enter Email: ");
	                                         normalUser.editAccountDetails(scanner);
	                                     }
	                                     break;

	                                 case 6:
	                                	    // Delete Review
	                                	    System.out.print("Enter Review ID to delete: ");
	                                	    String reviewID = scanner.next();
	                                	    normalUser.deleteReview(reviewID, hostels); // Assuming hostels list is available
	                                	    break;

	                                 case 7:
	                                	    System.out.println("Are you sure you want to delete your account? (yes/no)");
	                                	    String confirmDelete = scanner.next().toLowerCase();
	                                	    if (confirmDelete.equals("yes")) {
	                                	        // Free up all rooms associated with this user's bookings
	                                	        for (Booking booking : normalUser.getBookings()) {
	                                	            for (Hostel hostel : hostels) {
	                                	                for (Room room : hostel.getRooms()) {
	                                	                    if (room.getRoomID().equals(booking.getRoomID())) {
	                                	                        room.setAvailability(true);
	                                	                        System.out.println("Room " + room.getRoomID() + " is now available.");
	                                	                    }
	                                	                }
	                                	            }
	                                	        }

	                                	        users.remove(normalUser);
	                                	        System.out.println("Account deleted.");
	                                	    }
	                                	    break;

	                                 default:
	                                     System.out.println("Invalid action.");
	                             }
	                         } else {
	                             System.out.println("Invalid password. Please try again.");
	                         }
	                         break;
	                     }
	                 }

	                 if (!userFound) {
	                     System.out.println("User ID not found. Please register first.");
	                 }

	             } else if (userChoice == 2) {
	                 // Registration Process
	                 System.out.print("Enter ID: ");
	                 String userID = scanner.next();

	                 // Check if the ID is already in use
	                 boolean idExists = users.stream().anyMatch(user -> user.getUserID().equals(userID));
	                 if (idExists) {
	                     System.out.println("User ID already exists. Please log in.");
	                 } else {
	                    
	                     String name = readAlphabeticString(scanner, "Enter Name: ");
	                    
	                     String email = readValidEmail(scanner, "Enter Email: ");
	                   
	                     String phoneNumber = readValidPhoneNumber(scanner, "Enter Phone Number: ");
	                     System.out.print("Enter Password: ");
	                     String password = scanner.next();
	                     
	                     String city = readAlphabeticString(scanner, "Enter City: ");

	                     users.add(new NormalUser(userID, name, email, phoneNumber, password, city));
	                     System.out.println("Registration successful. Please log in to continue.");
	                 }
	             } else {
	                 System.out.println("Invalid choice.");
	             }
	             break;
	          
	         case 'C': 
	        	    System.out.print("Enter Admin ID: ");
	        	    String adminID = scanner.next();
	        	    System.out.print("Enter Password: ");
	        	    String adminPassword = scanner.next();
	        	    
	        	    if (admin.login(adminID, adminPassword)) {
	        	        System.out.println("Admin Login Successful.");
	        	        System.out.println("1) Show all normal users\n2) Show all hostel owners\n3) See support messages\n4) Manage payments");
	        	        
	        	        int adminAction = readValidInt(scanner, "Enter your choice: ", 1, 4); // Input validation
	        	        switch (adminAction) {
	        	        case 1:
	        	            System.out.println("--- Registered Normal Users ---");
	        	            boolean normalUsersExist = false;

	        	            for (Users user : users) {
	        	                if (user instanceof NormalUser) {
	        	                    normalUsersExist = true;
	        	                    System.out.println(user.getUserID() + " - " + user.name);
	        	                }
	        	            }

	        	            if (!normalUsersExist) {
	        	                System.out.println("No normal users found.");
	        	                break;
	        	            }

	        	            System.out.println("1) Delete a user\n2) Go back to main menu");
	        	            int normalUserAction = readValidInt(scanner, "Enter your choice: ", 1, 2);

	        	            if (normalUserAction == 1) {
	        	                System.out.print("Enter User ID to delete: ");
	        	                String deleteNormalUserID = scanner.next();

	        	                NormalUser userToDelete = null;
	        	                for (Users user : users) {
	        	                    if (user instanceof NormalUser && user.getUserID().equals(deleteNormalUserID)) {
	        	                        userToDelete = (NormalUser) user;
	        	                        break;
	        	                    }
	        	                }

	        	                if (userToDelete != null) {
	        	                    // Free up all rooms associated with this user's bookings
	        	                    for (Booking booking : userToDelete.getBookings()) {
	        	                        for (Hostel hostel : hostels) {
	        	                            for (Room room : hostel.getRooms()) {
	        	                                if (room.getRoomID().equals(booking.getRoomID())) {
	        	                                    room.setAvailability(true);
	        	                                    System.out.println("Room " + room.getRoomID() + " is now available.");
	        	                                }
	        	                            }
	        	                        }
	        	                    }

	        	                    users.remove(userToDelete);
	        	                    System.out.println("Normal user deleted successfully.");
	        	                } else {
	        	                    System.out.println("User ID not found.");
	        	                }
	        	            } else {
	        	                System.out.println("Returning to main menu.");
	        	            }
	        	            break;

	        	                
	        	        case 2:
	        	            System.out.println("--- Registered Hostel Owners ---");
	        	            boolean ownersExist = false;

	        	            for (Users user : users) {
	        	                if (user instanceof HostelOwner) {
	        	                    ownersExist = true;
	        	                    System.out.println(user.getUserID() + " - " + user.name);
	        	                }
	        	            }

	        	            if (!ownersExist) {
	        	                System.out.println("No hostel owners found.");
	        	                break;
	        	            }

	        	            System.out.println("1) Delete a hostel owner\n2) Go back to main menu");
	        	            int hostelOwnerAction = readValidInt(scanner, "Enter your choice: ", 1, 2);

	        	            if (hostelOwnerAction == 1) {
	        	                System.out.print("Enter Hostel Owner ID to delete: ");
	        	                String deleteOwnerID = scanner.next();

	        	                HostelOwner ownerToDelete = null;
	        	                for (Users user : users) {
	        	                    if (user instanceof HostelOwner && user.getUserID().equals(deleteOwnerID)) {
	        	                        ownerToDelete = (HostelOwner) user;
	        	                        break;
	        	                    }
	        	                }

	        	                if (ownerToDelete != null) {
	        	                    // Remove all hostels associated with this owner
	        	                    for (Hostel hostel : new ArrayList<>(hostels)) {
	        	                        if (hostel.getOwnerID().equals(deleteOwnerID)) {
	        	                            hostels.remove(hostel);
	        	                            System.out.println("Hostel " + hostel.getHostelID() + " deleted.");
	        	                        }
	        	                    }

	        	                    users.remove(ownerToDelete);
	        	                    System.out.println("Hostel owner deleted successfully.");
	        	                } else {
	        	                    System.out.println("Hostel Owner ID not found.");
	        	                }
	        	            } else {
	        	                System.out.println("Returning to main menu.");
	        	            }
	        	            break;
	        	            
	        	        case 3:
	                        List<SupportMessage> supportMessages = Support.getSupportMessages();
	                        if (supportMessages.isEmpty()) {
	                            System.out.println("No support messages found.");
	                        } else {
	                            System.out.println("--- Support Messages ---");
	                            for (SupportMessage message : supportMessages) {
	                                System.out.println(message);
	                            }

	                            System.out.print("Enter Ticket ID to resolve or 0 to go back to main menu: ");
	                            int ticketID = scanner.nextInt();
	                            if (ticketID != 0) {
	                                Support.resolveSupportMessage(ticketID);
	                            } else {
	                                System.out.println("Returning to main menu.");
	                            }
	                        }
	                        break;
	                        
	        	        case 4:
	        	        	admin.managePayments(payments, users, hostels, scanner);
	        	        	break;
	        	                
	        	            default:
	        	                System.out.println("Invalid choice.");
	        	        }
	        	    } else {
	        	        System.out.println("Invalid Admin Credentials.");
	        	    }
	        	    break;

	         case 'D':
	             System.out.println("Exiting system. Goodbye!");
	             scanner.close();
	             return;
	
	         default:
	             System.out.println("Invalid choice. Please try again.");
	     }
	 }
	}
	 
	
	 
	 private static void viewReviews(int hostelID, List<Review> reviews) 
	 {

	}
		// Helper methods for input validation
	    private static int readPositiveInt(Scanner scanner, String prompt) {
	        int value;
	        while (true) {
	            System.out.print(prompt);
	            if (scanner.hasNextInt()) {
	                value = scanner.nextInt();
	                if (value > 0) {
	                    break;
	                } else {
	                    System.out.println("Please enter a positive number.");
	                }
	            } else {
	                System.out.println("Invalid input. Please enter a valid number.");
	                scanner.next(); // Clear invalid input
	            }
	        }
	        return value;
	    }

	    private static double readPositiveDouble(Scanner scanner, String prompt) {
	        double value;
	        while (true) {
	            System.out.print(prompt);
	            if (scanner.hasNextDouble()) {
	                value = scanner.nextDouble();
	                if (value > 0) {
	                    break;
	                } else {
	                    System.out.println("Please enter a positive number.");
	                }
	            } else {
	                System.out.println("Invalid input. Please enter a valid number.");
	                scanner.next(); // Clear invalid input
	            }
	        }
	        return value;
	    }

	    private static String readValidName(Scanner scanner, String prompt) {
	        String name;
	        while (true) {
	            System.out.print(prompt);
	            name = scanner.next();
	            if (name.matches("[a-zA-Z]+")) {
	                break;
	            } else {
	                System.out.println("Invalid input. Name must contain only letters.");
	            }
	        }
	        return name;
	    }

	    private static String readValidEmail(Scanner scanner, String prompt) {
	        System.out.print(prompt);
	        return scanner.next();
	    }
	    
	    private static String readValidPhoneNumber(Scanner scanner, String prompt) {
	        String phoneNumber;
	        while (true) {
	            System.out.print(prompt);
	            phoneNumber = scanner.next();
	            if (phoneNumber.matches("\\d+")) { // Accepts any number of digits
	                break;
	            } else {
	                System.out.println("Invalid phone number. It must contain only digits.");
	            }
	        }
	        return phoneNumber;
	    }

	 // Helper method for yes/no input
	    public static String readYesNo(Scanner scanner, String prompt) {
	        String input;
	        while (true) {
	            System.out.print(prompt);
	            input = scanner.next().toLowerCase();
	            if (input.equals("yes") || input.equals("no")) {
	                break;
	            } else {
	                System.out.println("Invalid input. Please enter either 'yes' or 'no'.");
	            }
	        }
	        return input;
	    }
	    
	 // Helper method for valid date format
	    public static String readValidDate(Scanner scanner) {
	        String date;
	        while (true) {
	            date = scanner.next();
	            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
	                break;
	            } else {
	                System.out.println("Invalid date format. Enter as yyyy-mm-dd.");
	            }
	        }
	        return date;
	    }
	    
	 // Method to read alphabetic string only
	    public static String readAlphabeticString(Scanner scanner, String prompt) {
	        String input;
	        while (true) {
	            System.out.print(prompt);
	            input = scanner.next();
	            if (input.matches("[a-zA-Z]+")) { // Only alphabetic characters
	                break;
	            } else {
	                System.out.println("Invalid input. Please enter alphabetic characters only.");
	            }
	        }
	        return input;
	    }
	 // Helper method for integer validation
	    public static int readValidInt(Scanner scanner, String prompt, int min, int max) {
	        int value;
	        while (true) {
	            System.out.print(prompt);
	            if (scanner.hasNextInt()) {
	                value = scanner.nextInt();
	                if (value >= min && value <= max) {
	                    break;
	                } else {
	                    System.out.println("Please enter a number between " + min + " and " + max + ".");
	                }
	            } else {
	                System.out.println("Invalid input. Please enter a number.");
	                scanner.next(); // Clear invalid input
	            }
	        }
	        return value;
	    }
	    
}


