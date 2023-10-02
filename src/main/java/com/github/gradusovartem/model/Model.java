package com.github.gradusovartem.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class Model - хранит данные об операциях
 */
public class Model {
    private static Model instance = new Model();

    private List<Integer> modelId;
    private Map<Integer, String> modelComment;
    private Map<Integer, String> modelDt_Operation;
    private Map<Integer, Integer> modelOper_1;
    private Map<Integer, Integer> modelOper_2;
    private Map<Integer, String> modelOperation;
    private Map<Integer, Integer> modelResult;

    /**
     * @method getInstance() - возращает объект типа Model
     * @return
     */
    public static Model getInstance() {
        return instance;
    }

    /**
     * конструктор Model
     */
    private Model() {
        modelId = new ArrayList<>();
        modelComment = new HashMap<>();
        modelDt_Operation = new HashMap<>();
        modelOper_1 = new HashMap<>();
        modelOper_2 = new HashMap<>();
        modelOperation = new HashMap<>();
        modelResult = new HashMap<>();
    }

    /**
     * method add() - метод который добавляет составляющие операции в коллекции
     * @param id - id операции
     * @param comment - комментарий к операции
     * @param oper_1 - первый операнд
     * @param oper_2 - второй операнд
     * @param dt_operation - данные об операции
     * @param operation - операция
     * @param result - результат выражения
     */
    public void add(int id, String comment, int oper_1, int oper_2, String dt_operation, String operation, int result) {
        modelId.add(id);
        modelComment.put(id, comment);
        modelDt_Operation.put(id, dt_operation);
        modelOper_1.put(id, oper_1);
        modelOper_2.put(id, oper_2);
        modelOperation.put(id, operation);
        modelResult.put(id, result);
    }
}
