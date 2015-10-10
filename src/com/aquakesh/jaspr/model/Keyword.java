package com.aquakesh.jaspr.model;

public class Keyword {
	
	public Keyword(String keyword){
		this.keyword = keyword;
	}
	public boolean selected;
	public String keyword;
	
	@Override
	public String toString() {
		return keyword;
	}
}
