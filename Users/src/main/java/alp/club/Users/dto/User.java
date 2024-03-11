package alp.club.Users.dto;

import alp.club.Users.vao.UserEntity;
import alp.club.Users.vao.UserRole;
import alp.club.Users.vao.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Schema(hidden = true)
    private String id;
    private String name;
    private String surname;
    private String email;
    private UserRole role;
    private UserStatus status;

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.surname = userEntity.getSurname();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
        this.status = userEntity.getStatus();
    }
}
