package ma.enset.revision;

import ma.enset.revision.model.ArticleCatalogue;
import ma.enset.revision.model.ClientAcheteur;
import ma.enset.revision.service.GestionCommercialeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GestionCommercialeTest {
    private static EntityManagerFactory emf;
    private static GestionCommercialeService service;

    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("revision_gestion_commerciale");
        service = new GestionCommercialeService(emf);
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null && emf.isOpen()) emf.close();
    }

    @Test
    public void testEnregistrerClient() {
        ClientAcheteur client = new ClientAcheteur("CLI-100", "Moad Moussaoui", "moad@gmail.com", "0612345678", "Casablanca");
        service.enregistrerClient(client);
        assertNotNull(client.getId());

        ClientAcheteur found = service.trouverClient(client.getId());
        assertNotNull(found);
        assertEquals("Moad Moussaoui", found.getNomComplet());
    }

    @Test
    public void testEnregistrerArticle() {
        ArticleCatalogue art = new ArticleCatalogue("REF-PROD-99", "Station Travail IA", 18500.0, 10);
        service.enregistrerArticle(art);
        assertNotNull(art.getId());

        List<ArticleCatalogue> articles = service.listerTousLesArticles();
        assertFalse(articles.isEmpty());
    }
}
