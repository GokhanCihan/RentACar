package business;

import core.Helper;
import dao.ModelDao;
import entities.Model;

import java.util.ArrayList;

public class ModelManager {
    private final ModelDao modelDao;

    public ModelManager() {
        this.modelDao = new ModelDao();
    }

    public ArrayList<Object[]> getRowsForTable(int size) {
        ArrayList<Object[]> modelRows = new ArrayList<>();
        for (Model model : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = model.getId();
            rowObject[i++] = model.getBrand().getName();
            rowObject[i++] = model.getName();
            rowObject[i++] = model.getReleaseYear();
            rowObject[i++] = model.getBodyType();
            rowObject[i++] = model.getFuelType();
            rowObject[i++] = model.getGearType();
            modelRows.add(rowObject);
        }
        return modelRows;
    }

    private ArrayList<Model> findAll() {
        return this.modelDao.findAll();
    }

    public boolean add(Model model) {
        return this.modelDao.add(model);
    }

    public boolean update(Model model) {
        return this.modelDao.save(model);
    }

    public boolean delete(int id){
        if (this.getById(id) == null) {
            Helper.showDialog("notFound");
            return false;
        }

        return this.modelDao.delete(id);
    }

    public Model getById(int id) {
        return this.modelDao.getById(id);
    }
}
