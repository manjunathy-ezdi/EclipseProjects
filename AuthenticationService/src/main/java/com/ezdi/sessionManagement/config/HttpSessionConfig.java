package com.ezdi.sessionManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

// tag::class[]
@Configuration
@EnableRedisHttpSession // <1>
public class HttpSessionConfig {

	@Bean
	public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory jedisConnectionFectory = new JedisConnectionFactory();
//		jedisConnectionFectory.setHostName("localhost");
//		jedisConnectionFectory.setPort(6380);
		return jedisConnectionFectory;  // <2>
	}
	
	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy(); // <3>
	}
}
// end::class[]