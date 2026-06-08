//Creates the graphical interface

package greenloop.ui;

import greenloop.dao.DeliveryAgentDAO;
import greenloop.models.DeliveryAgent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AgentPanel extends JPanel {
    private final DeliveryAgentDAO aDAO;
    private JTable agentTable;
    private DefaultTableModel agentModel;
    private JTextField txtAgentId, txtAgentName, txtAgentVehicle, txtAgentEmail;

    public AgentPanel(DeliveryAgentDAO aDAO) {
        this.aDAO = aDAO;
        setOpaque(false);
        setLayout(new BorderLayout());

        //create table structure
        agentModel = new DefaultTableModel(new String[]{"Agent ID", "Name", "Vehicle Type", "Email"}, 0);
        agentTable = new JTable(agentModel);
        agentTable.setBackground(Dashboard.C_CARD);
        agentTable.setForeground(Dashboard.C_TEXT);
        refreshTable();

        add(new JScrollPane(agentTable), BorderLayout.CENTER);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Dashboard.C_CARD);
        form.setPreferredSize(new Dimension(300, 0));

        txtAgentId = Dashboard.sField(); form.add(new JLabel("AGENT ID")); form.add(txtAgentId);
        txtAgentName = Dashboard.sField(); form.add(new JLabel("AGENT NAME")); form.add(txtAgentName);
        txtAgentVehicle = Dashboard.sField(); form.add(new JLabel("VEHICLE TYPE")); form.add(txtAgentVehicle);
        txtAgentEmail = Dashboard.sField(); form.add(new JLabel("EMAIL ADDRESS")); form.add(txtAgentEmail);

        JButton btnSave = new JButton("Save Agent"); Dashboard.styleBtn(btnSave, Dashboard.C_ACCENT_DIM);
        
        btnSave.addActionListener(e -> {
            aDAO.saveAgent(new DeliveryAgent(txtAgentId.getText(), txtAgentName.getText(), txtAgentVehicle.getText(), txtAgentEmail.getText()));
            refreshTable(); Dashboard.toast("Agent assigned successfully.", Dashboard.C_ACCENT);
        });

        form.add(btnSave);
        add(form, BorderLayout.EAST);
    }

    private void refreshTable() {
        agentModel.setRowCount(0);
        for (DeliveryAgent a : aDAO.getAllAgents()) {
            agentModel.addRow(new Object[]{a.getId(), a.getName(), a.getVehicleType(), a.getEmail()});
        }
    }
}
