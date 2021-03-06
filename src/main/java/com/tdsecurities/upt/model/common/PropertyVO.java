package com.tdsecurities.upt.model.common;

import java.io.Serializable;

public class PropertyVO implements Serializable{
	private static final long serialVersionUID = -6243465717914388101L;
	private String scope;
	private String name;
	private String value;
	private String dbValue;
		
	public String getDbValue() {
		return dbValue;
	}
	public void setDbValue(String dbValue) {
		this.dbValue = dbValue;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isDbValueEqualCache() {
		if ( value != null  && dbValue != null ) {
			return value.equals(dbValue);
		} else if ( value == null && dbValue == null ) {
			return true;
		} else {
			return false;
		}
	}
}
