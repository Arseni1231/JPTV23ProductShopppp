package ee.ivkhkdev.productshop.interfaces;

import java.util.List;

public interface Repository<T> {
    void save(T entity);
    void saveAll(List<T> entities);
    List<T> load();
    List<T> getAll();
}
