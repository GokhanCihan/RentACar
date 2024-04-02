package views;

import core.Helper;

import javax.swing.*;

public class ViewLayout extends JFrame {
    public void layoutView(int width, int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rent a Car");
        this.setSize(width, height);
        this.setLocation(Helper.getCenter(this.getSize()));
        this.setVisible(true);
    }
}
