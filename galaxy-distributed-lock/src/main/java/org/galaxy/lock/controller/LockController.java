package org.galaxy.lock.controller;

import org.galaxy.lock.aop.RepeatSubmit;
import org.galaxy.lock.aop.RepeatSubmitStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lock")
public class LockController {

    @GetMapping("/getLock1")
    @RepeatSubmit(prefix = "galaxy-", repeatSubmitStrategy = RepeatSubmitStrategy.ALL_PARAMETER, limit = 60)
    public String lock1(@RequestParam("name") String name) {
        return "lock".concat("-").concat(name);
    }

}
