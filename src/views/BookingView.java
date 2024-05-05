package views;

import business.BookingManager;
import core.Helper;
import entities.Booking;
import entities.Car;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingView extends ViewLayout {
    private JPanel container;
    private JTextField nameTextField;
    private JTextField phoneTextField;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JTextField priceTextField;
    private JButton saveButton;
    private JLabel carInfoLabel;
    private JTextField mailTextField;
    private JTextField statusTextField;
    private JTextField noteTextField;
    private Booking booking;
    private Car car;
    private BookingManager bookingManager;
    private String action;


    public BookingView(Car selectedCar, String startDate, String endDate, String action) {
        this.setAction(action);
        this.action = action;
        this.bookingManager = new BookingManager();
        this.car = selectedCar;
        this.add(container);
        this.layoutView(300, 500);

        this.carInfoLabel.setText(
                this.car.getModel().getBrand().getName() + "\t" +
                        this.car.getModel().getName() + "\n" +
                        this.car.getPlate()
        );
        this.startDateTextField.setText(startDate);
        this.endDateTextField.setText(endDate);
        saveButton.addActionListener(e -> {
            JTextField[] textFields = {
                    this.nameTextField,
                    this.phoneTextField,
                    this.mailTextField,
                    this.priceTextField,
                    this.statusTextField,
                    this.noteTextField
            };
            if (Helper.isEmptyAny(textFields)) {
                Helper.showDialog("emptyField");
            } else {
                Booking booking = new Booking();
                booking.setCarId(this.car.getId());
                booking.setCustomerName(this.nameTextField.getText());
                booking.setEmail(this.mailTextField.getText());
                booking.setPhone(this.phoneTextField.getText());
                booking.setStartDate(LocalDate.parse(this.startDateTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                booking.setEndDate(LocalDate.parse(this.endDateTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                booking.setPrice(Integer.parseInt(this.priceTextField.getText()));
                booking.setStatus(this.statusTextField.getText());
                booking.setNote(this.noteTextField.getText());

                if (this.bookingManager.add(booking)) {
                    Helper.showDialog("success");
                } else {
                    Helper.showDialog("Error!");
                }
                dispose();
            }
        });
    }
}
