package ma.enset.revision;

import ma.enset.revision.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(">>> DEMARRAGE REVISION - GESTION COMMERCIALE AVEC HIBERNATE ET JPA <<<");
        System.out.println("==========================================================================");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("revision_gestion_commerciale");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // [1] CREATION CLIENTS
            System.out.println("\n--- [1] ENREGISTREMENT DES CLIENTS ---");
            ClientAcheteur client1 = new ClientAcheteur("CLI-2026-01", "Moad Moussaoui Solutions", "moussaoui9881@gmail.com", "0600112233", "Casablanca");
            ClientAcheteur client2 = new ClientAcheteur("CLI-2026-02", "Atlas Tech SARL", "contact@atlastech.ma", "0522334455", "Rabat");
            em.persist(client1);
            em.persist(client2);

            // [2] CREATION ARTICLES
            System.out.println("\n--- [2] ENREGISTREMENT DES ARTICLES ---");
            ArticleCatalogue artServeur = new ArticleCatalogue("SRV-DELL-R750", "Serveur Rack Dell PowerEdge R750", 42000.0, 5);
            ArticleCatalogue artSwitch = new ArticleCatalogue("NET-CISCO-C9200", "Switch Cisco Catalyst 9200 48P", 12500.0, 15);
            ArticleCatalogue artOnduleur = new ArticleCatalogue("PWR-APC-3KVA", "Onduleur APC Smart-UPS 3000VA", 7800.0, 20);
            em.persist(artServeur);
            em.persist(artSwitch);
            em.persist(artOnduleur);

            // [3] CREATION COMMANDE AVEC LIGNES (CASCADE PERSIST)
            System.out.println("\n--- [3] CREATION D'UNE COMMANDE COMPLETE ---");
            CommandeClient commande = new CommandeClient("CMD-2026-0001", client1);
            commande.setStatut(EtatCommande.EXPEDIEE);

            LigneArticleCommande ligne1 = new LigneArticleCommande(2, artServeur.getPrixUnitaireHt(), artServeur);
            LigneArticleCommande ligne2 = new LigneArticleCommande(4, artSwitch.getPrixUnitaireHt(), artSwitch);
            commande.ajouterLigne(ligne1);
            commande.ajouterLigne(ligne2);

            em.persist(commande);

            em.getTransaction().commit();
            System.out.println("✓ Commande " + commande.getNumeroCommande() + " enregistrée avec succès. Montant Total TTC: " + commande.getMontantTotalTtc() + " DH");

            // [4] REQUETES COMMERCIALES JPQL
            System.out.println("\n--- [4] CONSULTATION ET STATISTIQUES COMMERCIALES (JPQL) ---");
            List<CommandeClient> commandes = em.createQuery(
                    "SELECT c FROM CommandeClient c JOIN FETCH c.client JOIN FETCH c.lignes l JOIN FETCH l.article",
                    CommandeClient.class
            ).getResultList();

            for (CommandeClient c : commandes) {
                System.out.println("Commande N°: " + c.getNumeroCommande() +
                        " | Client: " + c.getClient().getNomComplet() +
                        " | Statut: " + c.getStatut() +
                        " | Total: " + c.getMontantTotalTtc() + " DH");

                for (LigneArticleCommande l : c.getLignes()) {
                    System.out.println("   * Ligne : " + l.getArticle().getDesignation() +
                            " (Qté: " + l.getQuantite() + " x " + l.getPrixFacture() + " DH = " + l.getSousTotal() + " DH)");
                }
            }

            Double chiffreAffaires = em.createQuery("SELECT SUM(c.montantTotalTtc) FROM CommandeClient c", Double.class).getSingleResult();
            System.out.println("\n>> Chiffre d'Affaires Global Réalisé : " + chiffreAffaires + " DH");

            System.out.println("\n==========================================================================");
            System.out.println(">>> EXECUTION DU PROJET REVISION TERMINEE AVEC SUCCES <<<");
            System.out.println("==========================================================================");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
