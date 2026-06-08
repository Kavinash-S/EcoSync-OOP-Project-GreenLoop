package greenloop.models;

// Class to store delivery agent details for the logistics module
public class DeliveryAgent {
    private String agentId;
    private String name;
    private String vehicleDetails;
    private String email;

    // Parameterized constructor
    public DeliveryAgent(String agentId, String name, String vehicleDetails, String email) {
        this.agentId = agentId;
        this.name = name;
        this.vehicleDetails = vehicleDetails;
        this.email = email;
    }

    // Standard getters and setters below
    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVehicleDetails() { return vehicleDetails; }
    public void setVehicleDetails(String vehicleDetails) { this.vehicleDetails = vehicleDetails; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}