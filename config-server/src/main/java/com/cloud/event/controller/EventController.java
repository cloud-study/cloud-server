package com.cloud.event.controller;

import com.cloud.event.basic.Event;
import com.cloud.event.basic.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/31 10:04
 * @description
 */
@RestController
@RequestMapping("/api/global")
public class EventController{
    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("/public/event")
    public ResponseEntity publishPublicEvent(@RequestBody Event event) {
        NotificationEvent notificationEvent = new NotificationEvent(this, this.applicationContext.getId(), event.getDestinationService());
        notificationEvent.setEventName(event.getEventName());

        if (Objects.nonNull(event.getEventArguments())) {
            event.getEventArguments().forEach((key, value) -> {
                notificationEvent.arg(key, value);
            });
        }
        applicationContext.publishEvent(notificationEvent);
        return ResponseEntity.ok(true);
    }
}
