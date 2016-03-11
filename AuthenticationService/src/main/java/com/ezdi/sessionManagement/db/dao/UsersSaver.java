package com.ezdi.sessionManagement.db.dao;

import com.ezdi.sessionManagement.db.model.EzdiUser;

public interface UsersSaver {
	
	public void save(EzdiUser user);
	public EzdiUser getByUsername(String username);

}
