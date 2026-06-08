package greenloop.models;

// Model class for Orders tracking
public class Order {
    private String orderId;
    private String clientName;
    private String productName;
    private int quantity;
    private double totalAmount;
    private String status; // e.g., Pending, Dispatched
    private String deliveryAgentName;

    // Constructor mapping to the orders table in our database
    public Order(String orderId, String clientName, String productName, int quantity, double totalAmount, String status, String deliveryAgentName) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.productName = productName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.deliveryAgentName = deliveryAgentName;
    }

    // Getter and setter methods
    public String getOrderId() { return orderId; }
    public String getClientName() { return clientName; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDeliveryAgentName() { return deliveryAgentName; }
    public void setDeliveryAgentName(String deliveryAgentName) { this.deliveryAgentName = deliveryAgentName; }
}