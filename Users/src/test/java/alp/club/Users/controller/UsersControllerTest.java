package alp.club.Users.controller;

import alp.club.Users.dao.UsersRepository;
import alp.club.Users.dto.User;
import alp.club.Users.rest.UsersController;
import alp.club.Users.vao.UserEntity;
import alp.club.Users.vao.UserRole;
import alp.club.Users.vao.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersRepository usersRepository;

    private User createUser() {
        User user = new User();
        user.setName("Test");
        user.setSurname("User");
        user.setEmail("test@test.si");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.TRAINEE);
        return user;
    }

    @Test
    public void testGetAllUsersEndpoint() throws Exception {
        User user = createUser();

        given(usersRepository.findAll()).willReturn(List.of(new UserEntity(user)));

        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())));
    }

    @Test
    public void testGetUserEndpoint() throws Exception {
        User user = createUser();
        UserEntity userEntity = new UserEntity(user);
        userEntity.setId(UUID.randomUUID().toString());

        given(usersRepository.findById(userEntity.getId())).willReturn(Optional.of(userEntity));

        mvc.perform(get("/users/{id}", userEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(userEntity.getId())))
                .andExpect(jsonPath("email", is(userEntity.getEmail())));
    }

    @Test
    public void testPostUserEndpoint() throws Exception {
        User user = createUser();
        user.setPassword("test");

        UserEntity userEntity = new UserEntity(user);
        userEntity.setId(UUID.randomUUID().toString());

        given(usersRepository.insert(ArgumentMatchers.any(UserEntity.class))).willReturn(userEntity);

        mvc.perform(post("/users")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));

        given(usersRepository.findUserEntityByEmail(userEntity.getEmail())).willReturn(Optional.of(userEntity));

        mvc.perform(post("/users")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        user.setEmail(null);
        mvc.perform(post("/users")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutUserEndpoint() throws Exception {
        mvc.perform(put("/users/{id}", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        User user = createUser();
        UserEntity userEntity = new UserEntity(user);
        userEntity.setId(UUID.randomUUID().toString());

        mvc.perform(put("/users/{id}", userEntity.getId())
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        given(usersRepository.findById(userEntity.getId())).willReturn(Optional.of(userEntity));
        userEntity.setUpdated(new Date());
        given(usersRepository.save(ArgumentMatchers.any(UserEntity.class))).willReturn(userEntity);

        mvc.perform(put("/users/{id}", userEntity.getId())
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(userEntity.getId())));
    }

    @Test
    public void testDeleteUserEndpoint() throws Exception {
        mvc.perform(delete("/users/{id}", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
