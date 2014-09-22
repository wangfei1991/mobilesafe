package com.wyu.mobilesafe.domain;

import java.io.Serializable;

public class BlackNumberInfo implements Serializable{
	private String name;
	private String number;
	private String mode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String toString()
	{
		return "["+name+","+number+","+mode+"]";
	}
}
