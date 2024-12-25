package com.cpg.bim.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="state")
public class State 
{
 
	@Id
	@Column(name="statecode")
	private String stateCode;
	@Column(name="statename")
	private String stateName;
	//@OneToMany(mappedBy="statecode")
	//@JsonIgnore
	//private Set<Publisher> publisher;
	public State() {}
 
	public State(String stateCode, String stateName) {
		super();
		this.stateCode = stateCode;
		this.stateName = stateName;
	}
 
	
	public String getStateCode() {
		return stateCode;
	}
 
	
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
 
	public String getStateName() {
		return stateName;
	}
 
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
 