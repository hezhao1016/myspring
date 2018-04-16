package com.test.myspring.mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Bean×é¼þÓ³ÉäÀà
 * @author user
 *
 */
public class Bean {
	private String id;
	private String className;
	private String scope;
	private Set<ValueProperty> valueProperties = new HashSet<ValueProperty>();
	private Set<RefProperty> refProperties = new HashSet<RefProperty>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public Set<ValueProperty> getValueProperties() {
		return valueProperties;
	}
	public void addValueProperties(ValueProperty valueProperty) {
		this.valueProperties.add(valueProperty);
	}
	public Set<RefProperty> getRefProperties() {
		return refProperties;
	}
	public void addRefProperties(RefProperty refProperty) {
		this.refProperties.add(refProperty);
	}
	
}
