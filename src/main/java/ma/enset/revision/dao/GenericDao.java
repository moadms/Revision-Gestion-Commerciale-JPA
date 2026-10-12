package ma.enset.revision.dao;

import java.util.List;

public interface GenericDao<T, ID> {
    void create(T entity);
    void update(T entity);
    void delete(T entity);
    T findById(ID id);
    List<T> findAll();
}
