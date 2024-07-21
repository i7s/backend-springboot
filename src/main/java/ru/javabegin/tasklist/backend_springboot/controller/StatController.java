package ru.javabegin.tasklist.backend_springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.tasklist.backend_springboot.entity.Stat;
import ru.javabegin.tasklist.backend_springboot.service.StatService;
import ru.javabegin.tasklist.backend_springboot.util.MyLogger;

@RestController
public class StatController {

    private final StatService statService;
    private final Long defaultId = 1L;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/stat")
    public ResponseEntity<Stat> findById() {
        MyLogger.showMethodName("StatController: findById()");
        return ResponseEntity.ok(statService.findById(defaultId));
    }

}
