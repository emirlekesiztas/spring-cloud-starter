package com.emir.lessonservice.service;

import com.emir.lessonservice.model.Lesson;
import com.emir.lessonservice.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson addLesson(Lesson lesson) {
        lessonRepository.save(lesson);
        return lesson;
    }

    public Lesson getLesson(String id) {
        return lessonRepository.findById(id).orElseThrow();
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }
}
