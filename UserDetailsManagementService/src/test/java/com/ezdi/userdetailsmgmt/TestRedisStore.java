package com.ezdi.userdetailsmgmt;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import redis.clients.jedis.JedisShardInfo;

/**
 * Created by EZDI\manjunath.y on 4/3/16.
 */
public class TestRedisStore {
	
	//static String id="5691e70e-5fda-42f0-873f-f17ddcc5d1f4";
	//static String idFull="spring:session:sessions:5691e70e-5fda-42f0-873f-f17ddcc5d1f4";
	//64cf35b2-baf8-459f-ae40-4815b1cc9e1f
	
	static String id="b27c94e2-0f10-4b7d-aa90-e05d23d282aa";
	//static String idFull="spring:session:sessions:10aa204d-1d68-4ea0-af8a-3ebe6e8fb87d";
	
	static String username="trump";
	static String prefix="PRINCIPAL_TO_SESSION_MAP_";
	
	public static void displayByte(byte[] b){
		if(b != null){
			for(int i=0; i<b.length; i++){
				System.out.println("Val "+i+" : "+b[i]);
			}
		}
	}
	
	public static void test2() throws Exception{
		byte[] key = (prefix+username).getBytes();
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setShardInfo(new JedisShardInfo("localhost", 6379));
		JedisConnection jedisConnection = jedisConnectionFactory.getConnection();
		byte[] val = jedisConnection.get(key);
		if(val == null){
			System.out.println("Custome Value is null");
			return;
		}
		else{
			displayByte(val);
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(val);
		ObjectInput oInp = new ObjectInputStream(bis);
		Object o = oInp.readObject();
		if(o == null) {
			System.out.println("Custome object is null");
			return;
		}
		System.out.println(o.toString());
	}
	
	public static void main(String[] args){
		try{
			test();
			test2();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void displayMapSessionAttributes(MapSession mapSession){
		if(mapSession == null){
			System.out.println("displayMapSessionAttributes :: MapSession is null");
			return;
		}
		Set<String> atts = mapSession.getAttributeNames();
		for(String each: atts){
			System.out.println("displayMapSessionAttributes KEY: VALUE's CLASS ::"+each+" : "+mapSession.getAttribute(each).getClass().getName());
		}
	}
	
	public static void displayMapSession(MapSession mapSession){
		if(mapSession == null){
			System.out.println("displayMapSession:: MapSession is null");
			return;
		}
		System.out.println("displayMapSession:: MapSession Class Namae: "+mapSession.getClass().getName());
		System.out.println("displayMapSession:: ID: "+mapSession.getId());
		System.out.println("displayMapSession:: Last Accessed time: "+mapSession.getLastAccessedTime());
		System.out.println("displayMapSession:: Creation time: "+mapSession.getCreationTime());
		System.out.println("displayMapSession:: Max Inactive Interval: "+mapSession.getMaxInactiveIntervalInSeconds());
		displayMapSessionAttributes(mapSession);
	}
	
	public static Map<Object,Object> getSessionBoundHashOperationsEntries(){
		String key=id;
		RedisOperations<Object, Object> redisOperations = new RedisTemplate<Object,Object>();
		return null;
	}
	
	public static void test(){
		//Map<Object, Object> entries = getSessionBoundHashOperations(id).entries();
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setShardInfo(new JedisShardInfo("localhost", 6379));
		RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(jedisConnectionFactory);
		Session session = sessionRepository.getSession(id);
		if(session == null) return;
		System.out.println("SESSION CLASS: "+session.getClass().getName());
		Set<String> attrs = session.getAttributeNames();
		for(String each: attrs){
			System.out.print(each+"= ");
			if(session.getAttribute(each)!=null){
				System.out.print(session.getAttribute(each).getClass().getName()+" ||| "+session.getAttribute(each).toString());
			}
			else{
				System.out.print("NULL");
			}
			System.out.println();
		}
		
		/*
		 * OUTPUT::
		 * 16:19:45.898 [main] DEBUG o.s.d.r.core.RedisConnectionUtils - Opening RedisConnection
		 * 16:19:45.949 [main] DEBUG o.s.d.r.core.RedisConnectionUtils - Closing Redis Connection
		 * SESSION CLASS: org.springframework.session.data.redis.RedisOperationsSessionRepository$RedisSession
		 * SPRING_SECURITY_CONTEXT= org.springframework.security.core.context.SecurityContextImpl ||| org.springframework.security.core.context.SecurityContextImpl@fb707c63: Authentication: org.springframework.security.authentication.UsernamePasswordAuthenticationToken@fb707c63: Principal: org.springframework.security.core.userdetails.User@31dd0b: Username: john; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_ADMIN,ROLE_USER; Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@957e: RemoteIpAddress: 127.0.0.1; SessionId: null; Granted Authorities: ROLE_ADMIN, ROLE_USER
		 */
		
		
		/*
		Map<Object, Object> entries = getSessionBoundHashOperationsEntries();
		if(entries.isEmpty()) {
			System.out.println("Map named 'entries' is null");
			return;
		}
		MapSession loaded = loadSession(id, entries);
		if(loaded == null){
			System.out.println("MapSession is null");
			return;
		}
		if(loaded.isExpired()) {
			System.out.println("MapSession is expired");
			return;
		}
		displayMapSession(loaded);
		*/
		
	}
}
