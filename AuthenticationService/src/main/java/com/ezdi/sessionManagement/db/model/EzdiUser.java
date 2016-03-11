package com.ezdi.sessionManagement.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class EzdiUser {
	
	@Column
	@Id
	private String username;
	
	@Column
	private String password;
	
	@Column
	private boolean enabled;
	
	@Column
	private int loginAttempts;
	
	@Column
	private boolean locked;

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

}
