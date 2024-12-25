package com.cpg.bim.entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="permrole")
public class PermRole {
	@Id
	@Column(name="rolenumber")
	private int rolenumber;
	@Column(name="permrole",length=30)
	private String PermRole;
	
	@OneToMany(mappedBy="permrole")
	@JsonIgnore
	private Set<Users> user;
	
	public PermRole() {}
 
	public PermRole(int rolenumber, String permrole) {
		this.rolenumber = rolenumber;
		this.PermRole = permrole;
	}
 
	public int getRolenumber() {
		return rolenumber;
	}
 
	public void setRolenumber(int rolenumber) {
		this.rolenumber = rolenumber;
	}
 
	public String getPermrole() {
		return PermRole;
	}
 
	public void setPermrole(String permrole) {
		this.PermRole = permrole;
	}

	public Set<Users> getUser() {
		return user;
	}

	public void setUser(Set<Users> user) {
		this.user = user;
	}

 
}