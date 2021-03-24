package my.test.dixa.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrimeEndpoint {

    @Autowired
    private PrimeService service;

    @GetMapping("/prime/number/{number}")
    ResponseEntity<List<Integer>> primeNumber(@PathVariable("number") int number) {
        return ResponseEntity.ok(service.primeNumber(number));
    }

}
