package HostelManagementSystem;

import java.util.ArrayList;
import java.util.List;

class Hostel 
{
    private static int idCounter = 1; // Static counter for unique IDs
    private int hostelID;
    private String hostelName;
    private final String ownerID;   
    
    private String name;
    private List<Review> reviews;
    private List<Room> rooms;
    
    public Hostel(String hostelName, String ownerID, int numberOfRooms, List<Room> rooms) 
    {
        this.hostelID = idCounter++; // Assign the current counter value and then increment it
        this.hostelName = hostelName;
        this.ownerID = ownerID;
        this.rooms = rooms;
        this.reviews = new ArrayList<>(); // Initialize the reviews list
    
    }
    
    public boolean removeRoom(String roomID)
    {
        for (Room room : rooms) {
            if (room.getRoomID().equals(roomID)) {
                rooms.remove(room); // Remove the room
                System.out.println("Room " + roomID + " has been removed successfully.");
                return true;
            }
        }
        System.out.println("Room ID " + roomID + " not found in Hostel " + hostelID);
        return false;
    }

    public void setHostelName(String newName) {
        this.hostelName = newName;
    }

	public Object getOwnerID() 
	{
		return ownerID;
	}

	public int getHostelID() 
    {
        return hostelID;
    }

    public String getName() 
    {
        return hostelName;
    }

    public List<Room> getRooms() 
    {
        return rooms;
    }

     
    public List<Review> getReviews() 
    {
        return reviews;
    }

    public void addReview(Review review)
    {
        reviews.add(review);
    }

    public void removeReview(Review review) 
    {
        reviews.remove(review);
    }
    

    @Override
    public String toString() 
    {
        return "Hostel ID: " + hostelID + ", Name: " + hostelName;
    }
}