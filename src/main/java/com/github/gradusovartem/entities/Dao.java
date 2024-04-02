package com.github.gradusovartem.entities;

import java.util.Collection;

/**
 * Класс-шаблон для реализации CRUD-операций
 * @param <T>
 */
public interface Dao<T> {

    Operation get(int id);

    Collection<T> getAll();

    boolean add(Operation t);

    boolean update(int id, String comment);

    boolean delete(int id);
}
