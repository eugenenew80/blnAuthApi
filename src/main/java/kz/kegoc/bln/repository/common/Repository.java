package kz.kegoc.bln.repository.common;

import kz.kegoc.bln.entity.common.HasId;
import java.util.List;

public interface Repository <T extends HasId> {
    List<T> selectAll();

    T selectById(Long entityId);
    
    T selectByCode(String entitycode);
    
    T selectByName(String entityName);
    
    T insert(T entity);

    T update(T entity);

    boolean delete(Long entityId);
}
