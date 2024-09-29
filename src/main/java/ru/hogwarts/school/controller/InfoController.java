package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
public class InfoController {

    @Autowired
    private InfoService infoService;

    @GetMapping("/port")
    public int getPort() {
        return infoService.getPort();
    }
}
