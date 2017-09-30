package kz.kegoc.bln.service.auth.impl;

import javax.ejb.Stateless;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.apache.commons.codec.binary.Base64;

import kz.kegoc.bln.service.auth.AuthService;


@Stateless
public class AuthServiceImpl implements AuthService {

	@Override
	public Long auth(String userName, String password) {
		Long userId = 0L;
		if ("eugene".equals(userName))
			userId = 1L;
		
		if (userId>0) {
	    	JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
			Jedis jedis = pool.getResource();
			jedis.set(userName, Base64.encodeBase64String((userName + ":" + password).getBytes()));
			jedis.expire(userName, 300);
			pool.close();
			pool.destroy();
		}
		
		return userId;
	}
}
