package views;

import business.BrandManager;
import core.Helper;
import entities.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends ViewLayout {
    private JPanel container;
    private JLabel label_welcome;
    private JPanel container_welcome;
    private JTabbedPane tabbedPane;
    private JButton button_logout;
    private JPanel pane_brand;
    private JScrollPane spane_brand;
    private JTable table_brand;
    private final User user;
    private final DefaultTableModel table_model_brand = new DefaultTableModel();
    private final BrandManager brandManager = new BrandManager();
    private JPopupMenu popupMenu = new JPopupMenu();


    public AdminView(User user) {
        this.add(container);
        this.layoutView(1000, 1000);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.label_welcome.setText("Welcome " + this.user.getUsername() + "!");

        loadBrandTable();

        this.table_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table_brand.rowAtPoint(e.getPoint());
                table_brand.setRowSelectionInterval(selected_row, selected_row);
            }
        });

        this.popupMenu.add("Add").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        this.popupMenu.add("Edit").addActionListener(e -> {
            int selectedId = this.getSelectedIdAt(table_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectedId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        this.popupMenu.add("Delete").addActionListener(e -> {
            if (Helper.confirmDelete()){
                int selectedId = getSelectedIdAt(table_brand, 0);
                if (this.brandManager.delete(selectedId)) {
                    loadBrandTable();
                }else {
                    Helper.showDialog("error");
                }
            }

        });

        this.table_brand.setComponentPopupMenu(popupMenu);
    }

    private void loadBrandTable() {
        Object[] column_brand = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brands = this.brandManager.getRowsForTable(column_brand.length);
        this.createTable(table_model_brand, this.table_brand, column_brand, brands);
    }
}
