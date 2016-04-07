package com.ezdi.userdetailsmgmt.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	@Column
	private String name;
	
	@Column
	private int active;
	
	@Column(name="add_permission")
	private boolean addPermission;
	
	@Column(name="edit_permission")
	private boolean editPermission;
	
	@Column(name="delete_permission")
	private boolean deletePermission;
	
	@Column(name="read_permission")
	private boolean readPermission;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public boolean isAddPermission() {
		return addPermission;
	}

	public void setAddPermission(boolean addPermission) {
		this.addPermission = addPermission;
	}

	public boolean isEditPermission() {
		return editPermission;
	}

	public void setEditPermission(boolean editPermission) {
		this.editPermission = editPermission;
	}

	public boolean isDeletePermission() {
		return deletePermission;
	}

	public void setDeletePermission(boolean deletePermission) {
		this.deletePermission = deletePermission;
	}

	public boolean isReadPermission() {
		return readPermission;
	}

	public void setReadPermission(boolean readPermission) {
		this.readPermission = readPermission;
	}
}
