package alp.club;

import alp.club.dao.ArticlesRepository;
import alp.club.jms.consumer.ViewsConsumer;
import alp.club.rest.ArticlesResource;
import alp.club.vao.ArticleEntity;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.MediaType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArticlesResourceTest {

    @InjectMocks
    private ArticlesResource articlesResource;

    @Mock
    private ViewsConsumer consumer;

    @Mock
    private ArticlesRepository articlesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllArticles() {
        List<ArticleEntity> sampleArticles = new ArrayList<>();
        sampleArticles.add(new ArticleEntity(new ObjectId(), new ObjectId(), "Title 1", "Content 1", 10, new Date()));
        sampleArticles.add(new ArticleEntity(new ObjectId(), new ObjectId(), "Title 2", "Content 2", 12, new Date()));

        when(articlesRepository.listAllArticles()).thenReturn(Uni.createFrom().item(sampleArticles));

        Uni<List<ArticleEntity>> result = articlesResource.getAllArticles();

        verify(articlesRepository, times(1)).listAllArticles();

        assertEquals(sampleArticles, result.await().indefinitely());
    }
}
