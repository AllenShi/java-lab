package io.allenshi.ghaze.controller;

import io.allenshi.ghaze.dto.Task;
import io.allenshi.ghaze.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BatchServiceController {

    @Autowired
    BatchService batchService;

    @PostMapping(path =  "/tasks", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> submitTask(@RequestBody Task task) {
        batchService.doBatchTask(task);
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }


}
