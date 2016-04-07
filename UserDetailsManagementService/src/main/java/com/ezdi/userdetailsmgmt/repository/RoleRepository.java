package com.ezdi.userdetailsmgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ezdi.userdetailsmgmt.persistence.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	
	Role findByUsername(@Param("name") String username ); 
	
}
