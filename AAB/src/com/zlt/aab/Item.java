package com.zlt.aab;

import org.litepal.crud.DataSupport;

public class Item extends DataSupport{
	
	private String name;
	private String entry;
	private String formula;  
	
	private String mass;
	
	private String weight;
	private String dbs;
	
	public String getName() {
		return name;
	}
	
	public String getEntry() {
		return entry;
	}
	
	public String getFormula() {
		return formula;
	}
	
	public String getMass() {
		return mass;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public String getDbs() {
		return dbs;
	}
	
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public void setMass(String mass) {
		this.mass = mass;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDbs(String dbs) {
		this.dbs = dbs;
	}
	
}
