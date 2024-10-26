package com.example.demo.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSummaryDTO {
    private Long id;
    private String name;
    private Integer idCourseMoodle;
}
