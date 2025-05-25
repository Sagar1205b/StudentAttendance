package com.sahana.StudentAttendance.Controller;


import com.sahana.StudentAttendance.Model.SubjectRequest;
import com.sahana.StudentAttendance.Repository.LicenseHolderRepository;
import com.sahana.StudentAttendance.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/digital")
public class SubjectController {



    private  final LicenseHolderRepository licenseHolderRepository;

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(LicenseHolderRepository licenseHolderRepository, SubjectService subjectService) {
        this.licenseHolderRepository = licenseHolderRepository;

        this.subjectService = subjectService;
    }

    // GET method to display the form
    @GetMapping("/subject/create")
    public String showCreateSubjectForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username",username);
        model.addAttribute("subjectcreation", new SubjectRequest()); // Initialize form backing object
       model.addAttribute("USN",subjectService.getAllUSNs());
        return "createSubject"; // Thymeleaf template
    }

    // POST method to handle form submission

    @PostMapping("/subject/create")
    public String submitSubjectForm(@ModelAttribute("subjectcreation") SubjectRequest subjectDTO) {

        subjectService.createSubject(subjectDTO.getUSN());
        subjectService.createAndSaveSubject((subjectDTO));

        return "redirect:/digital/subject/create";
    }




   /* @PostMapping("/subject/create1")
    public String submitSubjectAndForm(@ModelAttribute("subjectcreation") SubjectRequest subjectDTO) {

    subjectService.createSubject(String.valueOf(subjectDTO.getSubject()));
    SubjectUser user=new SubjectUser();
    user.setSubject(subjectDTO.getSubject());
    user.getId();

    subjectService.save(user);

        return "redirect:/digital/subject/create";
    }*/


}
