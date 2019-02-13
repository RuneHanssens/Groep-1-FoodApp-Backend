package foodappbackend.controllers;

import foodappbackend.model.TestEntity;
import foodappbackend.repositories.TestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private TestRepository testRepository;

    public HelloController(TestRepository testRepository) {
        this.testRepository = testRepository;
        testRepository.save(new TestEntity("YEEEEEEEEET"));
    }

    @GetMapping("/helloWorld")
    public String helloWorld(){
        return "hello world";
    }

    @GetMapping("/test")
    public Iterable<TestEntity> test(){
        return testRepository.findAll();
    }
}
