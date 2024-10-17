package subscription.subscription.model;

public class CustomerModel {
	
	private Long id;
    private String name;
    private String email;
    private int totalBids;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTotalBids() {
		return totalBids;
	}
	public void setTotalBids(int totalBids) {
		this.totalBids = totalBids;
	}
	
	
	@Override
	public String toString() {
		return "CustomerModel [id=" + id + ", name=" + name + ", email=" + email + ", totalBids=" + totalBids + "]";
	}
    
	
	public CustomerModel(Long id, String name, String email, int totalBids) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.totalBids = totalBids;
	}
    

}
