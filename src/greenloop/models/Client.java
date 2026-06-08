package greenloop.models;

// This class represents a Client in our system
public class Client {
    // Private variables for encapsulation
    private String id;
    private String name;
    private String contact;
    private String email;

    // Constructor to initialize a new Client object when we fetch from DB or create a new one
    public Client(String id, String name, String contact, String email) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
    }

    // Getters and Setters to access and modify the private variables safely
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}