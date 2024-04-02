package views;

import business.BrandManager;
import entities.Brand;
import entities.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class AdminView extends ViewLayout {
    private JPanel container;
    private JLabel label_welcome;
    private JPanel container_welcome;
    private JTabbedPane tabbedPane1;
    private JButton button_logout;
    private JPanel pane_brand;
    private JScrollPane spane_brand;
    private JTable table_brand;
    private final User user;
    private DefaultTableModel table_model_brand = new DefaultTableModel();
    private BrandManager brandManager = new BrandManager();
    private JPopupMenu popupMenu = new JPopupMenu();


    public AdminView(User user) {
        this.add(container);
        this.layoutView(1000, 1000);
        this.user = user;
        if (this.user == null){
            dispose();
        }
        this.label_welcome.setText("Welcome " + this.user.getUsername() + "!");
        Object[] column_brand = { "Brand ID", "Brand Name" };
        ArrayList<Brand> brands = this.brandManager.findAll();
        this.table_model_brand.setColumnIdentifiers(column_brand);
        for (Brand brand: brands) {
            Object[] row = {brand.getId(), brand.getName()};
            table_model_brand.addRow(row);
        }
        this.table_brand.setModel(table_model_brand);
        this.table_brand.getTableHeader().setReorderingAllowed(false);
        this.table_brand.setEnabled(false);
        this.table_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table_brand.rowAtPoint(e.getPoint());
                table_brand.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        this.popupMenu.add("Add").addActionListener(e -> {
            System.out.println("Add brand");
        });
        this.popupMenu.add("Edit");
        this.popupMenu.add("Delete");

        this.table_brand.setComponentPopupMenu(popupMenu);
    }
}
