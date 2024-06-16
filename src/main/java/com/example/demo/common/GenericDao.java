package com.example.demo.common;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T,ID> {
    public Optional<T> findById(ID id);
    public List<T> findAllPaginated(int pageNumber, int pageSize);
    public List<T> findBy(String fieldName, Object value);
    public void create(T entity);
    public void update(T entity, ID id);
    public void delete(ID id);
}
