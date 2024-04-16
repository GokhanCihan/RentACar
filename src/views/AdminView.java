package views;

import business.BookingManager;
import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.Helper;
import entities.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private JPanel container_filter;
    private JLabel label_filter_brand;
    private JComboBox<CBoxItem> cbox_filter_brand;
    private JLabel label_filter_body;
    private JComboBox<Model.BodyType> cbox_filter_body;
    private JLabel label_filter_fuel;
    private JComboBox<Model.FuelType> cbox_filter_fuel;
    private JLabel label_filter_gear;
    private JComboBox<Model.GearType> cbox_filter_gear;
    private JButton button_filter;
    private JButton button_reset;
    private JPanel panel_cars;
    private JTable table_cars;
    private JScrollPane scroll_pane_cars;
    private JPanel panel_booking;
    private JScrollPane scroll_pane_booking;
    private JTable table_booking;
    private JPanel container_filter_booking;
    private JComboBox<Model.BodyType> cbox_body_type;
    private JComboBox<Model.FuelType> cbox_fuel_type;
    private JComboBox<Model.GearType> cbox_gear_type;
    private JSpinner spinner_start_day;
    private JSpinner spinner_end_day;
    private JSpinner spinner_start_month;
    private JSpinner spinner_start_year;
    private JSpinner spinner_end_month;
    private JSpinner spinner_end_year;
    private final User user;
    private final DefaultTableModel table_model_brand = new DefaultTableModel();
    private final DefaultTableModel table_model_model = new DefaultTableModel();
    private final DefaultTableModel table_model_car = new DefaultTableModel();
    private final DefaultTableModel table_model_booking = new DefaultTableModel();
    private final BrandManager brandManager = new BrandManager();
    private final ModelManager modelManager = new ModelManager();
    private final CarManager carManager = new CarManager();
    private final BookingManager bookingManager = new BookingManager();
    private final JPopupMenu popup_menu_brands = new JPopupMenu();
    private final JPopupMenu popup_menu_models = new JPopupMenu();
    private final JPopupMenu popup_menu_cars = new JPopupMenu();
    private final JPopupMenu popup_menu_bookings = new JPopupMenu();
    private  ArrayList<Model> models;

    public AdminView(User user) {
        this.add(container);
        this.layoutView(1000, 400);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.label_welcome.setText("Welcome " + this.user.getUsername() + "!");
        configureBrandsPanel();
        configureModelsPanel();
        configureCarsPanel();
        configureBookingsPanel();
        configureModelsFilter();
        loadBrandsTable();
        loadModelsTable();
        loadCarsTable();
        loadBookingTable();

        configureBookingFilters();
    }

    private void loadBrandsTable() {
        Object[] columns = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brands = this.brandManager.getRowsForTable(columns.length);
        this.createTable(this.table_model_brand, this.table_brands, columns, brands);
    }

    private void loadModelsTable() {
        Object[] columns = {"Model ID", "Brand", "Model", "Release Year", "Body Type", "Fuel Type", "Gear Type"};
        ArrayList<Object[]> rows = this.modelManager.getRowsForTable(columns.length);
        this.createTable(this.table_model_model, this.table_models, columns, rows);
    }

    private void loadCarsTable() {
        Object[] columns = {"ID", "Brand", "Model", "Plate", "Color", "Mileage (km)", "Release Year",
                "Body Type", "Fuel Type", "Gear Type"};
        ArrayList<Object[]> rows = this.carManager.getRowsForTable(columns.length);
        this.createTable(this.table_model_car, this.table_cars, columns, rows);
    }

    private void loadBookingTable() {
        Object[] columns = {"ID", "Car", "Customer", "Phone", "Email", "Start Date", "End Date",
                "Price", "Status", "Notes"};
        ArrayList<Object[]> rows = this.bookingManager.getRowsForTable(columns.length);
        this.createTable(this.table_model_booking, this.table_booking, columns, rows);
    }

    private void configureBrandsPanel() {
        this.listenRowSelection(table_brands);
        this.popup_menu_brands.add("Add").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandsTable();
                    loadModelsTable();
                    loadCarsTable();
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
                    loadCarsTable();
                }
            });
        });
        this.popup_menu_brands.add("Delete").addActionListener(e -> {
            if (Helper.confirmDelete()) {
                int selectedId = getIdAtSelectedRow(table_brands, 0);
                if (this.brandManager.delete(selectedId)) {
                    loadBrandsTable();
                    loadModelsTable();
                    loadCarsTable();
                } else {
                    Helper.showDialog("error");
                }
            }
        });
        this.table_brands.setComponentPopupMenu(popup_menu_brands);
    }

    private void configureModelsPanel() {
        this.listenRowSelection(table_models);
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
                    loadCarsTable();
                }
            });
        });
        this.popup_menu_models.add("Delete").addActionListener(e -> {
            if (Helper.confirmDelete()) {
                int selectedId = getIdAtSelectedRow(table_models, 0);
                if (this.modelManager.delete(selectedId)) {
                    loadModelsTable();
                    loadCarsTable();
                    Helper.showDialog("success");
                } else {
                    Helper.showDialog("error");
                }
            }
        });
        this.table_models.setComponentPopupMenu(popup_menu_models);
    }

    private void configureCarsPanel() {
        this.listenRowSelection(table_cars);
        this.popup_menu_cars.add("Add").addActionListener(e -> {
            CarView carView = new CarView(new Car(), e.getActionCommand());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarsTable();
                }
            });
        });
        this.popup_menu_cars.add("Edit").addActionListener(e -> {
            int selectedId = this.getIdAtSelectedRow(table_cars, 0);
            CarView carView = new CarView(this.carManager.getById(selectedId), e.getActionCommand());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarsTable();
                }
            });
        });
        this.popup_menu_cars.add("Delete").addActionListener(e -> {
            if (Helper.confirmDelete()) {
                int selectedId = getIdAtSelectedRow(table_cars, 0);
                if (this.carManager.delete(selectedId)) {
                    loadCarsTable();
                    Helper.showDialog("success");
                } else {
                    Helper.showDialog("error");
                }
            }
        });
        this.table_cars.setComponentPopupMenu(popup_menu_cars);
    }

    private void configureBookingsPanel() {
        this.listenRowSelection(table_booking);
        this.popup_menu_bookings.add("Add").addActionListener(e -> {
            BookingView bookingView = new BookingView(new Booking(), e.getActionCommand());
            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandsTable();
                    loadModelsTable();
                    loadCarsTable();
                    loadBookingTable();
                }
            });
        });
        this.popup_menu_bookings.add("Edit").addActionListener(e -> {
            int selectedId = this.getIdAtSelectedRow(table_booking, 0);
            BookingView bookingView = new BookingView(this.bookingManager.getById(selectedId), e.getActionCommand());
            bookingView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandsTable();
                    loadModelsTable();
                    loadCarsTable();
                    loadBookingTable();
                }
            });
        });
        this.popup_menu_bookings.add("Delete").addActionListener(e -> {
            if (Helper.confirmDelete()) {
                int selectedId = getIdAtSelectedRow(table_booking, 0);
                if (this.bookingManager.delete(selectedId)) {
                    loadBrandsTable();
                    loadModelsTable();
                    loadCarsTable();
                    loadBookingTable();
                } else {
                    Helper.showDialog("error");
                }
            }
        });
        this.table_booking.setComponentPopupMenu(popup_menu_bookings);
    }

    private void configureModelsFilter() {
        for (Brand brand : this.brandManager.findAll()) {
            this.cbox_filter_brand.addItem(new CBoxItem(brand.getId(), brand.getName()));
        }
        this.cbox_filter_body.setModel(new DefaultComboBoxModel<>(Model.BodyType.values()));
        this.cbox_filter_fuel.setModel(new DefaultComboBoxModel<>(Model.FuelType.values()));
        this.cbox_filter_gear.setModel(new DefaultComboBoxModel<>(Model.GearType.values()));
        this.button_filter.addActionListener(e -> {
            int selectedId = ((CBoxItem) cbox_filter_brand.getModel().getSelectedItem()).getKey();
            String bodyType = cbox_filter_body.getModel().getSelectedItem().toString();
            String fuelType = cbox_filter_fuel.getModel().getSelectedItem().toString();
            String gearType = cbox_filter_gear.getModel().getSelectedItem().toString();
            this.models = this.modelManager.filterModels(selectedId, bodyType, fuelType, gearType);
            Object[] columns = {"Model ID", "Brand", "Model", "Release Year", "Body Type", "Fuel Type", "Gear Type"};
            ArrayList<Object[]> rows = this.modelManager.getUpdatedRowsForTable(columns.length, this.models);
            this.createTable(this.table_model_model, this.table_models, columns, rows);
        });
        this.button_reset.addActionListener(e -> {
           loadModelsTable();
        });
    }

    private void configureBookingFilters() {

        this.spinner_start_day.setModel(new SpinnerDateModel());
        this.spinner_start_day.setEditor(new JSpinner.DateEditor(this.spinner_end_day,"dd.MM.yyyy"));
        this.spinner_start_month.setModel(new SpinnerNumberModel(1, 1,12,1));
        this.spinner_start_year.setModel(new SpinnerNumberModel(2020, 2020,2030,1));

    }

}