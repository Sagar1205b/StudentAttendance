package com.sahana.StudentAttendance.Controller;

import com.sahana.StudentAttendance.Model.SubjectUser;
import com.sahana.StudentAttendance.Repository.SubjectRepository;
import com.sahana.StudentAttendance.Service.SubjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/digital")
public class HomeController {


    @Autowired
    private SubjectService subjectService;

    private final SubjectRepository subjectRepository;

    public HomeController(SubjectService subjectService, SubjectRepository subjectRepository) {
        this.subjectService = subjectService;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/home")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isRegistered=subjectService.hasUserCompletedRegistration(username);
        model.addAttribute("username",username);
        model.addAttribute("isRegistered",isRegistered);
        return "home";
    }
    @GetMapping("/index")
    public String getHome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean isRegistered=subjectService.hasUserCompletedRegistration(username);
        model.addAttribute("username",username);
        model.addAttribute("isRegistered",isRegistered);

        return "index";
    }


    @GetMapping("/default")
    public String defaultAfterLogin(Model model,HttpServletRequest request) {

        List<String> students = subjectService.getAllSubjects(); // Fetch from user_subject
        model.addAttribute("students", students);
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/digital/dash";
        }
        if (request.isUserInRole("USER")) {

            return "redirect:/digital/home";
        }
        // fallback
        return "redirect:/digital/LoginPage?error";
    }
}
