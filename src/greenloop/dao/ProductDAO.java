package greenloop.dao;

import greenloop.database.DatabaseConnection;
import greenloop.models.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Data Access Object specifically handling Product DB operations
public class ProductDAO {

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection c = DatabaseConnection.getConnection(); 
             Statement s = c.createStatement(); 
             ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new Product(rs.getString("product_id"), rs.getString("name"), rs.getDouble("price"), rs.getString("eco_rating"), rs.getInt("stock_qty"), rs.getInt("reorder_level")));
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    public void saveProduct(Product p) {
        String sql = "REPLACE INTO products (product_id, name, price, eco_rating, stock_qty, reorder_level) VALUES (?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getId()); 
            ps.setString(2, p.getName()); 
            ps.setDouble(3, p.getPrice()); 
            ps.setString(4, p.getEcoRating()); 
            ps.setInt(5, p.getStockQty()); 
            ps.setInt(6, p.getReorderLevel());
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void deleteProduct(String id) {
        String sql = "DELETE FROM products WHERE product_id=?";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id); 
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void updateProductStock(String id, int addedQty) {
        String sql = "UPDATE products SET stock_qty = stock_qty + ? WHERE product_id=?";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, addedQty); 
            ps.setString(2, id); 
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void decreaseProductStock(String id, int qty) {
        String sql = "UPDATE products SET stock_qty = stock_qty - ? WHERE product_id=?";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, qty); 
            ps.setString(2, id); 
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public Product getProductByName(String name) {
        String sql = "SELECT * FROM products WHERE name=?";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return new Product(rs.getString("product_id"), rs.getString("name"), rs.getDouble("price"), rs.getString("eco_rating"), rs.getInt("stock_qty"), rs.getInt("reorder_level"));
            }
        } catch(Exception e) { e.printStackTrace(); }
        return null;
    }
}