package com.github.gradusovartem.entities;

/**
 * class Operation - содержит составляющие операции
 */
public class Operation {
    public int id;
    public String comment;
    public String dt_operation;
    public int oper_1;
    public int oper_2;
    public String operation;
    public double result;

    public Operation(){

    }

    /**
     * constructor Operation
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

    /**
     * method createData создает data об операции
     * @param id
     * @param oper_1
     * @param oper_2
     * @param operation
     * @return
     */
    private String createData(int id, int oper_1, int oper_2, String operation) {
        String data = Integer.toString(oper_1) + operation.toString() + Integer.toString(oper_2);
        return data;
    }

    /**
     * method getId возвращает id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * method getCommand возвращает comment
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * method getOper_1 возвращает oper_1
     * @return
     */
    public int getOper_1() {
        return oper_1;
    }

    /**
     * method getOper_2 возвращает oper_2
     * @return
     */
    public int getOper_2() {
        return oper_2;
    }

    /**
     * method getOperation возвращает operation
     * @return
     */
    public String getOperation() {
        return operation;
    }

    /**
     * method setId записывает id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * method setComment записывает comment
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * method setOper_1 записывает oper_1
     * @param oper_1
     */
    public void setOper_1(int oper_1) {
        this.oper_1 = oper_1;
    }

    /**
     * method setOper_2 записывает oper_2
     * @param oper_2
     */
    public void setOper_2(int oper_2) {
        this.oper_2 = oper_2;
    }

    /**
     * method setOperation записывает operation
     * @param operation
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }
}
