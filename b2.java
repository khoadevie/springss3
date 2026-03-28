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

        public List<Instructor> getAllInstructor() { return repo.findAll(); }
        public Instructor getInstructorById(Long id) { return repo.findById(id); }
        public Instructor createInstructor(Instructor i) { return repo.create(i); }
        public Instructor updateInstructor(Long id, Instructor i) { return repo.update(id, i); }
        public Instructor deleteInstructorById(Long id) { return repo.deleteById(id); }
    }

    @Service
    static class CourseService {
        private final CourseRepository repo;

        public CourseService(CourseRepository repo) {
            this.repo = repo;
        }

        public List<Course> getAllCourse() { return repo.findAll(); }
        public Course getCourseById(Long id) { return repo.findById(id); }
        public Course createCourse(Course c) { return repo.create(c); }
        public Course updateCourse(Long id, Course c) { return repo.update(id, c); }
        public Course deleteCourseById(Long id) { return repo.deleteById(id); }
    }

    @Service
    static class EnrollmentService {
        private final EnrollmentRepository repo;

        public EnrollmentService(EnrollmentRepository repo) {
            this.repo = repo;
        }

        public List<Enrollment> getAllEnrollment() { return repo.findAll(); }
        public Enrollment getEnrollmentById(Long id) { return repo.findById(id); }
        public Enrollment createEnrollment(Enrollment e) { return repo.create(e); }
        public Enrollment updateEnrollment(Long id, Enrollment e) { return repo.update(id, e); }
        public Enrollment deleteEnrollmentById(Long id) { return repo.deleteById(id); }
    }

    @RestController
    @RequestMapping("/instructors")
    static class InstructorController {
        private final InstructorService service;

        public InstructorController(InstructorService service) {
            this.service = service;
        }

        @GetMapping
        public ResponseEntity<List<Instructor>> getAll() {
            return ResponseEntity.ok(service.getAllInstructor());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Instructor> getById(@PathVariable Long id) {
            Instructor i = service.getInstructorById(id);
            if (i == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(i);
        }

        @PostMapping
        public ResponseEntity<Instructor> create(@RequestBody Instructor i) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createInstructor(i));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Instructor> update(@PathVariable Long id, @RequestBody Instructor i) {
            Instructor updated = service.updateInstructor(id, i);
            if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(updated);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            Instructor deleted = service.deleteInstructorById(id);
            if (deleted == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        public ResponseEntity<List<Course>> getAll() {
            return ResponseEntity.ok(service.getAllCourse());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Course> getById(@PathVariable Long id) {
            Course c = service.getCourseById(id);
            if (c == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(c);
        }

        @PostMapping
        public ResponseEntity<Course> create(@RequestBody Course c) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createCourse(c));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course c) {
            Course updated = service.updateCourse(id, c);
            if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(updated);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            Course deleted = service.deleteCourseById(id);
            if (deleted == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
        public ResponseEntity<List<Enrollment>> getAll() {
            return ResponseEntity.ok(service.getAllEnrollment());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Enrollment> getById(@PathVariable Long id) {
            Enrollment e = service.getEnrollmentById(id);
            if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(e);
        }

        @PostMapping
        public ResponseEntity<Enrollment> create(@RequestBody Enrollment e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createEnrollment(e));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Enrollment> update(@PathVariable Long id, @RequestBody Enrollment e) {
            Enrollment updated = service.updateEnrollment(id, e);
            if (updated == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(updated);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            Enrollment deleted = service.deleteEnrollmentById(id);
            if (deleted == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}