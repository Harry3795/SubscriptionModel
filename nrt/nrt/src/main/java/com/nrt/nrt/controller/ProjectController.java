package com.nrt.nrt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nrt.nrt.model.ProjectModel;
import com.nrt.nrt.service.ProjectService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public ModelAndView listProjects(HttpSession session) {
        List<ProjectModel> projects = projectService.getAllProjects();
        ModelAndView modelAndView = new ModelAndView("projects");
        
        modelAndView.addObject("projects", projects);
        return modelAndView;
    }

    @PostMapping("/projects/bid")
    public ModelAndView bidOnProject(Long projectId, HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        // Get the logged-in user's ID from the session
        Long registerId = (Long) session.getAttribute("registerId");

        if (registerId == null) {
            // Redirect to login if user is not logged in
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        try {
            // Deduct bid from the user's account using the session's user ID
            projectService.bidOnProject(registerId, projectId);
            redirectAttributes.addFlashAttribute("successMessage", "Bid placed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }
}
