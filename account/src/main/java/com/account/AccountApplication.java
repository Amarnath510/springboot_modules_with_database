package com.account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;

@RestController
@SpringBootApplication(scanBasePackages = "com")
@EntityScan("com.*") // This is needed if we use @Entity annotation for POJOs.
@RequestMapping("/account")
public class AccountApplication {

    private static final User EMPTY_USER = new User();

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/hello")
    @GetMapping
    public String sayHello() {
        return "Acccount: Hello!";
    }

    /*
        URL: POST: localhost:8100/account/user/add
        Input Body: {
                        "name": "Amarnath",
                        "email": "amarnath@email.com"
                    }
     */
    @RequestMapping(value = "/user/add",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE
                    )
    public User addUser(@RequestBody User user) {
        return accountRepository.save(user);
    }

    @RequestMapping(value = "/user/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Iterable<User> allUser() {
        return accountRepository.findAll();
    }

    @RequestMapping(value = "/user/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User getUser(@PathVariable("id") Long id) {
        Optional<User> user = accountRepository.findById(id);
        if (user.isPresent()) {
            user.get();
        }
        return EMPTY_USER;
    }

    @RequestMapping(value = "/user/delete/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteUser(@PathVariable("id") Long id) {
        accountRepository.deleteById(id);
    }

    public static void main(String[] args) { SpringApplication.run(AccountApplication.class, args); }
}
