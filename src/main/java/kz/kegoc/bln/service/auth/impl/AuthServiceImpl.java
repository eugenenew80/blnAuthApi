package kz.kegoc.bln.service.auth.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import kz.kegoc.bln.entity.adm.User;
import kz.kegoc.bln.service.adm.UserService;
import kz.kegoc.bln.service.auth.ActiveDirectory;
import kz.kegoc.bln.service.auth.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.redisson.api.RMapCache;
import java.util.concurrent.TimeUnit;

@Stateless
public class AuthServiceImpl implements AuthService {

	@Override
	public Long auth(String userName, String password) {
		if (password!=null) {
			password = password.trim();
			if (password.length()==0)
				password = null;
		}
		if (userName!=null) {
			userName = userName.trim().toLowerCase();
			if (userName.length()==0)
				userName = null;
		}

		try {
			ActiveDirectory.getConnection(userName, password);
		}
		catch (Exception e) {
			return 0l;
		}

		User user =userService.findByName(userName.toUpperCase());
		if (user==null)
			return 0l;

		String hash = Base64.encodeBase64String((userName + ":" + password).getBytes());
		sessions.put(hash, user,30, TimeUnit.MINUTES);

		return user.getId();
	}

	@Inject
	private RMapCache<String, User> sessions;

	@Inject
	private UserService userService;
}
