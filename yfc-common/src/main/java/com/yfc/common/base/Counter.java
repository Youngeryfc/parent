package com.yfc.common.base;

public class Counter {

	private String cntType;
	private String cntKey="";
	private int cntValue;
	
	private int firstValue = 1;
	
	private int step=1;
	
	public Counter(){
		
	}
	
	public Counter(String cntType, String cntKey){
		this.cntType = cntType;
		this.cntKey = cntKey;
	}

	public String getCntKey() {
		return cntKey;
	}

	public void setCntKey(String cntKey) {
		this.cntKey = cntKey;
	}

	public int getCntValue() {
		return cntValue;
	}

	public void setCntValue(int cntValue) {
		this.cntValue = cntValue;
	}

	public int getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(int firstValue) {
		this.firstValue = firstValue;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getCntType() {
		return cntType;
	}

	public void setCntType(String cntType) {
		this.cntType = cntType;
	}
	
	
}
