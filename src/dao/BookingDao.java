package dao;

import core.DBConnection;
import entities.Booking;

import java.sql.*;
import java.util.ArrayList;

public class BookingDao {
    private Connection connection;
    private final CarDao carDao;

    public BookingDao() {
        this.connection = DBConnection.getInstance();
        this.carDao = new CarDao();
    }

    public ArrayList<Booking> selectByQuery(String query) {
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                bookings.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public ArrayList<Booking> findAll() {
        return this.selectByQuery("SELECT * FROM public.bookings ORDER BY booking_id ASC");
    }

    public boolean add(Booking booking) {
        String query = "INSERT INTO public.bookings(" +
                "car_id," +
                "name," +
                "phone," +
                "email," +
                "start_date," +
                "end_date," +
                "price," +
                "status," +
                "note)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, booking.getCarId());
            preparedStatement.setString(2, booking.getCustomerName());
            preparedStatement.setString(3, booking.getPhone());
            preparedStatement.setString(4, booking.getEmail());
            preparedStatement.setDate(5, Date.valueOf(booking.getStartDate()));
            preparedStatement.setDate(6, Date.valueOf(booking.getEndDate()));
            preparedStatement.setInt(7, booking.getPrice());
            preparedStatement.setString(8, booking.getStatus());
            preparedStatement.setString(9, booking.getNote());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Booking booking) {
        String query = "UPDATE public.bookings SET " +
                "car_id = ?, " +
                "name = ?, " +
                "phone = ?, " +
                "email = ?, " +
                "start_date = ?, " +
                "end_date = ?, " +
                "price = ?, " +
                "status = ?, " +
                "note = ?, " +
                "WHERE car_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, booking.getCarId());
            preparedStatement.setString(2, booking.getCustomerName());
            preparedStatement.setString(3, booking.getPhone());
            preparedStatement.setString(4, booking.getEmail());
            preparedStatement.setDate(5, Date.valueOf(booking.getStartDate()));
            preparedStatement.setDate(6, Date.valueOf(booking.getEndDate()));
            preparedStatement.setInt(7, booking.getPrice());
            preparedStatement.setString(8, booking.getStatus());
            preparedStatement.setString(9, booking.getNote());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.bookings WHERE booking_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Booking match(ResultSet resultSet) {
        Booking booking = new Booking();
        try {
            booking.setId(resultSet.getInt("booking_id"));
            booking.setCarId(resultSet.getInt("car_id"));
            booking.setCustomerName(resultSet.getString("name"));
            booking.setPhone(resultSet.getString("phone"));
            booking.setEmail(resultSet.getString("email"));
            booking.setStartDate(resultSet.getDate("start_date").toLocalDate());
            booking.setStartDate(resultSet.getDate("end_date").toLocalDate());
            booking.setPrice(resultSet.getInt("price"));
            booking.setStatus(resultSet.getString("status"));
            booking.setNote(resultSet.getString("note"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    public Booking getById(int id) {
        Booking booking = null;
        String query = "SELECT * FROM public.bookings WHERE booking_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }
}
