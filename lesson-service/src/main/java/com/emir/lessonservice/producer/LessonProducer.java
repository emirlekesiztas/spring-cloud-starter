package com.emir.lessonservice.producer;

import com.emir.lessonservice.model.Lesson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class LessonProducer {

    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    public LessonProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendLessonTopic(Lesson lesson) throws JsonProcessingException {
        String valueJson = objectMapper.writeValueAsString(lesson);
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.sendDefault(lesson.getId(), valueJson); // default topic comes from application.yaml

        completableFuture.thenApply(result -> {
            handleSucces(lesson.getId(), valueJson, result);
            return result;
        });

        completableFuture.exceptionally(throwable -> {
            handleFailure(throwable);
            return null;
        });
    }

    private void handleSucces(String key, String value, SendResult<String, String> result) {
        System.out.println("Message sent. Key : {"+key+ "}, Value {"+value+"}, partition {"+result.getRecordMetadata().partition()+"}");
    }

    private void handleFailure(Throwable throwable) {
        System.out.println("Message sent failure. Error message : {"+ throwable.getMessage() +"}");

        try {
            throw throwable;
        } catch (Throwable e) {
            System.err.println("Error on OnFailure" + e.getMessage());
        }
    }

}
