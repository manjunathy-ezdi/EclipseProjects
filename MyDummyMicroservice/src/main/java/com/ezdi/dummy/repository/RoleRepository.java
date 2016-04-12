package com.ezdi.dummy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezdi.dummy.persistence.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	
	Role findByName(@Param("name") String name ); 
	
}
