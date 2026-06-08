//Connect Java application to database

package greenloop.dao;

import greenloop.database.DatabaseConnection;
import greenloop.models.DeliveryAgent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Data Access Object handling Agents/Personnel DB logic
public class DeliveryAgentDAO {

    // Retrieve all agents from database.
    public List<DeliveryAgent> getAllAgents() {
        List<DeliveryAgent> list = new ArrayList<>();
        String sql = "SELECT * FROM delivery_agents";  //get every record from table
        try (Connection c = DatabaseConnection.getConnection(); 
             Statement s = c.createStatement(); 
             ResultSet rs = s.executeQuery(sql)) {
            while(rs.next()) {
                list.add(new DeliveryAgent(rs.getString("agent_id"), rs.getString("name"), rs.getString("vehicle_details"), rs.getString("email")));
            }
        } catch(Exception e) { e.printStackTrace(); }
        return list;
    }

    //insert agent into database
    public void saveAgent(DeliveryAgent a) {
        String sql = "REPLACE INTO delivery_agents (agent_id, name, vehicle_details, email) VALUES (?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getAgentId()); 
            ps.setString(2, a.getName()); 
            ps.setString(3, a.getVehicleDetails()); 
            ps.setString(4, a.getEmail());
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }

    //Remove an agent
    public void deleteAgent(String id) {
        String sql = "DELETE FROM delivery_agents WHERE agent_id=?";
        try (Connection c = DatabaseConnection.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id); 
            ps.executeUpdate();
        } catch(Exception e) { e.printStackTrace(); }
    }
}
