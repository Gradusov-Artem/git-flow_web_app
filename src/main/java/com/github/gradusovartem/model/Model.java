package com.github.gradusovartem.model;

import com.github.gradusovartem.entities.Operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class Model - хранит данные об операциях
 */
public class Model {
    private static Model instance = new Model();

    private Map<Integer, Operation> modelOper;

    /**
     * method getInstance() - возращает объект типа Model
     * @return
     */
    public static Model getInstance() {
        return instance;
    }

    /**
     * constructor Model
     */
    private Model() {
        modelOper = new HashMap<>();
    }

    public Model(int id, Operation operation) {
        modelOper.put(id, operation);
    }

    /**
     * method add() - метод который добавляет составляющие операции в коллекции
     */
    public void add(int id, Operation operation) {
        modelOper.put(id, operation);
    }

    public Map<Integer, Operation> getModelOper() {
        return modelOper;
    }

    public Operation getOperationById(int id) {
        Operation operationById = modelOper.get(id);
        return operationById;
    }

    public void deleteById(int id) {
        modelOper.remove(id, getOperationById(id));
    }
}
