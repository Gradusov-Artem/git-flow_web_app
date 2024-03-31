package com.github.gradusovartem.entities;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public interface IService {
    public Operation get(int id);

    public Collection<Operation> getAll();

    public Operation add(Operation operation);

    public Operation update(int id, String comment);

    public Operation delete(int id);

}
