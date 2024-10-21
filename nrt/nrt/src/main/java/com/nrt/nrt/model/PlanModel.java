package com.nrt.nrt.model;

import java.math.BigDecimal;

public class PlanModel {
    private Long id;
    private String name;
    private int validityInMonths;
    private int numberOfBids;
    private BigDecimal price;
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValidityInMonths() {
		return validityInMonths;
	}
	public void setValidityInMonths(int validityInMonths) {
		this.validityInMonths = validityInMonths;
	}
	public int getNumberOfBids() {
		return numberOfBids;
	}
	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

    // Getters and Setters

 
}
