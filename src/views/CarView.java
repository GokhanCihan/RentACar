package views;

import business.CarManager;
import business.ModelManager;
import core.Helper;
import entities.CBoxItem;
import entities.Car;
import entities.Model;

import javax.swing.*;

public class CarView extends ViewLayout {
    private final Car car;
    private final CarManager carManager;
    private ModelManager modelManager;
    private JPanel container;
    private JComboBox<CBoxItem> cbox_model;
    private JComboBox<Car.Color> cbox_color;
    private JTextField field_mileage;
    private JTextField field_plate;
    private JButton button_save;
    private JLabel label_cars;
    private JLabel label_model;
    private JLabel label_color;
    private JLabel label_mileage;
    private JLabel label_plate;

    public CarView(Car car, String action) {
        this.setAction(action);
        this.car = car;
        this.carManager = new CarManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.layoutView(300, 400);

        this.cbox_color.setModel(new DefaultComboBoxModel<>(Car.Color.values()));
        for (Model model: this.modelManager.findAll()) {
            this.cbox_model.addItem(new CBoxItem(model.getId(), model.getName()));
        }
        switch (getAction()){
            case "Add" ->  {
                this.cbox_model.getModel().setSelectedItem(null);
                this.cbox_color.getModel().setSelectedItem(null);
                this.field_mileage.setText("");
                this.field_plate.setText("");
            }
            case "Edit" -> {
                this.cbox_model.getModel().setSelectedItem(new CBoxItem(this.car.getModel().getId(), this.car.getModel().getName()));
                this.cbox_color.getModel().setSelectedItem(this.car.getColor());
                this.field_mileage.setText(String.valueOf(this.car.getMileageKm()));
                this.field_plate.setText(this.car.getPlate());
            }
        }
        this.button_save.addActionListener(e -> {
            if (Helper.isEmptyAny(new JTextField[]{this.field_mileage, this.field_plate})) {
                Helper.showDialog("emptyField");
            } else {
                int selectedId = ((CBoxItem) cbox_model.getSelectedItem()).getKey();
                this.car.setModelId(selectedId);
                this.car.setModel(this.car.getModel());
                this.car.setColor((Car.Color) cbox_color.getSelectedItem());
                this.car.setMileageKm(Integer.parseInt(this.field_mileage.getText()));
                this.car.setPlate(this.field_plate.getText());
                boolean result = false;
                switch (getAction()) {
                    case "Add" -> {
                        result = this.carManager.add(this.car);
                    }
                    case "Edit" -> {
                        result = this.carManager.update(this.car);
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
