package alp.club.Template.dto;

import alp.club.Template.vao.TemplateEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDto {
    private String id;

    public TemplateDto(TemplateEntity templateEntity) {
        this.id = templateEntity.getId();
    }
}
