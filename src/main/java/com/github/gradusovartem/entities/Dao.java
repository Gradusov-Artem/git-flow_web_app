package com.github.gradusovartem.entities;

import java.util.Collection;
import java.util.Optional;

/**
 * Класс-шаблон для реализации CRUD-операций
 * @param <T>
 */
public interface Dao<T> {

    Operation get(int id);

    Collection<T> getAll();

    void add(Operation t);

    void update(Operation t, String params);

    void delete(Operation t);
}
