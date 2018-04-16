package com.test.myspring.mapping;

/**
 * Value Ù–‘”≥…‰¿‡
 * @author user
 *
 */
public class ValueProperty{
	private String name;
	private Class<?> type;
	private Object propertyValue;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getType() {
		return type;
	}
	
	public void setType(String typeName) throws ClassNotFoundException {
		this.type = Class.forName(typeName);
	}
	
	public void setType(Class<?> type) {
		this.type = type;
	}
	
	public Object getPropertyValue() {
		return propertyValue;
	}
	
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
}
