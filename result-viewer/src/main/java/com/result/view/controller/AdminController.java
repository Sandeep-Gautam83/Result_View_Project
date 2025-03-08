package com.result.view.controller;

import com.result.view.dto.StudentForm;
import com.result.view.entity.Mark;
import com.result.view.entity.Student;
import com.result.view.repository.StudentRepo;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentRepo studentRepo;
    private final ModelMapper modelMapper;

    public AdminController(StudentRepo studentRepo, ModelMapper modelMapper) {
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/result-page")
    public String redirectHandler() {
        return "redirect:/admin/add-result";
    }

//    @GetMapping("/add-result")
//    public String addResultForm(Principal principal,Model model) {
//        String name = principal.getName();
//        System.out.println(name);
//
//        StudentForm studentForm = new StudentForm();
//
//        // Prepare standard options for the form
//        List<String> standardOptions = getStandardOptions();
//
//        model.addAttribute("studentForm", studentForm);
//        model.addAttribute("standardOptions", standardOptions);
//        model.addAttribute("name",name);
//        return "admin/add_result";
//    }

    @GetMapping("/add-result")
    public String addResultForm(Principal principal, Model model) { // Added missing comma
        String name = principal.getName();
        System.out.println(name);

        StudentForm studentForm = new StudentForm();

        // Prepare standard options for the form
        List<String> standardOptions = getStandardOptions();

        model.addAttribute("studentForm", studentForm);
        model.addAttribute("standardOptions", standardOptions);
        model.addAttribute("name", name);

        return "admin/add_result";
    }



    @PostMapping("/add-result-action")
    public String processAddResultForm(@Valid @ModelAttribute StudentForm studentForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Prepare standard options for the form
            List<String> standardOptions = getStandardOptions();

            model.addAttribute("studentForm", studentForm);
            model.addAttribute("standardOptions", standardOptions);
            return "admin/add_result"; // Return to the form with error messages
        }

        // Convert StudentForm to Student entity
        Student student = modelMapper.map(studentForm, Student.class);

         List<Mark> updatedList = student.getMarks().stream().map(mark -> {
            mark.setStudent(student);
            return mark;
        }).toList();

        student.setMarks(updatedList);
        student.setId(UUID.randomUUID().toString());
        studentRepo.save(student);

        return "redirect:/admin/add-result?message=Student added successfully";
    }

    // Utility method to provide standard options
    private List<String> getStandardOptions() {
        return List.of("CLASS 1", "CLASS 2", "CLASS 3", "CLASS 4", "CLASS 5", "CLASS 6");
    }
}
