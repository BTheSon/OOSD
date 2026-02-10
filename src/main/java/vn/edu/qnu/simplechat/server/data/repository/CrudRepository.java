package vn.edu.qnu.simplechat.server.data.repository;

import java.util.*;

public interface CrudRepository<ID, T> {

    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);
}
