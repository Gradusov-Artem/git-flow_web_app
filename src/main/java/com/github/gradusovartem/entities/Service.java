package com.github.gradusovartem.entities;

import java.util.Collection;

public interface Service {
    Operation get(int id);

    Collection<Operation> getAll();

    Operation add(Operation operation);

    Operation update(int id, String comment);

    Operation delete(int id);

}
