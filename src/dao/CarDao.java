package dao;

import core.DBConnection;
import entities.Car;
import entities.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarDao {
    private final Connection connection;
    private final BrandDao brandDao = new BrandDao();
    private ModelDao modelDao = new ModelDao();

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
