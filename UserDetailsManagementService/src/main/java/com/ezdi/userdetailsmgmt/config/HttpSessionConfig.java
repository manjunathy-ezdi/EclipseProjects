package com.ezdi.userdetailsmgmt.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import com.ezdi.sessionMgmt.logger.LoggerUtil;

@Configuration
@EnableRedisHttpSession
@PropertySource("application.properties")
public class HttpSessionConfig {
	
	private static final Logger LOGGER = Logger.getLogger(HttpSessionConfig.class);
	
	@Value("${redis.hostname}")
	private String redisHostName;

	@Value("${redis.port}")
	private int redisPort;
	
	@Value("${redis.session.timeout}")
	private int sessionTimeOutInMinutes;

	@Bean
	public JedisConnectionFactory connectionFactory() {
		LOGGER.info(LoggerUtil.getMessage("Initializing JedisConnectionFactory \n #$#HttpSessionConfig:connectionFactory()"
				+ redisHostName + ":" + redisPort));
		JedisConnectionFactory jedisConnectionFectory = new JedisConnectionFactory();
		jedisConnectionFectory.setHostName(redisHostName);
		jedisConnectionFectory.setPort(redisPort);
		LOGGER.info(LoggerUtil.getMessage("Exiting JedisConnectionFactory connectionFactory()"));
		return jedisConnectionFectory;
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		LOGGER.info(LoggerUtil.getMessage("#$#HttpSessionConfig:httpSessionStrategy"));
		return new HeaderHttpSessionStrategy();
	}
	
	@Primary
	@Bean 
	public RedisOperationsSessionRepository redisOperationsSessionRepository(){
		RedisOperationsSessionRepository redisOperationsSessionRepository =  new RedisOperationsSessionRepository(connectionFactory());
		redisOperationsSessionRepository.setDefaultMaxInactiveInterval(sessionTimeOutInMinutes*60);
		return redisOperationsSessionRepository;
	}

	public int getSessionTimeOutInMinutes() {
		return sessionTimeOutInMinutes;
	}
	
}