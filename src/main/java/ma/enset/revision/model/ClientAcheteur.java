package ma.enset.revision.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_clients_commerciaux")
public class ClientAcheteur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long id;

    @Column(name = "code_client_unique", unique = true, nullable = false, length = 30)
    private String codeClient;

    @Column(name = "raison_sociale_nom", nullable = false, length = 120)
    private String nomComplet;

    @Column(name = "courriel_pro", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "telephone_fixe")
    private String telephone;

    @Column(name = "ville_residence")
    private String ville;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeClient> commandesPassees = new ArrayList<>();

    public ClientAcheteur() {
    }

    public ClientAcheteur(String codeClient, String nomComplet, String email, String telephone, String ville) {
        this.codeClient = codeClient;
        this.nomComplet = nomComplet;
        this.email = email;
        this.telephone = telephone;
        this.ville = ville;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(String codeClient) {
        this.codeClient = codeClient;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public List<CommandeClient> getCommandesPassees() {
        return commandesPassees;
    }

    public void setCommandesPassees(List<CommandeClient> commandesPassees) {
        this.commandesPassees = commandesPassees;
    }

    @Override
    public String toString() {
        return "ClientAcheteur{" +
                "id=" + id +
                ", codeClient='" + codeClient + '\'' +
                ", nomComplet='" + nomComplet + '\'' +
                ", email='" + email + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
