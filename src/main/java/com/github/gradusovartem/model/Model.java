package com.github.gradusovartem.model;

import com.github.gradusovartem.entities.Operation;

import java.util.*;

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
    /* public Model(int id, Operation operation) {
        modelOper.put(id, operation);
    } */

    /**
     * method add() - метод который добавляет составляющие операции в коллекции
     */
    public void add(Operation operation) {
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
