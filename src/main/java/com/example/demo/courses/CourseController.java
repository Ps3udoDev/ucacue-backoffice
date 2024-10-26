package com.example.demo.courses;

import com.example.demo.common.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseOutDTO>> getCourses() {
        List<CourseOutDTO> courses = courseService.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseOutDTO> getCourseById(@PathVariable Long id) {
        Course course = courseService.findOne(id);
        CourseOutDTO courseOutDTO = new ModelMapper().map(course, CourseOutDTO.class);
        return new ResponseEntity<>(courseOutDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CourseOutDTO>> createCourse(@RequestBody @Valid CourseInDTO courseInDTO) {
        CourseOutDTO createdCourse = courseService.create(courseInDTO);

        CommonResponse<CourseOutDTO> response = CommonResponse.<CourseOutDTO>builder()
                .status("SUCCESS")
                .recordId(createdCourse.getId())
                .data(createdCourse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseOutDTO>> updateCourse(@PathVariable Long id, @RequestBody @Valid CourseInDTO courseInDTO) {
        CourseOutDTO updatedCourse = courseService.update(id, courseInDTO);

        CommonResponse<CourseOutDTO> response = CommonResponse.<CourseOutDTO>builder()
                .status("SUCCESS")
                .recordId(updatedCourse.getId())
                .data(updatedCourse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteCourse(@PathVariable Long id) {
        courseService.delete(id);

        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .status("SUCCESS")
                .recordId(null)
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
