package ma.enset.revision.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_lignes_commandes")
public class LigneArticleCommande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ligne")
    private Long id;

    @Column(name = "quantite_commandee", nullable = false)
    private Integer quantite;

    @Column(name = "prix_unitaire_facture", nullable = false)
    private Double prixFacture;

    @Column(name = "sous_total_ligne")
    private Double sousTotal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_commande", nullable = false)
    private CommandeClient commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_article", nullable = false)
    private ArticleCatalogue article;

    public LigneArticleCommande() {
    }

    public LigneArticleCommande(Integer quantite, Double prixFacture, ArticleCatalogue article) {
        this.quantite = quantite;
        this.prixFacture = prixFacture;
        this.article = article;
        this.sousTotal = quantite * prixFacture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
        if (this.prixFacture != null) {
            this.sousTotal = quantite * this.prixFacture;
        }
    }

    public Double getPrixFacture() {
        return prixFacture;
    }

    public void setPrixFacture(Double prixFacture) {
        this.prixFacture = prixFacture;
        if (this.quantite != null) {
            this.sousTotal = this.quantite * prixFacture;
        }
    }

    public Double getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(Double sousTotal) {
        this.sousTotal = sousTotal;
    }

    public CommandeClient getCommande() {
        return commande;
    }

    public void setCommande(CommandeClient commande) {
        this.commande = commande;
    }

    public ArticleCatalogue getArticle() {
        return article;
    }

    public void setArticle(ArticleCatalogue article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "LigneArticleCommande{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", prixFacture=" + prixFacture +
                ", sousTotal=" + sousTotal +
                '}';
    }
}
