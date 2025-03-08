package com.result.view.dto;

import com.result.view.entity.Student;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkForm {
    private String subjectName;
    private String marks;
    private String maxMarks;
    private String feedback;
    private String grade;


    private Student student;
}
