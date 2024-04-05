package com.emir.lessonservice.controller;

import com.emir.lessonservice.client.StudentClient;
import com.emir.lessonservice.model.Lesson;
import com.emir.lessonservice.model.Student;
import com.emir.lessonservice.producer.LessonProducer;
import com.emir.lessonservice.service.LessonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final LessonProducer lessonProducer;
    private final StudentClient studentClient;

    public LessonController(LessonService lessonService, LessonProducer lessonProducer, StudentClient studentClient) {
        this.lessonService = lessonService;
        this.lessonProducer = lessonProducer;
        this.studentClient = studentClient;
    }

    @PostMapping
    public Lesson add(@RequestBody Lesson lesson) {
        return lessonService.addLesson(lesson);
    }

    @GetMapping("/{id}")
    public Lesson findById(@PathVariable String id) {
        return lessonService.getLesson(id);
    }

    @GetMapping
    public List<Lesson> findAll() {
        return lessonService.findAll();
    }

    @GetMapping("/allwithstudents")
    public List<Lesson> findAllWithStudents() {
        List<Lesson> lessonList = lessonService.findAll();

        lessonList.forEach(c -> c.setStudentList(studentClient.findByLessonId(c.getId())));

        return lessonList;
    }

    @PostMapping("/notification")
    public ResponseEntity<Lesson> postLessonEvent(@RequestBody Lesson lesson) throws JsonProcessingException {
        lessonProducer.sendLessonTopic(lesson);
        return  ResponseEntity.status(HttpStatus.CREATED).body(lesson);
    }


}
