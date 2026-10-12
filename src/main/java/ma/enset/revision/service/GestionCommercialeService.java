package ma.enset.revision.service;

import ma.enset.revision.dao.AbstractGenericDao;
import ma.enset.revision.model.ArticleCatalogue;
import ma.enset.revision.model.ClientAcheteur;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GestionCommercialeService {
    private final AbstractGenericDao<ClientAcheteur, Long> clientDao;
    private final AbstractGenericDao<ArticleCatalogue, Long> articleDao;

    public GestionCommercialeService(EntityManagerFactory emf) {
        this.clientDao = new AbstractGenericDao<>(emf, ClientAcheteur.class) {};
        this.articleDao = new AbstractGenericDao<>(emf, ArticleCatalogue.class) {};
    }

    public void enregistrerClient(ClientAcheteur client) {
        clientDao.create(client);
    }

    public ClientAcheteur trouverClient(Long id) {
        return clientDao.findById(id);
    }

    public List<ClientAcheteur> listerTousLesClients() {
        return clientDao.findAll();
    }

    public void enregistrerArticle(ArticleCatalogue article) {
        articleDao.create(article);
    }

    public ArticleCatalogue trouverArticle(Long id) {
        return articleDao.findById(id);
    }

    public List<ArticleCatalogue> listerTousLesArticles() {
        return articleDao.findAll();
    }
}
