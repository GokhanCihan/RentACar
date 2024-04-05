package views;

import business.BrandManager;
import business.ModelManager;
import core.Helper;
import entities.Brand;
import entities.CBoxItem;
import entities.Model;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class ModelView extends ViewLayout {

    private JPanel container;
    private JLabel label_model;
    private JLabel label_brand_name;
    private JLabel label_model_name;
    private JTextField field_model_name;
    private JLabel label_release_year;
    private JTextField field_release_year;
    private JLabel label_body_type;
    private JLabel label_fuel_type;
    private JLabel label_gear_type;
    private JButton button_save;
    private JComboBox<Model.BodyType> cbox_body_type;
    private JComboBox<Model.FuelType> cbox_fuel_type;
    private JComboBox<Model.GearType> cbox_gear_type;
    private JComboBox<CBoxItem> cbox_brand;
    private Model model;
    private ModelManager modelManager;
    private BrandManager brandManager;

    public ModelView(Model model, String action) {
        this.setAction(action);
        this.model = model;
        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.add(container);
        this.layoutView(500, 200);

        for (Brand brand : this.brandManager.findAll()) {
            this.cbox_brand.addItem(new CBoxItem(brand.getId(), brand.getName()));
        }
        this.cbox_body_type.setModel(new DefaultComboBoxModel<>(Model.BodyType.values()));
        this.cbox_fuel_type.setModel(new DefaultComboBoxModel<>(Model.FuelType.values()));
        this.cbox_gear_type.setModel(new DefaultComboBoxModel<>(Model.GearType.values()));

        switch (getAction()){
            case "Add" ->  {
                this.cbox_brand.getModel().setSelectedItem(null);
                this.cbox_body_type.getModel().setSelectedItem(null);
                this.cbox_fuel_type.getModel().setSelectedItem(null);
                this.cbox_gear_type.getModel().setSelectedItem(null);
            }
            case "Edit" -> {
                this.field_model_name.setText(this.model.getName());
                this.field_release_year.setText(String.valueOf(this.model.getReleaseYear()));
                this.cbox_body_type.getModel().setSelectedItem(this.model.getBodyType());
                this.cbox_fuel_type.getModel().setSelectedItem(this.model.getFuelType());
                this.cbox_gear_type.getModel().setSelectedItem(this.model.getGearType());
                this.cbox_brand.getModel().setSelectedItem(new CBoxItem(this.model.getBrand().getId(), this.model.getBrand().getName()));
            }
        }
        this.button_save.addActionListener(e -> {
            if (Helper.isEmptyAny(new JTextField[]{this.field_model_name, this.field_release_year})) {
                Helper.showDialog("emptyField");
            } else {
                int selectedId = ((CBoxItem) cbox_brand.getSelectedItem()).getKey();
                this.model.setBrandId(selectedId);
                this.model.setName(field_model_name.getText());
                this.model.setReleaseYear(Integer.parseInt(field_release_year.getText()));
                this.model.setBodyType((Model.BodyType) cbox_body_type.getSelectedItem());
                this.model.setFuelType((Model.FuelType) cbox_fuel_type.getSelectedItem());
                this.model.setGearType((Model.GearType) cbox_gear_type.getSelectedItem());
                boolean result = false;
                switch (getAction()) {
                    case "Add" -> {
                        result = this.modelManager.add(this.model);
                    }
                    case "Edit" -> {
                        result = this.modelManager.update(this.model);
                    }
                }
                if (result) {
                    Helper.showDialog("success");
                    dispose();
                }else {
                    Helper.showDialog("error");
                    dispose();
                }
            }
        });

    }
}
