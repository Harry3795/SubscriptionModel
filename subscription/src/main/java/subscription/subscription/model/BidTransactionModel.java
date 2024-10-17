package subscription.subscription.model;

import java.time.LocalDate;
public class BidTransactionModel {
	
	private Long id;
    private Long customerId;
    private LocalDate bidDate;
    private int projectId;
    private int bidCount;
    
    
    //Getters and setters
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public LocalDate getBidDate() {
		return bidDate;
	}
	public void setBidDate(LocalDate bidDate) {
		this.bidDate = bidDate;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getBidCount() {
		return bidCount;
	}
	public void setBidCount(int bidCount) {
		this.bidCount = bidCount;
	}
	
	
	
	@Override
	public String toString() {
		return "BidTransactionModel [id=" + id + ", customerId=" + customerId + ", bidDate=" + bidDate + ", projectId="
				+ projectId + ", bidCount=" + bidCount + "]";
	}
    
  
	
	

}
