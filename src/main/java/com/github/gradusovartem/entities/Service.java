package com.github.gradusovartem.entities;

import com.github.gradusovartem.model.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class Service implements IService {
    private static final AtomicInteger id = new AtomicInteger(0);
    Dao<Operation> operationDao = new OperationDao();

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
        return currentOperation;
    }

    @Override
    public Operation update(int id, String comment) {
        Operation currentOperation = operationDao.get(id);
        if (currentOperation != null) {
            operationDao.update(currentOperation, comment);
            return currentOperation; // проверить обновляется ли комментарий или заменить на operationDao.get(id)
        }
        return null;
    }

    @Override
    public Operation delete(int id) {
        Operation currentOperation = operationDao.get(id);
        if (currentOperation != null) {
            operationDao.delete(currentOperation);
            return currentOperation;
        }
        return null;
    }

    public static int calculation(Operation data) {
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

    public static int generateUniqueID() {
        return id.incrementAndGet();
    }
}
