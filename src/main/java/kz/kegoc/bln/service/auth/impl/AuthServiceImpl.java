package kz.kegoc.bln.service.auth.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import kz.kegoc.bln.entity.adm.User;
import org.redisson.api.RMapCache;
import org.apache.commons.codec.binary.Base64;
import kz.kegoc.bln.service.auth.AuthService;
import java.util.concurrent.TimeUnit;

@Stateless
public class AuthServiceImpl implements AuthService {

	@Override
	public Long auth(String userName, String password) {
		Long userId = 0L;
		if ("eugene".equals(userName))
			userId = 1L;
		
		if (userId>0) {
			User user = new User();
			user.setId(1l);
			user.setName("EUGENE");
			user.setDescription("Басуков Евгений Александович");

			String hash = Base64.encodeBase64String((userName + ":" + password).getBytes());
			sessions.put(hash, user,30, TimeUnit.MINUTES);
		}
		
		return userId;
	}

	@Inject
	private RMapCache<String, User> sessions;
}
