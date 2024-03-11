package alp.club.Users.dao;

import alp.club.Users.vao.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntityByEmail(String email);
}
