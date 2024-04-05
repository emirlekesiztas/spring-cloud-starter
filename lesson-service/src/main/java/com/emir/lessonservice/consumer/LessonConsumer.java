package com.emir.lessonservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class LessonConsumer {
    private static final Logger logger = LoggerFactory.getLogger(LessonConsumer.class);

    @KafkaListener(topics = "notification-topic")
    public void consume(String message) {
        logger.info(String.format("******* Consumer just receive the message -> %s", message));
    }
}
