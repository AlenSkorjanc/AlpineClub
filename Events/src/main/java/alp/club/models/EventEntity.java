package alp.club.models;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class EventEntity {
    private ObjectId id;
    private String userId;
    private String name;
    private String description;
    private Long start;
    private Long end;
}
