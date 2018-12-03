package com.example.finance.web;

/**
 * 
 * 計算種別保持用のデータクラスです。
 *  
 * @author Akira Abe 
 *
 */
public class TypeBean {
	
	private String id;
	private String value;
	
	public TypeBean(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
