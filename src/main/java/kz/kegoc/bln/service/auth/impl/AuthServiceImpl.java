package kz.kegoc.bln.service.auth.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import kz.kegoc.bln.entity.adm.User;
import kz.kegoc.bln.service.adm.UserService;
import kz.kegoc.bln.service.auth.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

@Stateless
public class AuthServiceImpl implements AuthService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Long auth(String userName, String password) {
		if (userName!=null) {
			userName = userName.trim();
			if (userName.length()==0)
				userName = null;
		}

		if (password!=null) {
			password = password.trim();
			if (password.length()==0)
				password = null;
		}

		/*
		try {
			ActiveDirectory.getConnection("eugene", "Qw!31122017");
		}
		catch (NamingException e) {
			e.printStackTrace();
			return 0l;
		}
		*/

		User user = userService.findByName(userName.toUpperCase());
		if (user==null)
			return 0l;

		String hash = Base64.encodeBase64String((userName + ":" + password).getBytes());
		redissonClient.getBucket(hash).set(user, 30, TimeUnit.MINUTES);

		logger.info(userName + ": session created");

		return user.getId();
	}


	@Inject
	private RedissonClient redissonClient;

	@Inject
	private UserService userService;
}
