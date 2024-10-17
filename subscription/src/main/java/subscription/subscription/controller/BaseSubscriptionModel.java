package subscription.subscription.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class BaseSubscriptionModel {

	@GetMapping("/sub")
	public String sub() {
	
		return index.html;
	}
}
