package HostelManagementSystem;

class Review 
{
    private static int idCounter = 1; // Static counter for unique IDs
    private final String reviewID;
    private final String userID;
    private final int hostelID;
    private final String comment;

    public Review(String userID, int hostelID, String comment) 
    {
        this.reviewID = "R" + idCounter++; // Assign a unique ID
        this.userID = userID;
        this.hostelID = hostelID;
        this.comment = comment;
    }

    public String getReviewID() 
    {
        return reviewID;
    }

    public String getUserID() 
    {
        return userID;
    }

    public int getHostelID() 
    {
        return hostelID;
    }

    public String getComment() 
    {
        return comment;
    }

    @Override
    public String toString() 
    {
        return "Review ID: " + reviewID + ", User ID: " + userID + ", Hostel ID: " + hostelID + ", Comment: " + comment;
    }
}