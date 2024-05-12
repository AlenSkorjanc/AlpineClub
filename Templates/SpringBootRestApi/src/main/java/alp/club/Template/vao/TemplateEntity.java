package alp.club.Template.vao;

import alp.club.Template.dto.TemplateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Template")
public class TemplateEntity {
    @Id
    private String id;

    public TemplateEntity(TemplateDto templateDto) {
        this.id = templateDto.getId();
    }
}
