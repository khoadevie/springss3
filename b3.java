package com.example.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
public class CourseManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseManagementApplication.class, args);
    }

    static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public T getData() { return data; }
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
        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
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
        public void setTitle(String title) { this.title = title; }
        public void setStatus(String status) { this.status = status; }
        public void setInstructorId(Long instructorId) { this.instructorId = instructorId; }
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
        public void setStudentName(String studentName) { this.studentName = studentName; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
    }

    @Repository
    static class InstructorRepository {
        private final Map<Long, Instructor> data = new HashMap<>();

        public InstructorRepository() {
            data.put(1L, new Instructor(1L, "Nguyen Van A", "a@gmail.com"));
            data.put(2L, new Instructor(2L, "Tran Thi B", "b@gmail.com"));
        }

        public List<Instructor> findAll() { return new ArrayList<>(data.values()); }
        public Instructor findById(Long id) { return data.get(id); }
        public Instructor create(Instructor i) { data.put(i.getId(), i); return i; }
        public Instructor update(Long id, Instructor i) {
            Instructor old = data.get(id);
            if (old == null) return null;
            old.setName(i.getName());
            old.setEmail(i.getEmail());
            return old;
        }
        public Instructor deleteById(Long id) { return data.remove(id); }
    }

    @Repository
    static class CourseRepository {
        private final Map<Long, Course> data = new HashMap<>();

        public CourseRepository() {
            data.put(1L, new Course(1L, "Java Spring", "OPEN", 1L));
            data.put(2L, new Course(2L, "Backend", "CLOSED", 2L));
        }

        public List<Course> findAll() { return new ArrayList<>(data.values()); }
        public Course findById(Long id) { return data.get(id); }
        public Course create(Course c) { data.put(c.getId(), c); return c; }
        public Course update(Long id, Course c) {
            Course old = data.get(id);
            if (old == null) return null;
            old.setTitle(c.getTitle());
            old.setStatus(c.getStatus());
            old.setInstructorId(c.getInstructorId());
            return old;
        }
        public Course deleteById(Long id) { return data.remove(id); }
    }

    @Repository
    static class EnrollmentRepository {
        private final Map<Long, Enrollment> data = new HashMap<>();

        public EnrollmentRepository() {
            data.put(1L, new Enrollment(1L, "Khoa", 1L));
            data.put(2L, new Enrollment(2L, "An", 2L));
        }

        public List<Enrollment> findAll() { return new ArrayList<>(data.values()); }
        public Enrollment findById(Long id) { return data.get(id); }
        public Enrollment create(Enrollment e) { data.put(e.getId(), e); return e; }
        public Enrollment update(Long id, Enrollment e) {
            Enrollment old = data.get(id);
            if (old == null) return null;
            old.setStudentName(e.getStudentName());
            old.setCourseId(e.getCourseId());
            return old;
        }
        public Enrollment deleteById(Long id) { return data.remove(id); }
    }

    @Service
    static class InstructorService {
        private final InstructorRepository repo;

        public InstructorService(InstructorRepository repo) {
            this.repo = repo;
        }

        public List<Instructor> getAll() { return repo.findAll(); }
        public Instructor getById(Long id) { return repo.findById(id); }
        public Instructor create(Instructor i) { return repo.create(i); }
        public Instructor update(Long id, Instructor i) { return repo.update(id, i); }
        public Instructor delete(Long id) { return repo.deleteById(id); }
    }

    @Service
    static class CourseService {
        private final CourseRepository repo;

        public CourseService(CourseRepository repo) {
            this.repo = repo;
        }

        public List<Course> getAll() { return repo.findAll(); }
        public Course getById(Long id) { return repo.findById(id); }
        public Course create(Course c) { return repo.create(c); }
        public Course update(Long id, Course c) { return repo.update(id, c); }
        public Course delete(Long id) { return repo.deleteById(id); }
    }

    @Service
    static class EnrollmentService {
        private final EnrollmentRepository repo;

        public EnrollmentService(EnrollmentRepository repo) {
            this.repo = repo;
        }

        public List<Enrollment> getAll() { return repo.findAll(); }
        public Enrollment getById(Long id) { return repo.findById(id); }
        public Enrollment create(Enrollment e) { return repo.create(e); }
        public Enrollment update(Long id, Enrollment e) { return repo.update(id, e); }
        public Enrollment delete(Long id) { return repo.deleteById(id); }
    }

    @RestController
    @RequestMapping("/instructors")
    static class InstructorController {
        private final InstructorService service;

        public InstructorController(InstructorService service) {
            this.service = service;
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<Instructor>>> getAll() {
            return ResponseEntity.ok(new ApiResponse<>(true, "Fetched instructors", service.getAll()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<Instructor>> getById(@PathVariable Long id) {
            Instructor i = service.getById(id);
            if (i == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Instructor not found", null));
            return ResponseEntity.ok(new ApiResponse<>(true, "Success", i));
        }

        @PostMapping
        public ResponseEntity<ApiResponse<Instructor>> create(@RequestBody Instructor i) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Created", service.create(i)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Instructor>> update(@PathVariable Long id, @RequestBody Instructor i) {
            Instructor updated = service.update(id, i);
            if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Instructor not found", null));
            return ResponseEntity.ok(new ApiResponse<>(true, "Updated", updated));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
            Instructor deleted = service.delete(id);
            if (deleted == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Instructor not found", null));
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(true, "Deleted", null));
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
        public ResponseEntity<ApiResponse<List<Course>>> getAll() {
            return ResponseEntity.ok(new ApiResponse<>(true, "Fetched courses", service.getAll()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<Course>> getById(@PathVariable Long id) {
            Course c = service.getById(id);
            if (c == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Course not found", null));
            return ResponseEntity.ok(new ApiResponse<>(true, "Success", c));
        }

        @PostMapping
        public ResponseEntity<ApiResponse<Course>> create(@RequestBody Course c) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Created", service.create(c)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Course>> update(@PathVariable Long id, @RequestBody Course c) {
            Course updated = service.update(id, c);
            if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Course not found", null));
            return ResponseEntity.ok(new ApiResponse<>(true, "Updated", updated));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
            Course deleted = service.delete(id);
            if (deleted == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Course not found", null));
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(true, "Deleted", null));
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
        public ResponseEntity<ApiResponse<List<Enrollment>>> getAll() {
            return ResponseEntity.ok(new ApiResponse<>(true, "Fetched enrollments", service.getAll()));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<Enrollment>> getById(@PathVariable Long id) {
            Enrollment e = service.getById(id);
            if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Enrollment not found", null));
            return ResponseEntity.ok(new ApiResponse<>(true, "Success", e));
        }

        @PostMapping
        public ResponseEntity<ApiResponse<Enrollment>> create(@RequestBody Enrollment e) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Created", service.create(e)));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<Enrollment>> update(@PathVariable Long id, @RequestBody Enrollment e) {
            Enrollment updated = service.update(id, e);
            if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Enrollment not found", null));
            return ResponseEntity.ok(new ApiResponse<>(true, "Updated", updated));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
            Enrollment deleted = service.delete(id);
            if (deleted == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Enrollment not found", null));
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(true, "Deleted", null));
        }
    }
}