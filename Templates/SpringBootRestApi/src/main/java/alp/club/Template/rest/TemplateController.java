package alp.club.Template.rest;

import alp.club.Template.dao.TemplateRepository;
import alp.club.Template.dto.TemplateDto;
import alp.club.Template.vao.TemplateEntity;
import com.bugsnag.Bugsnag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/template")
public class TemplateController {

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    private final TemplateRepository templateRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Bugsnag bugsnag;

    @GetMapping
    public ResponseEntity<List<TemplateDto>> getAllEntities() {
        List<TemplateEntity> allEntities = templateRepository.findAll();
        List<TemplateDto> entities = new ArrayList<>();
        for(TemplateEntity entity : allEntities) {
            entities.add(new TemplateDto(entity));
        }

        logger.info("Getting all entities");
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateDto> getEntityById(@PathVariable String id) {
        Optional<TemplateEntity> entity = templateRepository.findById(id);
        if(entity.isPresent()) {
            TemplateDto entityResult = new TemplateDto(entity.get());
            logger.info("Getting by id '" + id + "'");
            return ResponseEntity.ok(entityResult);
        }

        logger.error("Getting by id '" + id + "' failed, entity doesn't exist");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addEntity(@RequestBody TemplateDto entity) {
        if(entity == null) {
            logger.error("Can't create, because data is missing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TemplateEntity createdEntity = templateRepository.insert(new TemplateEntity(entity));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEntity.getId())
                .toUri();

        return ResponseEntity.created(location).body("Successfully created entity!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateDto> updateEntity(@RequestBody TemplateDto updatedEntity, @PathVariable String id) {
        if(updatedEntity == null || id == null || id.isEmpty()) {
            logger.error("Can't update, because data is missing");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<TemplateEntity> entityOptional = templateRepository.findById(id);
        if(entityOptional.isEmpty()) {
            logger.error("Can't update, because id '" + id + "' doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TemplateEntity entity = entityOptional.get();
        // set properties of entity

        TemplateEntity result = templateRepository.save(entity);
        logger.info("Updated entity with id '" + id + "'");
        return ResponseEntity.ok(new TemplateDto(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntity(@PathVariable String id) {
        if(id == null || id.isEmpty()) {
            logger.error("Can't delete, because id is not provided");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        templateRepository.deleteById(id);
        logger.info("Entity with id '" + id + "' successfully deleted");
        return ResponseEntity.ok("Entity with id '" + id + "' successfully deleted");
    }

    @GetMapping("/test-bugsnag")
    public ResponseEntity<String> testBugsnag() {
        if(bugsnag.notify(new RuntimeException("Test error from " + applicationContext.getApplicationName() + " microservice"))) {
            return ResponseEntity.ok("Bugsnag working!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bugsnag not working!");
        }
    }
}
