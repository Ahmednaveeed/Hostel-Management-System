package HostelManagementSystem;

class Booking 
{
    private final String bookingID;
    private final String roomID;
    private final String userID;
    private final String checkInDate;
    private final String checkOutDate;
    private final double cost;

    public Booking(String bookingID, String roomID, String userID, String checkInDate, String checkOutDate,double cost)
    {
        this.bookingID = bookingID;
        this.roomID = roomID;
        this.userID = userID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.cost=cost;
     }

    public double getCost()
    {
    	return cost;
    }
    public String getBookingID() 
    {
        return bookingID;
    }
    
    public String getRoomID()
    {
    	return roomID;
    }

    @Override
    public String toString() 
    {
        return "Booking ID: " + bookingID + ", Room ID: " + roomID + ", User ID: " + userID;
    }
}