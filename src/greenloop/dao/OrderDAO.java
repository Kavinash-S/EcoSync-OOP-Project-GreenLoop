package greenloop.dao;

import greenloop.database.DatabaseConnection;
import greenloop.models.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Data Access Object handling Order tracking DB logic
public class OrderDAO {

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection c = DatabaseConnection.getConnection(); 
             Statement s = c.createStatement(); 
             ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new Order(rs.getString("order_id"), rs.getString("client_name"), rs.getString("product_name"), rs.getInt("quantity"), rs.getDouble("total_amount"), rs.getString("status"), rs.getString("delivery_agent_name")));
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    public void saveOrder(Order o) {
        String sql = "REPLACE INTO orders (order_id, client_name, product_name, quantity, total_amount, status, delivery_agent_name) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, o.getOrderId()); 
            ps.setString(2, o.getClientName()); 
            ps.setString(3, o.getProductName()); 
            ps.setInt(4, o.getQuantity()); 
            ps.setDouble(5, o.getTotalAmount()); 
            ps.setString(6, o.getStatus()); 
            ps.setString(7, o.getDeliveryAgentName());
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }
}