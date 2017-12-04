package kz.kegoc.bln.service.adm.impl;

import kz.kegoc.bln.entity.adm.User;
import kz.kegoc.bln.repository.common.Repository;
import kz.kegoc.bln.service.adm.UserService;
import kz.kegoc.bln.service.common.AbstractEntityService;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Validator;

@Stateless
public class UserServiceImpl extends AbstractEntityService<User> implements UserService {
	@Inject
    public UserServiceImpl(Repository<User> repository, Validator validator) {
        super(repository, validator);
    }
}
