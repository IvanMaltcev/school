package ru.hogwarts.school.service;

import java.util.Collection;

public interface CrudeService<T, G> {

    T create(T t);

    T getById(Long id);

    T update(T t);

    T delete(Long id);

    Collection<T> getFilter(G param);

}
