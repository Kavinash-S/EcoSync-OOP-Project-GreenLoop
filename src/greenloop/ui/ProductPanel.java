package greenloop.ui;

import greenloop.dao.ProductDAO;
import greenloop.models.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductPanel extends JPanel {
    private final ProductDAO pDAO;
    private JTable productTable;
    private DefaultTableModel productModel;
    private JTextField txtProdId, txtProdName, txtProdPrice, txtProdEco, txtProdQty, txtProdReorder;

    public ProductPanel(ProductDAO pDAO) {
        this.pDAO = pDAO;
        setOpaque(false);
        setLayout(new BorderLayout());

        productModel = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Eco Rating", "Stock", "Reorder"}, 0);
        productTable = new JTable(productModel);
        productTable.setBackground(Dashboard.C_CARD);
        productTable.setForeground(Dashboard.C_TEXT);
        refreshTable();
        
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Dashboard.C_CARD);
        form.setPreferredSize(new Dimension(300, 0));

        txtProdId = Dashboard.sField(); form.add(new JLabel("PRODUCT ID")); form.add(txtProdId);
        txtProdName = Dashboard.sField(); form.add(new JLabel("PRODUCT NAME")); form.add(txtProdName);
        txtProdPrice = Dashboard.sField(); form.add(new JLabel("PRICE (LKR)")); form.add(txtProdPrice);
        txtProdEco = Dashboard.sField(); form.add(new JLabel("ECO RATING")); form.add(txtProdEco);
        txtProdQty = Dashboard.sField(); form.add(new JLabel("STOCK QTY")); form.add(txtProdQty);
        txtProdReorder = Dashboard.sField(); form.add(new JLabel("REORDER THRESHOLD")); form.add(txtProdReorder);

        JButton btnSave = new JButton("Save Product"); Dashboard.styleBtn(btnSave, Dashboard.C_ACCENT_DIM);
        JButton btnStock = new JButton("Stock-In Entry"); Dashboard.styleBtn(btnStock, Dashboard.C_ACCENT_DIM);

        btnSave.addActionListener(e -> {
            try {
                Product p = new Product(txtProdId.getText(), txtProdName.getText(), Double.parseDouble(txtProdPrice.getText()), txtProdEco.getText(), Integer.parseInt(txtProdQty.getText()), Integer.parseInt(txtProdReorder.getText()));
                pDAO.saveProduct(p); refreshTable(); Dashboard.toast("Product saved!", Dashboard.C_ACCENT);
            } catch (Exception ex) { Dashboard.toast("Fill all fields correctly.", Dashboard.C_DANGER); }
        });

        btnStock.addActionListener(e -> {
            String s = JOptionPane.showInputDialog("Enter incoming stock quantity:");
            if (s != null) {
                try {
                    Product px = pDAO.getAllProducts().stream().filter(p -> p.getId().equalsIgnoreCase(txtProdId.getText())).findFirst().orElse(null);
                    if (px != null) {
                        px.setStockQty(px.getStockQty() + Integer.parseInt(s.trim()));
                        pDAO.saveProduct(px); refreshTable(); Dashboard.toast("Stock updated!", Dashboard.C_ACCENT);
                    }
                } catch (Exception ex) { Dashboard.toast("Invalid quantity.", Dashboard.C_DANGER); }
            }
        });

        form.add(btnSave); form.add(btnStock);
        add(form, BorderLayout.EAST);
    }

    private void refreshTable() {
        productModel.setRowCount(0);
        for (Product p : pDAO.getAllProducts()) {
            productModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getEcoRating(), p.getStockQty(), p.getReorderLevel()});
        }
    }
}