package org.spboot.spboot.controller;


import org.spboot.spboot.model.User;
import org.spboot.spboot.properties.AppProperties;
import org.spboot.spboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class DemoController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppProperties appProperties;

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user){
        user.setId(id);
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return "User deleted";
    }



    @GetMapping("/name")
    public String getName() {

        String[] appName = appProperties.getName();

        if (appName.length > 0) {
            return "SpringBootApplicationNames are: "
                    + String.join(", ", appName);
        }

        return "No Data Available in Appname";
    }

    @GetMapping("/version")
    public String getVersion(){
        return "SpringBootApplicationVersion is: " + appProperties.getVersion();
    }

    @GetMapping("/date")
    public String getDate() {

        // 1. Get format from properties
        String format = appProperties.getDateFormat();

        // 2. Create formatter dynamically
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        // 3. Parse string date from properties
        LocalDate date = LocalDate.parse(appProperties.getDate(), formatter);

        // 4. Return formatted output
        return "Formatted Date: " + date.format(formatter)
                + " | Format used: " + format;
    }


}