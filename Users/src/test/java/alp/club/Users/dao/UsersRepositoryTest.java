package alp.club.Users.dao;

import alp.club.Users.vao.UserEntity;
import alp.club.Users.vao.UserRole;
import alp.club.Users.vao.UserStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private UserEntity createUser() {
        UserEntity user = new UserEntity();
        user.setName("Test");
        user.setSurname("User");
        user.setEmail("test-user-email@unit-test.si");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.TRAINEE);
        user.setCreated(new Date());
        return user;
    }

    @Test
    @Order(0)
    public void insertUserEntityTest() {
        UserEntity newUser = createUser();
        UserEntity userEntity = usersRepository.insert(newUser);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getId()).isNotNull();
        assertThat(userEntity.getEmail()).isEqualTo(newUser.getEmail());
    }
    
    @Test
    @Order(1)
    public void saveUserEntityTest() {
        UserEntity userEntity = createUser();
        Optional<UserEntity> existingUser = usersRepository.findUserEntityByEmail(userEntity.getEmail());
        assertThat(existingUser).isPresent();
        assertThat(existingUser.get().getEmail()).isEqualTo(userEntity.getEmail());

        UserEntity updatedUser = new UserEntity(existingUser.get());
        updatedUser.setUpdated(new Date());

        UserEntity updatedUserResponse = usersRepository.save(updatedUser);
        assertThat(updatedUserResponse).isNotNull();
        assertThat(updatedUserResponse.getId()).isEqualTo(existingUser.get().getId());
        assertThat(updatedUserResponse.getUpdated()).isEqualTo(updatedUser.getUpdated());
        assertThat(updatedUserResponse.getUpdated()).isNotEqualTo(existingUser.get().getUpdated());
    }

    @Test
    @Order(2)
    public void findUserEntityTest() {
        List<UserEntity> users = usersRepository.findAll();
        assertThat(users).isNotNull();
        assertThat(users.size()).isGreaterThanOrEqualTo(1);

        UserEntity userEntity = createUser();
        Optional<UserEntity> userEntityByEmail = usersRepository.findUserEntityByEmail(userEntity.getEmail());
        assertThat(userEntityByEmail).isPresent();
        assertThat(userEntityByEmail.get().getEmail()).isEqualTo(userEntity.getEmail());

        Optional<UserEntity> userEntityById = usersRepository.findById(userEntityByEmail.get().getId());
        assertThat(userEntityById).isPresent();
        assertThat(userEntityById.get().getId()).isEqualTo(userEntityByEmail.get().getId());
    }

    @Test
    @Order(3)
    public void deleteUserEntityTest() {
        UserEntity userEntity = createUser();
        Optional<UserEntity> userEntityByEmail = usersRepository.findUserEntityByEmail(userEntity.getEmail());

        assertThat(userEntityByEmail).isPresent();
        assertThat(userEntityByEmail.get().getEmail()).isEqualTo(userEntity.getEmail());

        usersRepository.deleteById(userEntityByEmail.get().getId());

        Optional<UserEntity> deletedUser = usersRepository.findById(userEntityByEmail.get().getId());

        assertThat(deletedUser).isNotPresent();
    }
}
