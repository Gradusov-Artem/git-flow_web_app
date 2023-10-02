package com.github.gradusovartem.entities;

/**
 * class Operation - содержит составляющие операции
 */
public class Operation {
    public int id;
    public String comment;
    public String dt_operation;
    public double oper_1;
    public double oper_2;
    public String operation;
    public double result;

    public Operation(){

    }

    /**
     * конструктор Operation
     * @param id - id операции
     * @param comment - комментарий к операции
     * @param oper_1 - первый операнд
     * @param oper_2 - второй операнд
     * @param operation - операция
     * @param result - результат выражения
     */
    public Operation(int id, String comment, int oper_1, int oper_2, String operation, int result){
        this.id = id;
        this.comment = comment;
        this.dt_operation = createData(id, oper_1, oper_2, operation);
        this.oper_1 = oper_1;
        this.oper_2 = oper_2;
        this.operation = operation;
        this.result = result;
    }

    private String createData(int id, int oper_1, int oper_2, String operation) {
        String data = Integer.toString(oper_1) + operation.toString() + Integer.toString(oper_2);
        return data;
    }
}
