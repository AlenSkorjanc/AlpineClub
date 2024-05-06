package alp.club.Users.vao;

import alp.club.Users.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private UserRole role;
    private UserStatus status;
    private Date created;
    private Date updated;

    public UserEntity(User user) {
        name = user.getName();
        surname = user.getSurname();
        email = user.getEmail();
        role = user.getRole();
        status = user.getStatus();
        password = user.getPassword();
    }

    public UserEntity(UserEntity source) {
        this.id = source.getId();
        this.name = source.getName();
        this.surname = source.getSurname();
        this.email = source.getEmail();
        this.role = source.getRole();
        this.status = source.getStatus();
        this.created = source.getCreated();
        this.updated = source.getUpdated();
    }
}
