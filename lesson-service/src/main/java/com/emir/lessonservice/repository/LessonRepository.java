package com.emir.lessonservice.repository;

import com.emir.lessonservice.model.Lesson;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LessonRepository extends MongoRepository<Lesson, String> {
}
