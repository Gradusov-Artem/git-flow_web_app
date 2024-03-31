package com.github.gradusovartem.entities;

import com.github.gradusovartem.model.Model;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class OperationDao implements Dao<Operation>{
    private static final AtomicInteger id = new AtomicInteger(0);
    private Model model = Model.getInstance();

    @Override
    public Operation get(int id) {
        return model.getOperationById(id);
    }

    @Override
    public Collection<Operation> getAll() {
        return model.getValues();
    }

    @Override
    public void add(Operation operation) {
        model.add(operation);
    }

    @Override
    public void update(Operation operation, String comment) {
        operation.setComment(comment);
    }

    @Override
    public void delete(Operation operation) {
        model.removeById(operation.getId());
    }
}
