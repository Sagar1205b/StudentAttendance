package com.sahana.StudentAttendance.Controller;


import com.sahana.StudentAttendance.Model.Attendance;
import com.sahana.StudentAttendance.Model.AttendanceDTO;
import com.sahana.StudentAttendance.Model.LicenseHolder;
import com.sahana.StudentAttendance.Model.SubjectUser;
import com.sahana.StudentAttendance.Repository.AttendanceRepository;
import com.sahana.StudentAttendance.Repository.LicenseHolderRepository;
import com.sahana.StudentAttendance.Service.AttendanceService;
import com.sahana.StudentAttendance.Service.EmailSenderService;
import com.sahana.StudentAttendance.Service.LicenseHolderService;
import com.sahana.StudentAttendance.Service.SubjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/digital")
public class AuthController {


    private final SubjectService subjectService;
    private final LicenseHolderService licenseHolderService;
    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;
    private final LicenseHolderRepository licenseHolderRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public AuthController(SubjectService subjectService, LicenseHolderService licenseHolderService, AttendanceService attendanceService, AttendanceRepository attendanceRepository, LicenseHolderRepository licenseHolderRepository) {
        this.subjectService = subjectService;
        this.licenseHolderService = licenseHolderService;
        this.attendanceService = attendanceService;
        this.attendanceRepository = attendanceRepository;
        this.licenseHolderRepository = licenseHolderRepository;
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
    public String getDash(Model model) {
        List<SubjectUser> subjectUsers = subjectService.findAll();

        // Get today's date string in "yyyy-MM-dd" format
        LocalDate today = LocalDate.now();
        String todayStr = today.toString();

        // ✅ Get only today's attendance
        List<Attendance> todaysAttendance = attendanceService.findByFilters(null, null, todayStr);

        // Map USN to today's attendance
        Map<String, Attendance> attendanceMap = todaysAttendance.stream()
                .collect(Collectors.toMap(
                        Attendance::getUSN,
                        a -> a,
                        (a1, a2) -> a1.getTimestamp().isAfter(a2.getTimestamp()) ? a1 : a2
                ));

        // Build the DTO list
        List<AttendanceDTO> studentAttendanceList = subjectUsers.stream().map(user -> {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setUsn(user.getUsn());
            dto.setName(user.getSubject());
            dto.setBranch(user.getBranch());
            dto.setSemester(user.getSem());

            Attendance todayRecord = attendanceMap.get(user.getUsn());
            if (todayRecord != null) {
                dto.setPresent(todayRecord.isPresent());
                dto.setTimestamp(todayRecord.getTimestamp());
            } else {
                dto.setPresent(false); // default to absent
                dto.setTimestamp(null);
            }

            return dto;
        }).collect(Collectors.toList());

        // Count present students only from today's attendance
        long presentCount = studentAttendanceList.stream().filter(AttendanceDTO::isPresent).count();
        long totalStudents = subjectUsers.size();
        long absentCount = totalStudents - presentCount;
        long percentage = totalStudents > 0 ? Math.round((presentCount * 100.0) / totalStudents) : 0;

        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("presentCount", presentCount);
        model.addAttribute("absentCount", absentCount);
        model.addAttribute("percentage", percentage);
        model.addAttribute("students", studentAttendanceList);
        model.addAttribute("attendancePresent", !todaysAttendance.isEmpty());

        return "attendance";
    }





//    @PostMapping("/save")
//    public  String saveLicenseHolder(@ModelAttribute("licenseholder") LicenseHolder thelicenseHolder, BindingResult result,Model model){
//        /* Random random=new Random();
//	int otp=100000+random.nextInt(900000);
//    String name=thelicenseHolder.getUsername();
//
//        String email= thelicenseHolder.getEmail();
//        String subject="DigtialIdentity Email Verification";
//        String body = "Hi "+name+",\nYour one time password is " + otp + ".\nPlease don't share this with anyone.";
//        session.setAttribute("otp", otp);
//        session.setAttribute("tempUser", thelicenseHolder);
//
//        emailSenderService.sendEmail(email,subject,body);
//
//        licenseHolderService.save(thelicenseHolder);
//                   */
//        Optional<LicenseHolder> existingUser=licenseHolderRepository.findByUsername(thelicenseHolder.getUsername());
//        Optional<LicenseHolder> existingEmail=licenseHolderRepository.findByEmail(thelicenseHolder.getEmail());
//
//
//        licenseHolderService.save(thelicenseHolder);
//
//        return  "redirect:/digital/home";
//    }
    @PostMapping("/save")
    public String saveLicenseHolder(@Valid @ModelAttribute("licenseholder") LicenseHolder licenseHolder,
                                    BindingResult result,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {

        // Check if username exists
        if (licenseHolderRepository.findByUsername(licenseHolder.getUsername()).isPresent()) {
            model.addAttribute("usernameError", "Username is already taken");
            return "register";
        }

        // Check if email exists
        if (licenseHolderRepository.findByEmail(licenseHolder.getEmail()).isPresent()) {
            model.addAttribute("emailError", "Email is already registered");
            return "register";
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            licenseHolderService.save(licenseHolder);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/digital/home";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

//    @PostMapping("/verify-otp")
//    public String verifyOtp(@RequestParam("otp") int enteredOtp, HttpSession session) {
//        Integer generatedOtp = (Integer) session.getAttribute("otp");
//        LicenseHolder licenseHolder = (LicenseHolder) session.getAttribute("tempUser");
//
//        if (generatedOtp != null && licenseHolder != null && enteredOtp == generatedOtp) {
//            licenseHolderService.save(licenseHolder); // Now save to DB
//
//            // Clean up session
//            session.removeAttribute("otp");
//            session.removeAttribute("tempUser");
//
//            return "redirect:/digital/home";
//        } else {
//            // Add error message if needed
//            return "verify-otp";
//        }
//    }






    @GetMapping("/attendance-filter")
    public String filterAttendance(
            @RequestParam(required = false) String branch,
            @RequestParam(name = "semester", required = false) Integer sem,
            @RequestParam(name = "date", required = false) String dateStr,
            Model model) {

        List<SubjectUser> allStudents = subjectService.findAll();

        // Filter students by branch and semester (only if provided)
        List<SubjectUser> filteredStudents = allStudents.stream()
                .filter(user -> {
                    boolean branchMatches = (branch == null || branch.isBlank()) || branch.equalsIgnoreCase(user.getBranch());

                    boolean semMatches = true;
                    if (sem != null) {
                        String userSemStr = user.getSem();
                        if (userSemStr != null && !userSemStr.isBlank()) {
                            try {
                                int userSem = Integer.parseInt(userSemStr.trim());
                                semMatches = sem.equals(userSem);
                            } catch (NumberFormatException e) {
                                semMatches = false;
                            }
                        } else {
                            semMatches = false;
                        }
                    }

                    boolean dateMatches = true;


                    return branchMatches && semMatches && dateMatches;
                })
                .toList();


        // Fetch attendance filtered already by branch, sem, date
        List<Attendance> filteredAttendance = attendanceService.findByFilters(branch, sem, dateStr);

        // Map latest attendance per student
        Map<SubjectUser, Attendance> latestAttendanceMap = filteredAttendance.stream()
                .filter(att -> filteredStudents.contains(att.getSubjectUser()))
                .collect(Collectors.toMap(
                        Attendance::getSubjectUser,
                        a -> a,
                        (a1, a2) -> a1.getTimestamp().isAfter(a2.getTimestamp()) ? a1 : a2
                ));

        // Combine into DTOs
        List<AttendanceDTO> studentAttendanceList = filteredStudents.stream()
                .map(user -> {
                    AttendanceDTO dto = new AttendanceDTO();
                    dto.setUsn(user.getUsn());
                    dto.setName(user.getSubject());
                    dto.setBranch(user.getBranch());
                    dto.setSemester(user.getSem());

                    Attendance latestAttendance = latestAttendanceMap.get(user);
                    if (latestAttendance != null) {
                        dto.setPresent(latestAttendance.isPresent());
                        dto.setTimestamp(latestAttendance.getTimestamp());
                    } else {
                        dto.setPresent(false);
                        dto.setTimestamp(null);
                    }

                    return dto;
                }).toList();

        // Summary Stats
        long presentCount = studentAttendanceList.stream().filter(AttendanceDTO::isPresent).count();
        long absentCount = studentAttendanceList.size() - presentCount;
        long percentage = studentAttendanceList.isEmpty() ? 0 :
                Math.round((presentCount * 100.0) / studentAttendanceList.size());


        System.out.printf("Filtering: branch=%s, sem=%s, date=%s%n", branch, sem, dateStr);

        // Model attributes
        model.addAttribute("students", studentAttendanceList);
        model.addAttribute("presentCount", presentCount);
        model.addAttribute("absentCount", absentCount);
        model.addAttribute("percentage", percentage);
        model.addAttribute("totalStudents", studentAttendanceList.size());

        return "attendance";
    }




}



