package com.result.view.controller;

import com.result.view.dto.RequestResultForm;
import com.result.view.entity.Mark;
import com.result.view.entity.Student;
import com.result.view.repository.StudentRepo;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PageController {

    private final StudentRepo studentRepo;

    public PageController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/view-result")
    public String viewResultForm(Model model) {
        model.addAttribute("requestResultForm", new RequestResultForm());
        return "view_result_form";
    }

    @PostMapping("/view-result-action")
    public String viewResult(
            @Valid @ModelAttribute RequestResultForm requestResultForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "view_result_form";
        }

        // Find student by roll number and date of birth
        Optional<Student> optionalStudent = studentRepo.findByRollNumberAndDateOfBirth(
                requestResultForm.getRollNumber(), requestResultForm.getDateOfBirth()
        );

        if (optionalStudent.isEmpty()) {
            model.addAttribute("message", "Student not found");
            return "view_result_form";
        }

        Student student = optionalStudent.get();
        List<Mark> marks = student.getMarks();

        // Ensure marks is not null and remove duplicates
        if (marks == null || marks.isEmpty()) {
            marks = new ArrayList<>();  // If no marks, set an empty list
            model.addAttribute("marks", marks);
            model.addAttribute("totalMarks", "0/0");
            model.addAttribute("percentage", "0.00%");
            model.addAttribute("result", "No Data Available");
        } else {
            marks = marks.stream().distinct().collect(Collectors.toList()); // Remove duplicates

            // Calculate total marks, max marks, percentage, and result
            int totalMarks = marks.stream().mapToInt(Mark::getMarksObtained).sum();
            int maxMarks = marks.stream().mapToInt(Mark::getMaxMarks).sum();

            // Avoid division by zero (if maxMarks is 0)
            double percentage = maxMarks > 0 ? (double) totalMarks / maxMarks * 100 : 0;
            String result = percentage >= 35 ? "Passed" : "Failed";

            model.addAttribute("marks", marks);
            model.addAttribute("totalMarks", totalMarks + "/" + maxMarks);
            model.addAttribute("percentage", String.format("%.2f", percentage) + "%");
            model.addAttribute("result", result);
        }

        // Add student data and generation date to the model
        model.addAttribute("student", student);
        model.addAttribute("generationDate", LocalDate.now().toString());

        return "view_result";
    }


    @GetMapping("/help")
    public String help() {
        return "help";
    }

    @GetMapping("/user-login")
    public String loginPage() {
        return "login_page";
    }
}
