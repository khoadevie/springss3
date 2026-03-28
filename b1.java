package com.example.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CourseManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseManagementApplication.class, args);
    }

    static class Instructor {
        private Long id;
        private String name;
        private String email;

        public Instructor(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    static class Course {
        private Long id;
        private String title;
        private String status;
        private Long instructorId;

        public Course(Long id, String title, String status, Long instructorId) {
            this.id = id;
            this.title = title;
            this.status = status;
            this.instructorId = instructorId;
        }

        public Long getId() { return id; }
        public String getTitle() { return title; }
        public String getStatus() { return status; }
        public Long getInstructorId() { return instructorId; }
    }

    static class Enrollment {
        private Long id;
        private String studentName;
        private Long courseId;

        public Enrollment(Long id, String studentName, Long courseId) {
            this.id = id;
            this.studentName = studentName;
            this.courseId = courseId;
        }

        public Long getId() { return id; }
        public String getStudentName() { return studentName; }
        public Long getCourseId() { return courseId; }
    }

    @Repository
    static class InstructorRepository {
        private final List<Instructor> data = new ArrayList<>();

        public InstructorRepository() {
            data.add(new Instructor(1L, "Nguyen Van A", "a@gmail.com"));
            data.add(new Instructor(2L, "Tran Thi B", "b@gmail.com"));
        }

        public List<Instructor> findAll() {
            return data;
        }
    }

    @Repository
    static class CourseRepository {
        private final List<Course> data = new ArrayList<>();

        public CourseRepository() {
            data.add(new Course(1L, "Java Spring", "OPEN", 1L));
            data.add(new Course(2L, "Backend", "CLOSED", 2L));
        }

        public List<Course> findAll() {
            return data;
        }
    }

    @Repository
    static class EnrollmentRepository {
        private final List<Enrollment> data = new ArrayList<>();

        public EnrollmentRepository() {
            data.add(new Enrollment(1L, "Khoa", 1L));
            data.add(new Enrollment(2L, "An", 2L));
        }

        public List<Enrollment> findAll() {
            return data;
        }
    }

    @Service
    static class InstructorService {
        private final InstructorRepository repo;

        public InstructorService(InstructorRepository repo) {
            this.repo = repo;
        }

        public List<Instructor> getAll() {
            return repo.findAll();
        }
    }

    @Service
    static class CourseService {
        private final CourseRepository repo;

        public CourseService(CourseRepository repo) {
            this.repo = repo;
        }

        public List<Course> getAll() {
            return repo.findAll();
        }
    }

    @Service
    static class EnrollmentService {
        private final EnrollmentRepository repo;

        public EnrollmentService(EnrollmentRepository repo) {
            this.repo = repo;
        }

        public List<Enrollment> getAll() {
            return repo.findAll();
        }
    }

    @RestController
    @RequestMapping("/instructors")
    static class InstructorController {
        private final InstructorService service;

        public InstructorController(InstructorService service) {
            this.service = service;
        }

        @GetMapping
        public List<Instructor> getAll() {
            return service.getAll();
        }
    }

    @RestController
    @RequestMapping("/courses")
    static class CourseController {
        private final CourseService service;

        public CourseController(CourseService service) {
            this.service = service;
        }

        @GetMapping
        public List<Course> getAll() {
            return service.getAll();
        }
    }

    @RestController
    @RequestMapping("/enrollments")
    static class EnrollmentController {
        private final EnrollmentService service;

        public EnrollmentController(EnrollmentService service) {
            this.service = service;
        }

        @GetMapping
        public List<Enrollment> getAll() {
            return service.getAll();
        }
    }
}