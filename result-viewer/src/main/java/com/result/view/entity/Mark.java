package com.result.view.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "result_viewer_marks")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;
    private String marks;
    private String maxMarks;
    private String feedback;
    private String grade;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Method to get marks obtained as an integer with null check
    public int getMarksObtained() {
        return marks != null && !marks.isEmpty() ? Integer.parseInt(this.marks) : 0;
    }

    // Method to get max marks as an integer with null check
    public int getMaxMarks() {
        return maxMarks != null && !maxMarks.isEmpty() ? Integer.parseInt(this.maxMarks) : 0;
    }
}
