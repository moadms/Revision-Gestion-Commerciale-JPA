package ma.enset.revision.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_articles_catalogue")
public class ArticleCatalogue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    private Long id;

    @Column(name = "reference_sku", unique = true, nullable = false, length = 40)
    private String referenceSku;

    @Column(name = "designation_produit", nullable = false, length = 120)
    private String designation;

    @Column(name = "prix_catalogue_ht", nullable = false)
    private Double prixUnitaireHt;

    @Column(name = "quantite_en_stock")
    private Integer quantiteStock;

    public ArticleCatalogue() {
    }

    public ArticleCatalogue(String referenceSku, String designation, Double prixUnitaireHt, Integer quantiteStock) {
        this.referenceSku = referenceSku;
        this.designation = designation;
        this.prixUnitaireHt = prixUnitaireHt;
        this.quantiteStock = quantiteStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceSku() {
        return referenceSku;
    }

    public void setReferenceSku(String referenceSku) {
        this.referenceSku = referenceSku;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getPrixUnitaireHt() {
        return prixUnitaireHt;
    }

    public void setPrixUnitaireHt(Double prixUnitaireHt) {
        this.prixUnitaireHt = prixUnitaireHt;
    }

    public Integer getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(Integer quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    @Override
    public String toString() {
        return "ArticleCatalogue{" +
                "id=" + id +
                ", referenceSku='" + referenceSku + '\'' +
                ", designation='" + designation + '\'' +
                ", prixUnitaireHt=" + prixUnitaireHt +
                ", quantiteStock=" + quantiteStock +
                '}';
    }
}
