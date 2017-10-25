package com.sophos.semih.model;

import java.util.ArrayList;

public class Row {

	
	private ArrayList<Item> rowItems;
	
	
	public Row() {
		super();
	}

	public Row(ArrayList<Item> rowItems) {
		super();
		this.rowItems = rowItems;
	}

	public ArrayList<Item> getRowItems() {
		return rowItems;
	}

	public void setRowItems(ArrayList<Item> rowItems) {
		this.rowItems = rowItems;
	}
	
	
}
