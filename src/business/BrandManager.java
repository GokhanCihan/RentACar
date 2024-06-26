package business;

import core.Helper;
import dao.BrandDao;
import entities.Brand;
import entities.Model;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;
    private final ModelManager modelManager;

    public BrandManager() {
        this.brandDao = new BrandDao();
        this.modelManager = new ModelManager();
    }

    public ArrayList<Object[]> getRowsForTable(int size) {
        ArrayList<Object[]> brandRows = new ArrayList<>();
        for (Brand brand : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brand.getId();
            rowObject[i++] = brand.getName();
            brandRows.add(rowObject);
        }
        return brandRows;
    }

    public ArrayList<Brand> findAll() {
        return this.brandDao.findAll();
    }

    public boolean add(Brand brand) {
        return this.brandDao.add(brand);
    }

    public boolean update(Brand brand) {
        return this.brandDao.update(brand);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showDialog("notFound");
            return false;
        }
        for (Model model: this.modelManager.getModelsByBrandId(id)){
            this.modelManager.delete(model.getId());
        }
        return this.brandDao.delete(id);
    }

    public Brand getById(int id) {
        return this.brandDao.getById(id);
    }
}
