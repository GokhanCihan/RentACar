package business;

import core.Helper;
import dao.BookingDao;
import entities.Booking;

import java.util.ArrayList;

public class BookingManager {
    private final BookingDao bookingDao;

    public BookingManager() {
        this.bookingDao = new BookingDao();
    }

    public ArrayList<Object[]> getRowsForTable(int size) {
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Booking booking : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = booking.getId();
            rowObject[i++] = booking.getCustomerName();
            rowObject[i++] = booking.getPhone();
            rowObject[i++] = booking.getEmail();
            rowObject[i++] = booking.getStartDate();
            rowObject[i++] = booking.getEndDate();
            rowObject[i++] = booking.getPrice();
            rowObject[i++] = booking.getStatus();
            rowObject[i++] = booking.getNote();
            rows.add(rowObject);
        }
        return rows;
    }

    public ArrayList<Booking> findAll() {
        return bookingDao.findAll();
    }

    public boolean add(Booking booking) {
        return bookingDao.add(booking);
    }

    public boolean update(Booking booking) {
        return this.bookingDao.update(booking);
    }

    public boolean delete(int id){
        if (this.getById(id) == null) {
            Helper.showDialog("notFound");
            return false;
        }
        return this.bookingDao.delete(id);
    }

    private Booking getById(int id) {
        return this.bookingDao.getById(id);
    }
}
