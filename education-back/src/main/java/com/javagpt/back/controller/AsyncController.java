package com.javagpt.back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {

    @GetMapping("/async")
    public DeferredResult<String> handleAsyncRequest() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        // Simulate an async process
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate delay
                deferredResult.setResult("Async response");
            } catch (InterruptedException e) {
                deferredResult.setErrorResult("Error in async processing");
            }
        }).start();
        return deferredResult;
    }
}