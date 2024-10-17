package subscription.subscription.model;

import java.time.LocalDate;

public class CustomerSubscriptionModel {

	
	 private Long id;
	    private Long customerId;
	    private Long planId;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private int remainingBids;
	    private boolean isActive;
	    
	    
	    // Getters and setters
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
		public Long getPlanId() {
			return planId;
		}
		public void setPlanId(Long planId) {
			this.planId = planId;
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
		public boolean isActive() {
			return isActive;
		}
		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
		@Override
		public String toString() {
			return "CustomerSubscriptionModel [id=" + id + ", customerId=" + customerId + ", planId=" + planId
					+ ", startDate=" + startDate + ", endDate=" + endDate + ", remainingBids=" + remainingBids
					+ ", isActive=" + isActive + "]";
		}
	    
	 
	    
}
