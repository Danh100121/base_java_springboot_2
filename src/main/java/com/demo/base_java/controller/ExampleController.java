package com.demo.base_java.controller;


import com.demo.base_java.exception.EntityNotFoundException;
import com.demo.base_java.model.TestModel;
import com.demo.base_java.repository.TestModelRepository;
import com.demo.base_java.utils.Response;
import com.demo.base_java.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/example-api")
public class ExampleController {

    private final TestModelRepository testModelRepository;

    @GetMapping("/get-data")
    public ResponseEntity<Response> getDataExamples() {
        return ResponseUtils.ok(testModelRepository.findAll());
    }

    @PostMapping("/create-data/{name}")
    public ResponseEntity<Response> createDataExamples(@PathVariable String name) {
        var testModel = new TestModel();
        testModel.setName(name);
        return ResponseUtils.ok(testModelRepository.save(testModel));
    }

    @PostMapping("/update-data/{id}/name/{name}")
    public ResponseEntity<Response> updateDataExamples(@PathVariable String name, @PathVariable Integer id) {
        var testModel = testModelRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(TestModel.class.toString(), id.toString()));
        testModel.setName(name);
        return ResponseUtils.ok(testModelRepository.save(testModel));
    }
}
