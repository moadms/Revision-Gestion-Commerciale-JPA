package ma.enset.revision.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_commandes_clients")
public class CommandeClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long id;

    @Column(name = "num_bon_commande", unique = true, nullable = false, length = 30)
    private String numeroCommande;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_enregistrement")
    private Date dateCommande;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_actuel", length = 35)
    private EtatCommande statut;

    @Column(name = "montant_total_ttc")
    private Double montantTotalTtc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_client_acheteur", nullable = false)
    private ClientAcheteur client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneArticleCommande> lignes = new ArrayList<>();

    public CommandeClient() {
        this.dateCommande = new Date();
        this.montantTotalTtc = 0.0;
        this.statut = EtatCommande.BROUILLON;
    }

    public CommandeClient(String numeroCommande, ClientAcheteur client) {
        this();
        this.numeroCommande = numeroCommande;
        this.client = client;
    }

    public void ajouterLigne(LigneArticleCommande ligne) {
        this.lignes.add(ligne);
        ligne.setCommande(this);
        recalculerMontantTotal();
    }

    public void recalculerMontantTotal() {
        double total = 0.0;
        for (LigneArticleCommande l : lignes) {
            if (l.getSousTotal() != null) {
                total += l.getSousTotal();
            }
        }
        this.montantTotalTtc = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatCommande getStatut() {
        return statut;
    }

    public void setStatut(EtatCommande statut) {
        this.statut = statut;
    }

    public Double getMontantTotalTtc() {
        return montantTotalTtc;
    }

    public void setMontantTotalTtc(Double montantTotalTtc) {
        this.montantTotalTtc = montantTotalTtc;
    }

    public ClientAcheteur getClient() {
        return client;
    }

    public void setClient(ClientAcheteur client) {
        this.client = client;
    }

    public List<LigneArticleCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneArticleCommande> lignes) {
        this.lignes = lignes;
    }

    @Override
    public String toString() {
        return "CommandeClient{" +
                "id=" + id +
                ", numeroCommande='" + numeroCommande + '\'' +
                ", dateCommande=" + dateCommande +
                ", statut=" + statut +
                ", montantTotalTtc=" + montantTotalTtc +
                '}';
    }
}
