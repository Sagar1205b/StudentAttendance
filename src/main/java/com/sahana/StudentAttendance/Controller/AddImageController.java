package com.sahana.StudentAttendance.Controller;


import com.sahana.StudentAttendance.Model.AddImage;
import com.sahana.StudentAttendance.Service.AddImageService;
import com.sahana.StudentAttendance.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/digital")
public class AddImageController {

    @Autowired
    private final AddImageService addImageService;
    

    @Autowired
    private final SubjectService subjectService;

    public AddImageController(AddImageService addImageService, SubjectService subjectService) {
        this.addImageService = addImageService;
        this.subjectService = subjectService;
    }


    @GetMapping("/subject-dropdown")
    public String getSubjectDropdown(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username",username);
        List<String> subjects = subjectService.getAllUSNs();
        model.addAttribute("USN", subjects);
        return "UploadFile"; // Return the view that displays the dropdown
    }

    @PostMapping("/faces")
    public String Uploadimage(@ModelAttribute("imageupload") AddImage addImage,
                              @RequestParam("file") MultipartFile file) {
        try {

            addImageService.addImage(addImage.getUSN(), file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "faceResponse";
    }



}
