package kz.kegoc.bln.ejb.cdi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import kz.kegoc.bln.entity.common.Lang;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;

@Singleton
@Startup
public class Producer {
    private RedissonClient redissonClient = null;

    @PostConstruct
    public void Producer() {
        ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.setCodec(new JsonJacksonCodec(mapper));

        redissonClient = Redisson.create(config);
    }

    @Produces
    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    @Produces
    public Lang defLang() {
        return Lang.RU;
    }
}
