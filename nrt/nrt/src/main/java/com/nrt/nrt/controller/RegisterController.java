package com.nrt.nrt.controller;

import com.nrt.nrt.model.PlanModel;
import com.nrt.nrt.model.RegisterModel;
import com.nrt.nrt.service.RegisterService;
import com.nrt.nrt.service.SubscriptionService;
import com.nrt.nrt.model.SubscriptionModel;
import com.nrt.nrt.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegisterController {
	@Autowired
	SubscriptionService subscriptionService;
    @Autowired
    private RegisterService registerService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("register", new RegisterModel());
        return modelAndView;
    }
    
    
    @GetMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView("home");
        
        return modelAndView;
    }
    
    
    
    @GetMapping("/plans")
    public ModelAndView showDashboard(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("plans");
        Long registerId = (Long) session.getAttribute("registerId");

        if (registerId == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        // Fetch user and subscriptions
        RegisterModel user = registerService.findById(registerId);
        List<SubscriptionModel> subscriptions = subscriptionRepository.findByRegisterId(registerId);
        List<SubscriptionModel> activeSubscriptions = subscriptionRepository.findActiveSubscriptionByRegisterId(registerId); // Use the new method
        List<PlanModel> availablePlans = subscriptionService.getAllPlans();

        // Check if there are any active subscriptions
        SubscriptionModel activeSubscription = null;
        if (!activeSubscriptions.isEmpty()) {
            activeSubscription = activeSubscriptions.get(0); 
        }

        // Add data to the model
        modelAndView.addObject("plans", availablePlans);
        modelAndView.addObject("user", user);
        modelAndView.addObject("subscriptions", subscriptions);
        modelAndView.addObject("activeSubscription", activeSubscription); 
       

        return modelAndView;
    }

    
    
    
    @PostMapping(value = "/plans")
    public ModelAndView plans(@RequestAttribute PlanModel p) {
        ModelAndView modelAndView = new ModelAndView("plans");
      
        return modelAndView;
    }

    @PostMapping(value = "/register")
    public ModelAndView register(@ModelAttribute RegisterModel user, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        RegisterModel existingUser = registerService.findByEmail(user.getEmail());
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            modelAndView.setViewName("redirect:/register");
        } else {
            registerService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registered successfully");
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("userModel", new RegisterModel());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("userModel") RegisterModel user, 
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, 
                              HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Check for binding errors (e.g., validation)
        if (bindingResult.hasErrors()) {
            System.out.println("Binding errors: " + bindingResult.getAllErrors());
            modelAndView.setViewName("login");
            modelAndView.addObject("userModel", user);
            return modelAndView;
        }

        // Validate user credentials
        RegisterModel existingUser = registerService.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
        	
        	
            // Set registerId in session
            session.setAttribute("registerId", existingUser.getRegisterId());

            // Fetch subscriptions and store them in the session
            List<SubscriptionModel> subscriptions = subscriptionRepository.findByRegisterId(existingUser.getRegisterId());
            session.setAttribute("subscriptions", subscriptions);

            redirectAttributes.addFlashAttribute("successMessage", "Login successful");
            modelAndView.setViewName("redirect:/dashboard");
        } else {
            System.out.println("Invalid email or password for user: " + user.getEmail());
            modelAndView.setViewName("login");
            modelAndView.addObject("userModel", user);
            modelAndView.addObject("errorMessage", "Invalid email or password");
        }
        return modelAndView;
    }

@Autowired
RegisterService userService ;
    @GetMapping("/subs")
    public ModelAndView viewSubscriptions(HttpSession session, Model model) {
    	ModelAndView modelAndView = new ModelAndView();
    	
    	Long registerId = (Long) session.getAttribute("registerId");
        if (registerId != null) {
            List<SubscriptionModel> subscriptions = subscriptionService.getSubscriptionsByRegisterId(registerId);
            
            modelAndView.setViewName("subscriptions");
            model.addAttribute("subscriptions", subscriptions);
            modelAndView.addObject("user", registerService.findById(registerId));
			model.addAttribute("user", userService.getUserById(registerId)); // Add the user object to the model
        }
        return modelAndView; // The name of your Thymeleaf template
    }

    
}
