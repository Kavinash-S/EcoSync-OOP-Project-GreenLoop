package greenloop.ui;

import greenloop.dao.ClientDAO;
import greenloop.models.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientPanel extends JPanel {
    private final ClientDAO cDAO;
    private JTable clientTable;
    private DefaultTableModel clientModel;
    private JTextField txtClientId, txtClientName, txtClientContact, txtClientEmail;

    public ClientPanel(ClientDAO cDAO) {
        this.cDAO = cDAO;
        setOpaque(false);
        setLayout(new BorderLayout());

        clientModel = new DefaultTableModel(new String[]{"Client ID", "Name", "Contact", "Email"}, 0);
        clientTable = new JTable(clientModel);
        clientTable.setBackground(Dashboard.C_CARD);
        clientTable.setForeground(Dashboard.C_TEXT);
        refreshTable();

        clientTable.getSelectionModel().addListSelectionListener(e -> {
            int r = clientTable.getSelectedRow(); if (r < 0) return;
            txtClientId.setText(clientModel.getValueAt(r,0).toString());
            txtClientName.setText(clientModel.getValueAt(r,1).toString());
            txtClientContact.setText(clientModel.getValueAt(r,2).toString());
            txtClientEmail.setText(clientModel.getValueAt(r,3).toString());
        });

        add(new JScrollPane(clientTable), BorderLayout.CENTER);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Dashboard.C_CARD);
        form.setPreferredSize(new Dimension(300, 0));

        txtClientId = Dashboard.sField(); form.add(new JLabel("CLIENT ID")); form.add(txtClientId);
        txtClientName = Dashboard.sField(); form.add(new JLabel("COMPANY NAME")); form.add(txtClientName);
        txtClientContact = Dashboard.sField(); form.add(new JLabel("CONTACT NUMBER")); form.add(txtClientContact);
        txtClientEmail = Dashboard.sField(); form.add(new JLabel("EMAIL ADDRESS")); form.add(txtClientEmail);

        JButton btnSave = new JButton("Save Client"); Dashboard.styleBtn(btnSave, Dashboard.C_ACCENT_DIM);
        JButton btnDelete = new JButton("Remove Client"); Dashboard.styleBtn(btnDelete, Dashboard.C_DANGER_DIM);

        btnSave.addActionListener(e -> {
            cDAO.saveClient(new Client(txtClientId.getText(), txtClientName.getText(), txtClientContact.getText(), txtClientEmail.getText()));
            refreshTable(); Dashboard.toast("Client saved successfully.", Dashboard.C_ACCENT);
        });

        btnDelete.addActionListener(e -> {
            cDAO.deleteClient(txtClientId.getText());
            refreshTable(); Dashboard.toast("Client removed.", Dashboard.C_DANGER);
        });

        form.add(btnSave); form.add(btnDelete);
        add(form, BorderLayout.EAST);
    }

    private void refreshTable() {
        clientModel.setRowCount(0);
        for (Client c : cDAO.getAllClients()) {
            clientModel.addRow(new Object[]{c.getId(), c.getName(), c.getContact(), c.getEmail()});
        }
    }
}