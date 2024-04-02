package com.github.gradusovartem.entities;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class OperationService implements Service {
    private final AtomicInteger id = new AtomicInteger(0);
    private Dao<Operation> operationDao = new OperationDao();

    @Override
    public Operation get(int id) {
        Operation currentOperation = operationDao.get(id);
        if (currentOperation != null)
            return currentOperation;
        return null;
    }

    @Override
    public Collection<Operation> getAll() {
        return operationDao.getAll();
    }

    @Override
    public Operation add(Operation operation) {
        if (operation.getOperation() == null) {
            return null;
        }

        switch (operation.getOperation()) {
            case "+":
            case "-":
            case "*":
            case "/":
                break;
            default:
                return null;
        }

        int result = calculation(operation);
        operation.setId(generateUniqueID());
        operation.setResult(result);
        Operation currentOperation = new Operation(operation);
        operationDao.add(currentOperation);
        currentOperation = operationDao.get(Integer.parseInt(String.valueOf(id)));
        return currentOperation;
    }

    @Override
    public Operation update(int id, String comment) {
        Operation currentOperation = operationDao.get(id);
        if (currentOperation != null) {
            if (operationDao.update(id, comment)) {
                currentOperation = operationDao.get(id);
                return currentOperation;
            }
        }
        return null;
    }

    @Override
    public Operation delete(int id) {
        Operation currentOperation = operationDao.get(id);
        if (currentOperation != null) {
            if (operationDao.delete(id)) {
                return currentOperation;
            }
        }
        return null;
    }

    private int calculation(Operation data) {
        int result = 0;

        if (data.getOperation().equals("+")) {
            result = data.getOper_1() + data.getOper_2();
        }

        if (data.getOperation().equals("-")) {
            result = data.getOper_1() - data.getOper_2();
        }

        if (data.getOperation().equals("*")) {
            result = data.getOper_1() * data.getOper_2();
        }

        if (data.getOperation().equals("/")) {
            result = data.getOper_1() / data.getOper_2();
        }

        return result;
    }

    private int generateUniqueID() {
        return id.incrementAndGet();
    }
}
