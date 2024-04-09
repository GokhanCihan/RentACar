package business;

import core.Helper;
import dao.CarDao;
import entities.Car;

import java.util.ArrayList;

public class CarManager {
    private final CarDao carDao;

    public CarManager() {
        this.carDao = new CarDao();
    }

    public Car getById(int id) {
        return this.carDao.getById(id);
    }

    public ArrayList<Car> findAll() {
        return carDao.findAll();
    }

    public ArrayList<Car> getByBrandId(int brandId) {
        return carDao.getByBrandId(brandId);
    }

    public ArrayList<Car> getByModelId(int modelId) {
        return carDao.getByModelId(modelId);
    }

    public ArrayList<Object[]> getRowsForTable(int size) {
        ArrayList<Object[]> rows = new ArrayList<>();
        for (Car car: this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = car.getId();
            rowObject[i++] = car.getModel().getBrand().getName();
            rowObject[i++] = car.getModel().getName();
            rowObject[i++] = car.getPlate();
            rowObject[i++] = car.getColor();
            rowObject[i++] = car.getMileageKm();
            rowObject[i++] = car.getModel().getReleaseYear();
            rowObject[i++] = car.getModel().getBodyType();
            rowObject[i++] = car.getModel().getFuelType();
            rowObject[i++] = car.getModel().getGearType();
            rows.add(rowObject);
        }
        return rows;
    }

    public boolean add(Car car) {
        return this.carDao.add(car);
    }

    public boolean update(Car car) {
        return this.carDao.update(car);
    }

    public boolean delete(int id){
        if (this.getById(id) == null) {
            Helper.showDialog("notFound");
            return false;
        }
        return this.carDao.delete(id);
    }
}
