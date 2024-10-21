package com.nrt.nrt.model;

import java.time.LocalDate;

public class SubscriptionModel {
    private Long id;
    private Long planId;
    private Long registerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int remainingBids;
    
    private String planName;
    private int numberOfBids;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Long getRegisterId() {
		return registerId;
	}
	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public int getRemainingBids() {
		return remainingBids;
	}
	public void setRemainingBids(int remainingBids) {
		this.remainingBids = remainingBids;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public int getNumberOfBids() {
		return numberOfBids;
	}
	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	} 

    
    
    
}
