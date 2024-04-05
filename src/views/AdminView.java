package views;

import business.BrandManager;
import business.ModelManager;
import core.Helper;
import entities.Model;
import entities.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends ViewLayout {
    private JPanel container;
    private JLabel label_welcome;
    private JPanel container_welcome;
    private JTabbedPane tabbedPane;
    private JButton button_logout;
    private JPanel panel_brands;
    private JScrollPane scroll_pane_brands;
    private JTable table_brands;
    private JPanel panel_models;
    private JScrollPane scroll_pane_models;
    private JTable table_models;
    private final User user;
    private final DefaultTableModel table_model_brand = new DefaultTableModel();
    private final DefaultTableModel table_model_model = new DefaultTableModel();
    private final BrandManager brandManager = new BrandManager();
    private final ModelManager modelManager = new ModelManager();
    private JPopupMenu popup_menu_brands = new JPopupMenu();
    private JPopupMenu popup_menu_models = new JPopupMenu();

    public AdminView(User user) {
        this.add(container);
        this.layoutView(1000, 400);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.label_welcome.setText("Welcome " + this.user.getUsername() + "!");
        loadBrandsTable();
        loadModelsTable();
        configureBrandsMenu();
        configureModelsMenu();
    }

    private void loadBrandsTable() {
        Object[] columns = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brands = this.brandManager.getRowsForTable(columns.length);
        this.createTable(this.table_model_brand, this.table_brands, columns, brands);
    }

    private void loadModelsTable() {
        Object[] columns = { "Model ID", "Brand", "Model", "Release Year", "Body Type", "Fuel Type", "Gear Type"};
        ArrayList<Object[]> models = this.modelManager.getRowsForTable(columns.length);
        this.createTable(this.table_model_model, this.table_models, columns, models);
    }

    private void configureBrandsMenu() {
        this.listenRowSelectionAt(table_brands);
        this.popup_menu_brands.add("Add").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandsTable();
                    loadModelsTable();
                }
            });
        });
        this.popup_menu_brands.add("Edit").addActionListener(e -> {
            int selectedId = this.getIdAtSelectedRow(table_brands, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectedId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandsTable();
                    loadModelsTable();
                }
            });
        });
        this.popup_menu_brands.add("Delete").addActionListener(e -> {
            if (Helper.confirmDelete()){
                int selectedId = getIdAtSelectedRow(table_brands, 0);
                if (this.brandManager.delete(selectedId)) {
                    loadBrandsTable();
                    loadModelsTable();
                }else {
                    Helper.showDialog("error");
                }
            }
        });
        this.table_brands.setComponentPopupMenu(popup_menu_brands);
    }

    private void configureModelsMenu() {
        this.listenRowSelectionAt(table_models);
        this.popup_menu_models.add("Add").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model(), e.getActionCommand());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelsTable();
                }
            });
        });
        this.popup_menu_models.add("Edit").addActionListener(e -> {
            int selectedId = this.getIdAtSelectedRow(table_models, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectedId), e.getActionCommand());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelsTable();
                }
            });
        });
        this.popup_menu_models.add("Delete").addActionListener(e -> {
            if (Helper.confirmDelete()){
                int selectedId = getIdAtSelectedRow(table_models, 0);
                if (this.modelManager.delete(selectedId)) {
                    loadModelsTable();
                    Helper.showDialog("success");
                }else {
                    Helper.showDialog("error");
                }
            }
        });
        this.table_models.setComponentPopupMenu(popup_menu_models);
    }
}
