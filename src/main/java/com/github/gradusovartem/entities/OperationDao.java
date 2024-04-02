package com.github.gradusovartem.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OperationDao implements Dao<Operation>{
    // private Model model = Model.getInstance();
    private Map<Integer, Operation> modelOper;
    public OperationDao() {
        modelOper = new HashMap<>();
    }

    @Override
    public Operation get(int id) {
        return getOperationById(id);
    }

    @Override
    public Collection<Operation> getAll() {
        return getValues();
    }

    @Override
    public boolean add(Operation operation) {
        addOperation(operation);
        return true;
    }

    @Override
    public boolean update(int id, String comment) {
        // operation.setComment(comment);
        Operation operation = getOperationById(id);
        if (operation != null) {
            operation.setComment(comment);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Operation operation = getOperationById(id);
        if (operation != null) {
            removeById(id);
            return true;
        }
        return false;
    }

    public void addOperation(Operation operation) {
        modelOper.put(operation.getId(), operation);
    }

    /* private Map<Integer, Operation> getModelOper() {
        return modelOper;
    } */

    public Operation getOperationById(int id) {
        Operation operationById = modelOper.get(id);
        return operationById;
    }

    public Operation removeById(int id) {
        Operation operationById = getOperationById(id);
        modelOper.remove(id, getOperationById(id));
        return operationById;
    }
    public Collection<Operation> getValues() {
        Collection<Operation> values = modelOper.values();
        return values;
    }
}
