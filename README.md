# Révision — Gestion Commerciale avec Hibernate & JPA

Projet de synthèse couvrant la conception et l'implémentation d'une application de gestion commerciale complète avec **JPA 2.2** et **Hibernate ORM 5.6** adossé à **H2 Database**.

---

## 1. Modèle Métier & Relations

1. **`ClientAcheteur` (`t_clients_commerciaux`)** :
   - Informations d'identification du client (`codeClient`, `nomComplet`, `email`, `telephone`, `ville`).
   - Relation **One-to-Many** vers `CommandeClient` (`@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)`).

2. **`ArticleCatalogue` (`t_articles_catalogue`)** :
   - Référence SKU (`referenceSku`), désignation du produit (`designation`), prix unitaire hors taxe (`prixUnitaireHt`), quantité en stock (`quantiteStock`).

3. **`CommandeClient` (`t_commandes_clients`)** :
   - Numéro de bon de commande (`numeroCommande`), date d'enregistrement (`dateCommande`), statut (`statut`), montant total calculé (`montantTotalTtc`).
   - Relation **Many-to-One** vers `ClientAcheteur`.
   - Relation **One-to-Many** vers `LigneArticleCommande` avec cascade totale et orphan removal.

4. **`LigneArticleCommande` (`t_lignes_commandes`)** :
   - Quantité commandée (`quantite`), prix unitaire facturé (`prixFacture`), sous-total calculé (`sousTotal`).
   - Relation **Many-to-One** vers `CommandeClient` et vers `ArticleCatalogue`.

---

## 2. Fonctionnalités et Requêtes JPQL

- Persistance en cascade d'une commande complète et de l'ensemble de ses lignes d'articles.
- Recalcul dynamique du montant total TTC de la commande.
- Consultation via JPQL avec `FETCH JOIN` :
  ```java
  em.createQuery("SELECT c FROM CommandeClient c JOIN FETCH c.client JOIN FETCH c.lignes l JOIN FETCH l.article", CommandeClient.class)
  ```
- Calcul du Chiffre d'Affaires global cumulé via fonction d'agrégation `SUM` :
  ```java
  em.createQuery("SELECT SUM(c.montantTotalTtc) FROM CommandeClient c", Double.class).getSingleResult();
  ```

---

## 3. Démarrage et Tests

```bash
mvn clean compile exec:java "-Dexec.mainClass=ma.enset.revision.App"
```
