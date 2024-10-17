package subscription.subscription.model;

public class SubscriptionPlanModel {
	private Long id;
    private String name;
    private int validityInMonths;
    private int bids;
    private double amount;
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
	public int getBids() {
		return bids;
	}
	public void setBids(int bids) {
		this.bids = bids;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "SubscriptionPlanModel [id=" + id + ", name=" + name + ", validityInMonths=" + validityInMonths
				+ ", bids=" + bids + ", amount=" + amount + "]";
	}
    
    

}
