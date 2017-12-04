package kz.kegoc.bln.service.common;

import kz.kegoc.bln.entity.common.HasId;
import java.util.List;

public interface EntityService<T extends HasId> {
	List<T> findAll();

	T findById(Long entityId);
    
	T findByCode(String entityCode);
	
	T findByName(String entityName);
	
	T create(T entity);

	T update(T entity);

    boolean delete(Long entityId);
}
