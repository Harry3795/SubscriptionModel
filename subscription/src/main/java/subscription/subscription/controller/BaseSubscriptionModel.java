package subscription.subscription.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class BaseSubscriptionModel {

	@GetMapping("/sub")
	public String sub() {
	
		return "sub";
	}
}
