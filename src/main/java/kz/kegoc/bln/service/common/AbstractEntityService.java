package kz.kegoc.bln.service.common;

import kz.kegoc.bln.entity.common.HasId;
import kz.kegoc.bln.exception.EntityNotFoundException;
import kz.kegoc.bln.exception.InvalidArgumentException;
import kz.kegoc.bln.exception.RepositoryNotFoundException;
import kz.kegoc.bln.repository.common.Repository;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

public abstract class AbstractEntityService<T extends HasId> implements EntityService<T> {
    public AbstractEntityService() {}

    public AbstractEntityService(Repository<T> repository) {
        this.repository = repository;
    }
    
    public AbstractEntityService(Repository<T> repository, Validator validator) {
        this(repository);
        this.validator = validator;
    }
    
	public List<T> findAll() {
		if (repository==null)
			throw new RepositoryNotFoundException();
		
		return repository.selectAll();
	}


	public T findById(Long entityId) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entityId==null)
			throw new InvalidArgumentException();
		
		T entity = repository.selectById(entityId);
		if (entity==null)
			throw new EntityNotFoundException(entityId);
		
		return entity;
	}
	

	public T findByCode(String entityCode) {
		if (repository==null)
			throw new RepositoryNotFoundException();
		
		if (entityCode==null)
			throw new InvalidArgumentException();
		
		T entity = repository.selectByCode(entityCode);
		if (entity==null)
			throw new EntityNotFoundException(entityCode);

		return entity;
	}
	
	
	public T findByName(String entityName) {
		if (repository==null)
			throw new RepositoryNotFoundException();
		
		if (entityName==null)
			throw new InvalidArgumentException();
		
		T entity = repository.selectByName(entityName);
		if (entity==null)
			throw new EntityNotFoundException(entityName);

		return entity;
	}
	
	
	public T create(T entity) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entity==null) 
			throw new InvalidArgumentException();
		
		if (entity.getId()!=null)
			throw new InvalidArgumentException(entity);

		Set<ConstraintViolation<T>> violations =  validator.validate(entity);
		if (violations.size()>0) {			
			ConstraintViolation<T> violation = violations.iterator().next();		
			throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
		}

		return repository.insert(entity);
	}

	
	public T update(T entity) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entity==null) 
			throw new InvalidArgumentException();
		
		if (entity.getId()==null) 
			throw new InvalidArgumentException(entity);

		Set<ConstraintViolation<T>> violations =  validator.validate(entity);
		if (violations.size()>0) {			
			ConstraintViolation<T> violation = violations.iterator().next();
			throw new ValidationException(violation.getPropertyPath() + ": " + violation.getMessage());
		}

		return repository.update(entity);
	}

	
	public boolean delete(Long entityId) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entityId==null) 
			throw new InvalidArgumentException();
		
		findById(entityId);
		
		return repository.delete(entityId); 
	}	

	private Repository<T> repository;
	private Validator validator;
}
