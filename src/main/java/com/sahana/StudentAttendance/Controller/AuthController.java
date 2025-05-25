package com.sahana.StudentAttendance.Controller;


import com.sahana.StudentAttendance.Model.LicenseHolder;
import com.sahana.StudentAttendance.Service.EmailSenderService;
import com.sahana.StudentAttendance.Service.LicenseHolderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
@RequestMapping("/digital")
public class AuthController {


    private LicenseHolderService licenseHolderService;

    @Autowired
    private EmailSenderService emailSenderService;

    public AuthController(LicenseHolderService licenseHolderService) {
        this.licenseHolderService = licenseHolderService;
    }




    @GetMapping("/signup")
    public String getSignup(){
        return "register";
    }

    @GetMapping("/verify-otp")
    public String getOtpVerificationPage() {

        return "verify-otp"; // Thymeleaf page to enter OTP
    }

    @GetMapping("/dash")
    public String getDash(){

        return "attendance";
    }



    @PostMapping("/save")
    public  String saveLicenseHolder(@ModelAttribute("licenseholder") LicenseHolder thelicenseHolder, HttpSession session){
        Random random=new Random();
	int otp=100000+random.nextInt(900000);
    String name=thelicenseHolder.getUsername();

        String email= thelicenseHolder.getEmail();
        String subject="DigtialIdentity Email Verification";
        String body = "Hi "+name+",\nYour one time password is " + otp + ".\nPlease don't share this with anyone.";
        session.setAttribute("otp", otp);
        session.setAttribute("tempUser", thelicenseHolder);

        emailSenderService.sendEmail(email,subject,body);


//        licenseHolderService.save(thelicenseHolder);

        return  "redirect:/digital/verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") int enteredOtp, HttpSession session) {
        Integer generatedOtp = (Integer) session.getAttribute("otp");
        LicenseHolder licenseHolder = (LicenseHolder) session.getAttribute("tempUser");

        if (generatedOtp != null && licenseHolder != null && enteredOtp == generatedOtp) {
            licenseHolderService.save(licenseHolder); // Now save to DB

            // Clean up session
            session.removeAttribute("otp");
            session.removeAttribute("tempUser");

            return "redirect:/digital/home";
        } else {
            // Add error message if needed
            return "verify-otp";
        }
    }


//    @PostMapping("/save")
//    public  String saveLicenseHolder(@ModelAttribute("licenseholder")LicenseHolder thelicenseHolder){
//
//
////
//        licenseHolderService.save(thelicenseHolder);
////        UsernamePasswordAuthenticationToken authRequest =
////                new UsernamePasswordAuthenticationToken(thelicenseHolder.getUsername(), thelicenseHolder.getPassword());
//
////        Authentication authentication = authenticationManager.authenticate(authRequest);
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////        use redirect to prevent duplicate submissions
//        return  "redirect:/digital/home";
//    }
}



