package dao;

import core.DBConnection;
import entities.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection connection;

    public BrandDao() {
        this.connection = DBConnection.getInstance();
    }

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brands = new ArrayList<>();
        String query = "SELECT * FROM public.brands ORDER BY brand_id ASC";
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                Brand brand = this.match(resultSet);
                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    public boolean add(Brand brand) {
        String query = "INSERT INTO public.brands(name) VALUES (?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, brand.getName());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Brand brand) {
        String query = "UPDATE public.brands SET name = ? WHERE brand_id = ? ";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, brand.getName());
            preparedStatement.setInt(2, brand.getId());
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.brands WHERE brand_id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Brand match(ResultSet resultSet) throws SQLException {
        Brand brand = new Brand();
        brand.setId(resultSet.getInt("brand_id"));
        brand.setName(resultSet.getString("name"));

        return brand;
    }

    public Brand getById(int id) {
        Brand brand = null;
        String query = "SELECT * FROM public.brands WHERE brand_id = ? ";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                brand = this.match(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brand;
    }

}
