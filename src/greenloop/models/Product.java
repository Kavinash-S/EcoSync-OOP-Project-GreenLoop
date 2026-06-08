package greenloop.models;

// This class represents the eco-packaging products in our catalogue
public class Product {
    private String id;
    private String name;
    private double price;
    private String ecoRating;
    private int stockQty;
    private int reorderLevel;

    // Constructor to set up a product with all its attributes
    public Product(String id, String name, double price, String ecoRating, int stockQty, int reorderLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ecoRating = ecoRating;
        this.stockQty = stockQty;
        this.reorderLevel = reorderLevel;
    }

    // Getters and setters for product fields
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getEcoRating() { return ecoRating; }
    public void setEcoRating(String ecoRating) { this.ecoRating = ecoRating; }
    public int getStockQty() { return stockQty; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }
}