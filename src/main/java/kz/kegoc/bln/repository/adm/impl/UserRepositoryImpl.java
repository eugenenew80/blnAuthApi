package kz.kegoc.bln.repository.adm.impl;

import kz.kegoc.bln.entity.adm.User;
import kz.kegoc.bln.repository.adm.UserRepository;
import kz.kegoc.bln.repository.common.AbstractRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;


@Stateless
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
	public UserRepositoryImpl() { setClazz(User.class); }

	public UserRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
