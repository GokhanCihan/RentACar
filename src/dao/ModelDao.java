package dao;

import core.DBConnection;
import entities.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelDao {
    private final Connection connection;
    private final BrandDao brandDao = new BrandDao();

    public ModelDao() {
        this.connection = DBConnection.getInstance();
    }

    public ArrayList<Model> selectByQuery(String query) {
        ArrayList<Model> models = new ArrayList<>();
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                models.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return models;
    }

    public ArrayList<Model> getModelsByBrandId(int brandId) {
        return this.selectByQuery("SELECT * FROM public.models WHERE brand_id = " + brandId);
    }

    public ArrayList<Model> findAll() {
        return this.selectByQuery("SELECT * FROM public.models ORDER BY model_id ASC");
    }

    public boolean add(Model model) {
        String query = "INSERT INTO public.models(" +
                "brand_id," +
                "name," +
                "release_year," +
                "body_type," +
                "fuel_type," +
                "gear_type)" +
                " VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, model.getBrandId());
            preparedStatement.setString(2, model.getName());
            preparedStatement.setInt(3, model.getReleaseYear());
            preparedStatement.setString(4, model.getBodyType().toString());
            preparedStatement.setString(5, model.getFuelType().toString());
            preparedStatement.setString(6, model.getGearType().toString());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Model model) {
        String query = "UPDATE public.models SET " +
                "brand_id = ?, " +
                "name = ?, " +
                "release_year = ?, " +
                "body_type = ?, " +
                "fuel_type = ?, " +
                "gear_type = ? " +
                "WHERE model_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, model.getBrandId());
            preparedStatement.setString(2, model.getName());
            preparedStatement.setInt(3, model.getReleaseYear());
            preparedStatement.setString(4, model.getBodyType().toString());
            preparedStatement.setString(5, model.getFuelType().toString());
            preparedStatement.setString(6, model.getGearType().toString());
            preparedStatement.setInt(7, model.getId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.models WHERE model_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Model match(ResultSet resultSet) throws SQLException {
        Model model = new Model();
        model.setId(resultSet.getInt("model_id"));
        model.setBrandId(resultSet.getInt("brand_id"));
        model.setName(resultSet.getString("name"));
        model.setReleaseYear(resultSet.getInt("release_year"));
        model.setBodyType(Model.BodyType.valueOf(resultSet.getString("body_type")));
        model.setFuelType(Model.FuelType.valueOf(resultSet.getString("fuel_type")));
        model.setGearType(Model.GearType.valueOf(resultSet.getString("gear_type")));
        model.setBrand(this.brandDao.getById(resultSet.getInt("brand_id")));
        return model;
    }

    public Model getById(int id) {
        Model model = null;
        String query = "SELECT * FROM public.models WHERE model_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                model = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public ArrayList<Model> filterModels(Integer brandId, String bodyType, String fuelType, String gearType) {

        String query = "SELECT * FROM public.models WHERE brand_id = '" + brandId +
                "' AND body_type = '" + bodyType +
                "' AND fuel_type = '" + fuelType +
                "' AND gear_type = '" + gearType + "'";
        return selectByQuery(query);
    }
}