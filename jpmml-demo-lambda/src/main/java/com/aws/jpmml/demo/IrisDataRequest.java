package com.aws.jpmml.demo;

/*
 * Copyright 2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: MIT-0
 */

public class IrisDataRequest {
	
	String sepalLength;
	String sepalWidth;
	String petalLength;
	String petalWidth;
	
	
	public String getSepalLength() {
		return sepalLength;
	}
	public void setSepalLength(String sepalLength) {
		this.sepalLength = sepalLength;
	}
	public String getSepalWidth() {
		return sepalWidth;
	}
	public void setSepalWidth(String sepalWidth) {
		this.sepalWidth = sepalWidth;
	}
	public String getPetalLength() {
		return petalLength;
	}
	public void setPetalLength(String petalLength) {
		this.petalLength = petalLength;
	}
	public String getPetalWidth() {
		return petalWidth;
	}
	public void setPetalWidth(String petalWidth) {
		this.petalWidth = petalWidth;
	}
	
	@Override
	public String toString() {
		return "IrisDataRequest [sepalLength=" + sepalLength + ", sepalWidth=" + sepalWidth + ", petalLength="
				+ petalLength + ", petalWidth=" + petalWidth + "]";
	}
	
	

}
