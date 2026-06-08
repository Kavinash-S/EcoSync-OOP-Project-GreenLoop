package greenloop.dao;

import greenloop.database.DatabaseConnection;
import greenloop.models.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Data Access Object handling Client DB logic
public class ClientDAO {

    public List<Client> getAllClients() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection c = DatabaseConnection.getConnection(); 
             Statement s = c.createStatement(); 
             ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new Client(rs.getString("client_id"), rs.getString("name"), rs.getString("contact"), rs.getString("email")));
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    public void saveClient(Client cl) {
        String sql = "REPLACE INTO clients (client_id, name, contact, email) VALUES (?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cl.getId()); 
            ps.setString(2, cl.getName()); 
            ps.setString(3, cl.getContact()); 
            ps.setString(4, cl.getEmail());
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void deleteClient(String id) {
        String sql = "DELETE FROM clients WHERE client_id=?";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id); 
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }
}