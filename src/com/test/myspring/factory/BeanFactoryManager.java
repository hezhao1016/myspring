package com.test.myspring.factory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.test.myspring.exception.MySpringException;
import com.test.myspring.mapping.Bean;
import com.test.myspring.mapping.RefProperty;
import com.test.myspring.mapping.ValueProperty;

/**
 * Bean���������࣬�����ȡ�����ļ�������װ��Bean������
 * 
 * @author user
 * 
 */
public class BeanFactoryManager {

	/**
	 * beans ����
	 */
	private static Map<String, Bean> beans = new HashMap<String, Bean>();

	/**
	 * ��ȡ�����ļ�����ɳ�ʼ�� beans ����
	 * @param configureFileName
	 * @throws MySpringException
	 */
	public void init(String configureFileName) throws Exception {
		try {
			if (configureFileName == null || configureFileName.isEmpty()) {
				throw new MySpringException("myspring�����ļ�δ�ҵ���");
			}
			InputStream is = this.getClass().getResourceAsStream(
					"/" + configureFileName);
			Document doc = new SAXReader().read(is);
			Element root = doc.getRootElement();
			Iterator<Element> beansIt = root.elements("beans").iterator();
			Element beansElement = beansIt.next();
			for (Iterator<Element> beanIt = beansElement.elementIterator("bean"); beanIt
					.hasNext();) {
				Element beanElement = beanIt.next();
				Bean bean = new Bean();
				String id = beanElement.attributeValue("id");
				if(id==null || id.isEmpty()){
					Random random = new Random();
					id = String.valueOf(random.nextInt(100000000));
				}
				bean.setId(id);
				bean.setClassName(beanElement.attributeValue("class"));
				String scope = beanElement.attributeValue("scope");
				if(scope==null || scope.isEmpty()){
					scope = "singleton";
				}
				if(!scope.equals("singleton") && !scope.equals("prototype")){
					throw new MySpringException("Bean ["+id+"] �� Scope ���� ["+scope+"] ���ô���");
				}
				bean.setScope(scope);
				for (Iterator<Element> resultIt = beanElement
						.elementIterator("property"); resultIt.hasNext();) {
					Element propertyElement = resultIt.next();
					String name = propertyElement.attributeValue("name");
					String value = propertyElement.attributeValue("value");
					String ref = propertyElement.attributeValue("ref");
					if(value!=null){
						ValueProperty property = new ValueProperty();
						property.setName(name);
						property.setPropertyValue(value);
						String type = propertyElement.attributeValue("type");
						//Ĭ��String����
						if(type==null || type.isEmpty()){
							property.setType(getPropertyType(property));
						}else{
							try {
								property.setType(type);
							} catch (Exception e) {
								throw new ClassNotFoundException("Bean ["+bean.getId()+"] >> Property ["+name+"] ��Type���� ["+type+"] �����Ҳ������ࡣ");
							}
						}
						bean.addValueProperties(property);
					}else{
						RefProperty property = new RefProperty();
						property.setName(name);
						property.setPropertyValue(ref);
						bean.addRefProperties(property);
					}
				}
				//id�Ѵ���
				if(beans.containsKey(bean.getId())){
					throw new MySpringException("Bean ["+bean.getId()+"] ���ڶ��");
				}
				beans.put(bean.getId(), bean);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MySpringException("myspring�����ļ�δ�ҵ�:[" + configureFileName
					+ "],����ԭ��:δ����myspring�����ļ����ļ����������ô���");
		}
	}
	
	/**
	 * ��ȡ���Ե���������
	 * @param value
	 * @return
	 */
	private Class<?> getPropertyType(ValueProperty valueProperty){
		String value = (String) valueProperty.getPropertyValue();
		try {
			int parseInt = Integer.parseInt(value);
			valueProperty.setPropertyValue(parseInt);
			return int.class;
		} catch (NumberFormatException e1) {
		}
		try {
			double parseDouble = Double.parseDouble(value);
			valueProperty.setPropertyValue(parseDouble);
			return double.class;
		} catch (NumberFormatException e) {
		}
		try {
			float parseFloat = Float.parseFloat(value);
			valueProperty.setPropertyValue(parseFloat);
			return float.class;
		} catch (NumberFormatException e1) {
		}
		try {
			byte parseByte = Byte.parseByte(value);
			valueProperty.setPropertyValue(parseByte);
			return byte.class;
		} catch (NumberFormatException e1) {
		}
		try {
			short parseShort = Short.parseShort(value);
			valueProperty.setPropertyValue(parseShort);
			return short.class;
		} catch (NumberFormatException e1) {
		}
		try {
			long parseLong = Long.parseLong(value);
			valueProperty.setPropertyValue(parseLong);
			return long.class;
		} catch (NumberFormatException e1) {
		}
		if(value.equals("true") || value.equals("false")){
			valueProperty.setPropertyValue(Boolean.parseBoolean(value));
			return boolean.class;
		}
		return java.lang.String.class;
	}
	
	public BeanFactoryManager() {

	}

	public BeanFactoryManager(String... configureFileNames) {
		for (String configureFileName : configureFileNames) {
			try {
				init(configureFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����Bean��Id��ȡBean����
	 * @param beanId
	 * @return
	 * @throws MySpringException
	 */
	public Bean getBean(String beanId) throws MySpringException {
		if (beanId == null || beanId.isEmpty()) {
			return null;
		}
		Bean mapping = this.beans.get(beanId);
		if (mapping == null) {
			throw new MySpringException("δ�ҵ�IdΪ:[" + beanId
					+ "]��Bean������ԭ��:Bean���ô�����������ļ���һ�¡�");
		}
		return mapping;
	}
}
