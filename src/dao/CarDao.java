package dao;

import core.DBConnection;
import entities.Booking;
import entities.Car;
import entities.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CarDao {
    private final Connection connection;
    private final BrandDao brandDao = new BrandDao();
    private final ModelDao modelDao = new ModelDao();
    private final BookingDao bookingDao = new BookingDao();

    public CarDao() {
        this.connection = DBConnection.getInstance();
    }

    public Car getById(int id) {
        Car car = null;
        String query = "SELECT * FROM public.cars WHERE car_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public ArrayList<Car> findAll() {
        return this.selectByQuery("SELECT * FROM public.cars ORDER BY car_id ASC");
    }

    public ArrayList<Car> searchCars(
            String startDate,
            String endDate,
            Model.BodyType bodyType,
            Model.FuelType fuelType,
            Model.GearType gearType) {
        String query = "SELECT * FROM public.cars as c LEFT JOIN public.models as m";
        ArrayList<String> carTypeConditions = new ArrayList<>();
        ArrayList<String> join = new ArrayList<>();
        ArrayList<String> dateConditions = new ArrayList<>();
        join.add("c.model_id = m.model_id");
        startDate = String.valueOf(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        endDate = String.valueOf(LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        if (bodyType != null) {
            carTypeConditions.add("m.body_type = '" + bodyType.toString() + "'");
        }
        if (gearType != null) {
            carTypeConditions.add("m.gear_type = '" + gearType.toString() + "'");
        }
        if (fuelType != null) {
            carTypeConditions.add("m.fuel_type = '" + fuelType.toString() + "'");
        }
        String conditionStr = String.join(" AND ", carTypeConditions);
        String joinStr = String.join(" AND ", join);
        if (!joinStr.isEmpty()) {
            query += " ON " + joinStr;
        }
        if (!conditionStr.isEmpty()) {
            query += " WHERE " + conditionStr;
        }

        ArrayList<Car> cars = this.selectByQuery(query);
        dateConditions.add("('" + startDate + "' BETWEEN start_date AND end_date)");
        dateConditions.add("('" + endDate + "' BETWEEN start_date AND end_date)");
        dateConditions.add("(start_date BETWEEN '" + startDate + "' AND '" + endDate + "')");
        dateConditions.add("(end_date BETWEEN '" + startDate + "' AND '" + endDate + "')");

        String dateQuery = "SELECT * FROM public.bookings WHERE" + String.join(" OR ", dateConditions);

        System.out.println(dateQuery);
        ArrayList<Booking> bookings = this.bookingDao.selectByQuery(dateQuery);
        ArrayList<Integer> carIds = new ArrayList<>();
        for (Booking booking: bookings) {
            carIds.add(booking.getCarId());
        }
        cars.removeIf(car -> carIds.contains(car.getId()));
        return cars;
    }

    public ArrayList<Car> getByBrandId(int brandId) {
        return this.selectByQuery("SELECT * FROM public.cars WHERE brand_id = " + brandId);
    }

    public ArrayList<Car> getByModelId(int modelId) {
        return this.selectByQuery("SELECT * FROM public.cars WHERE model_id = " + modelId);
    }

    public Car match(ResultSet resultSet) {
        Car car = new Car();
        try {
            car.setId(resultSet.getInt("car_id"));
            car.setModelId(resultSet.getInt("model_id"));
            car.setColor(Car.Color.valueOf(resultSet.getString("color")));
            car.setMileageKm((resultSet.getInt("mileage_km")));
            car.setPlate(resultSet.getString("Plate"));
            car.setModel(this.modelDao.getById(resultSet.getInt("model_id")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    private ArrayList<Car> selectByQuery(String query) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                cars.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public boolean add(Car car) {
        String query = "INSERT INTO public.cars(" +
                "model_id," +
                "color," +
                "mileage_km," +
                "Plate)" +
                " VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, car.getModelId());
            preparedStatement.setString(2, car.getColor().toString());
            preparedStatement.setInt(3, car.getMileageKm());
            preparedStatement.setString(4, car.getPlate());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Car car) {
        String query = "UPDATE public.cars SET " +
                "model_id = ?, " +
                "color = ?, " +
                "mileage_km = ?, " +
                "plate = ? " +
                "WHERE car_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, car.getModelId());
            preparedStatement.setString(2, car.getColor().toString());
            preparedStatement.setInt(3, car.getMileageKm());
            preparedStatement.setString(4, car.getPlate());
            preparedStatement.setInt(5, car.getId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.cars WHERE car_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
