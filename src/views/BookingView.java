package views;

import business.CarManager;
import business.ModelManager;
import entities.Booking;

import javax.swing.*;

public class BookingView extends ViewLayout {
    private JPanel container;
    private final Booking booking;

    public BookingView(Booking booking, String action) {
        this.setAction(action);
        this.booking = booking;
        this.add(container);
        this.layoutView(300, 400);
    }
}
