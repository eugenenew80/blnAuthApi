package kz.kegoc.bln.repository.common;

import kz.kegoc.bln.entity.common.HasId;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


public abstract class AbstractRepository<T extends HasId> implements Repository<T> {

	public List<T> selectAll() {
		TypedQuery<T> query = em.createNamedQuery(clazz.getSimpleName() +  ".findAll", clazz);
		return query.getResultList();
	}

	public T selectById(Long entityId) {
		return em.find(clazz, entityId);
	}

	
	public T selectByName(String entityName) {
		TypedQuery<T> query = em.createNamedQuery(clazz.getSimpleName() + ".findByName", clazz);
		query.setParameter("name", entityName);
		
		return query.getResultList().stream()
			.findFirst()
			.orElse(null);
	}
	
	
	public T selectByCode(String entityCode) {
		TypedQuery<T> query = em.createNamedQuery(clazz.getSimpleName() + ".findByCode", clazz);
		query.setParameter("code", entityCode);
		
		return query.getResultList().stream()
			.findFirst()
			.orElse(null);
	}
	
	
	public T insert(T entity) {
		em.persist(entity);
		return entity;
	}

	
	public T update(T entity) {
		em.merge(entity);		
		return entity;
	}
	

	public boolean delete(Long entityId) {
		T entity = selectById(entityId);
		if (entity!=null) {
			em.remove(entity);
			return true;
		}
		return false;
	}
	
	
	
	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}	
		
	protected void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}	

	
	@PersistenceContext(unitName = "bln")
	private EntityManager em;
	private Class<T> clazz;
}
