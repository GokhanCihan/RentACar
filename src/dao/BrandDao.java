package dao;

import core.DBConnection;
import entities.Brand;
import entities.User;

import java.sql.Connection;
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
        String query = "SELECT * FROM public.brands ";
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

    public Brand match(ResultSet resultSet) throws SQLException {
        Brand brand = new Brand();
        brand.setId(resultSet.getInt("brand_id"));
        brand.setName(resultSet.getString("name"));

        return brand;
    }
}
