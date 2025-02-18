package space.mkhoavo.graphql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import space.mkhoavo.graphql.entity.User;
import space.mkhoavo.graphql.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> users() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public Optional<User> userByUsername(@Argument String username) {
        return userService.getUserByUsername(username);
    }

    @MutationMapping
    public User createUser(@Argument String username, @Argument String password,
                           @Argument String firstName, @Argument String lastName,
                           @Argument Integer age) {
        User user = User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
        return userService.saveUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument String username, @Argument String password,
                           @Argument String firstName, @Argument String lastName,
                           @Argument Integer age) {
        User updatedUser = User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();
        return userService.updateUser(username, updatedUser);
    }


    @MutationMapping
    public Boolean deleteUser(@Argument String username) {
        userService.deleteUser(username);
        return true;
    }
}
