package views;

import business.BrandManager;
import core.Helper;
import entities.Brand;

import javax.swing.*;

public class BrandView extends ViewLayout{
    private JPanel container;
    private JLabel label_brand;
    private JLabel label_brand_name;
    private JTextField field_brand_name;
    private JButton button_save;
    private final Brand brand;
    private final BrandManager brandManager;

    public BrandView(Brand brand) {
        this.brandManager = new BrandManager();
        this.brand = brand;
        this.add(container);
        this.layoutView(500, 200);

        if (brand != null) {
            this.field_brand_name.setText(brand.getName());
        }

        button_save.addActionListener(e -> {
            if (Helper.isEmpty(this.field_brand_name)){
                Helper.showDialog("emptyField");
            }else {
                boolean result;
                if(this.brand == null) {
                    result = this.brandManager.add(new Brand(field_brand_name.getText()));
                } else {
                    this.brand.setName(field_brand_name.getText());
                    result = this.brandManager.update(this.brand);
                }
                if (result){
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
