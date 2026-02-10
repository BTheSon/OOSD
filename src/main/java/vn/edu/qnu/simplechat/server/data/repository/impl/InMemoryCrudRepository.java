package vn.edu.qnu.simplechat.server.data.repository.impl;

import vn.edu.qnu.simplechat.server.data.repository.CrudRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InMemoryCrudRepository <ID, T> implements CrudRepository<ID, T> {
    protected final Map<ID, T> store = new ConcurrentHashMap<>();

    protected abstract ID getId(T entity);

    @Override
    public T save(T entity) {
        store.put(getId(entity), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void deleteById(ID id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return store.containsKey(id);
    }
}
