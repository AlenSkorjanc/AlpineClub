package alp.club.Users.rest;

import alp.club.Users.dao.UsersRepository;
import alp.club.Users.dto.User;
import alp.club.Users.vao.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<UserEntity> userEntities = usersRepository.findAll();
        List<User> users = new ArrayList<>();
        for(UserEntity userEntity : userEntities) {
            users.add(new User(userEntity));
        }

        logger.info("Getting all users");
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<UserEntity> userEntity = usersRepository.findById(id);
        if(userEntity.isPresent()) {
            User user = new User(userEntity.get());
            logger.info("Getting user by id '" + id + "'");
            return ResponseEntity.ok(user);
        }

        logger.error("Getting user by id '" + id + "' failed, user doesn't exist");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if(user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
            logger.error("Can't create user, because data is missing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> existingUser = usersRepository.findUserEntityByEmail(user.getEmail());
        if(existingUser.isPresent()) {
            logger.error("Can't create user, because user with email '" + user.getEmail() + "' already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        logger.info("Creating user with email '" + user.getEmail() + "'");
        UserEntity userEntity = new UserEntity(user);
        userEntity.setCreated(new Date());
        UserEntity createdUser = usersRepository.insert(userEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).body("Successfully created user!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String id) {
        if(user == null || id == null || id.isEmpty()) {
            logger.error("Can't update user, because data is missing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> userEntityOptional = usersRepository.findById(id);
        if(userEntityOptional.isEmpty()) {
            logger.error("Can't update user, because user with id '" + id + "' doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = userEntityOptional.get();
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setEmail(user.getEmail());
        userEntity.setStatus(user.getStatus());
        userEntity.setRole(user.getRole());
        userEntity.setUpdated(new Date());

        UserEntity updatedUser = usersRepository.save(userEntity);
        logger.info("Updated user with id '" + id + "'");
        return ResponseEntity.ok(new User(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        if(id == null || id.isEmpty()) {
            logger.error("Can't delete user, because id is not provided");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        usersRepository.deleteById(id);
        logger.info("User with id '" + id + "' successfully deleted");
        return ResponseEntity.ok("User with id '" + id + "' successfully deleted");
    }
}
