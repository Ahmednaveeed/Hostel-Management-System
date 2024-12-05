package HostelManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HostelOwner extends Users 
{
    private List<Hostel> ownedHostels;

    public HostelOwner(String userID, String name, String email, String phoneNumber, String password)
    {
        super(userID, name, email, phoneNumber, password);
        this.ownedHostels = new ArrayList<>();
    }

    public void updateHostelDetails(Scanner scanner, List<Hostel> allHostels)
    {
        System.out.print("Enter Hostel ID to update: ");
        int hostelID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (Hostel hostel : allHostels) 
        {
            if (hostel.getHostelID() == hostelID && hostel.getOwnerID().equals(this.userID))
            {
                System.out.println("Current Hostel Name: " + hostel.getName());
                System.out.print("Enter new Hostel Name: ");
                String newHostelName = scanner.nextLine();
                if (!newHostelName.isEmpty()) 
                {
                    hostel.setHostelName(newHostelName); // Update name
                    System.out.println("Hostel name updated successfully.");
                } else 
                {
                    System.out.println("No changes made.");
                }
                return;
            }
        }
        System.out.println("Hostel not found or you don't own it.");
    }
    
    public void updateRoomDetails(Scanner scanner, List<Hostel> allHostels) {
        System.out.print("Enter Hostel ID to update room details: ");
        int hostelID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (Hostel hostel : allHostels) {
            if (hostel.getHostelID() == hostelID && hostel.getOwnerID().equals(this.userID)) {
                System.out.println("Current rooms in " + hostel.getName() + ":");
                List<Room> rooms = hostel.getRooms();

                for (Room room : rooms) {
                    System.out.println("Room ID: " + room.getRoomID() +
                                       ", Name: " + room.getRoomName() +
                                       ", Price: " + room.getPrice());
                }

                System.out.print("Enter Room ID to update: ");
                String roomID = scanner.nextLine();

                for (Room room : rooms) {
                    if (room.getRoomID().equals(roomID)) {
                        System.out.print("Enter new Room Name: ");
                        String newRoomName = scanner.nextLine();

                        System.out.print("Enter new Room Price: ");
                        double newRoomPrice = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline

                        if (!newRoomName.isEmpty()) {
                            room.setRoomName(newRoomName);
                        }
                        room.setPrice(newRoomPrice);

                        System.out.println("Room details updated successfully.");
                        return;
                    }
                }

                System.out.println("Room not found.");
                return;
            }
        }

        System.out.println("Hostel not found or you don't own it.");
    }
    
    @Override
    public void viewAccountDetails() 
    {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Hostels Owned: " + ownedHostels);
    }

    @Override
    public void editAccountDetails(Scanner scanner)
    {
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

        System.out.println("Account details updated successfully.");
    }
    
    public void listHostel(String hostelName, int numberOfRooms, List<Room> rooms, List<Hostel> allHostels) 
    {
        Hostel hostel = new Hostel(hostelName, this.userID, numberOfRooms, rooms);
        ownedHostels.add(hostel);
        allHostels.add(hostel);
        System.out.println("Hostel listed successfully.");
    }

    public void viewListedHostels() 
    {
        if (ownedHostels.isEmpty()) 
        {
            System.out.println("No hostels listed.");
        } else 
        {
            for (Hostel hostel : ownedHostels) 
            {
                System.out.println(hostel);
            }
        }
    }
    
    public void deleteRoom(Scanner scanner,List<Hostel> hostels, List<NormalUser> allUsers) 
    {
        System.out.println("Enter Hostel ID:");
        int hostelID = scanner.nextInt();

        Hostel targetHostel = null;
        for (Hostel hostel : hostels) 
        {
            if (hostel.getHostelID() == hostelID) 
            {
                targetHostel = hostel;
                break;
            }
        }

        if (targetHostel == null) {
            System.out.println("Hostel ID not found.");
            return;
        }

        System.out.println("Enter Room ID to delete:");
        String roomID = scanner.nextLine();

        boolean removed = targetHostel.removeRoom(roomID);
        if (removed) {
            // Update all user bookings
            for (NormalUser user : allUsers) {
                user.updateBookings(roomID);
            }
        }
    }

}