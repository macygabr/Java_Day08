package school21.spring.service.repositories;

import java.util.List;

public interface CrudRepository<T> {
    public <T> T findById(long id);
    public <T> List<T> findAll();
    public <T> void save(T entity);
    public <T> void update(T entity);
    public void delete(Long id);
}