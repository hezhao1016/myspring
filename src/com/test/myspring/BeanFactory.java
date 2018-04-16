package com.test.myspring;

import com.test.myspring.factory.BeanFactoryManager;
import com.test.myspring.factory.BeanManager;
import com.test.myspring.mapping.Bean;

/**
 * Bean�������
 * @author user
 *
 */
public class BeanFactory {
	private String config;
	private BeanFactoryManager beanFactoryManager;
	
	/**
	 * �õ�BeanFactoryʵ��
	 */
	public BeanFactory(){
		init();
	}
	
	/**
	 * �õ�BeanFactoryʵ��
	 * @param config �����ļ����ƣ������ö������Ӣ�Ķ��ŷָ�
	 */
	public BeanFactory(String config){
		this.config = config;
		init();
	}
	
	/**
	 * ��ʼ��BeanFactory
	 */
	public void init() {
		init(this.config);
	}
	
	/**
	 * ��ʼ��BeanFactory
	 * @param config�������ļ����ƣ������ö������Ӣ�Ķ��ŷָ�
	 */
	public void init(String config) {
		// ���԰�����������ļ�
		String[] configFiles = null;
		if (config == null || config.isEmpty()) {
			configFiles = new String[] { "myspring.xml" };
		} else {
			// ��������ļ������ַ���
			configFiles = config.split(",");
		}
		this.beanFactoryManager = new BeanFactoryManager(configFiles);
	}

	/**
	 * ����Id��ȡ���
	 * @param beanId
	 * @return
	 */
	public Object getBean(String beanId){
		try {
			Bean bean = beanFactoryManager.getBean(beanId);
			Object obj = new BeanManager().di(bean);
			return obj;
		} catch (Exception e) {
			System.out.println("��ȡBean���["+beanId+"]����");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �õ������ļ�����
	 * @return
	 */
	public String getConfig() {
		return config;
	}

	/**
	 * ���������ļ�����
	 * @param config
	 */
	public void setConfig(String config) {
		this.config = config;
	}

}
