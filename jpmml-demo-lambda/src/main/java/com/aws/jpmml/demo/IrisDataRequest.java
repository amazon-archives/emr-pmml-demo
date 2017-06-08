package com.aws.jpmml.demo;

/*
 * Copyright 2012-2017 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
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
