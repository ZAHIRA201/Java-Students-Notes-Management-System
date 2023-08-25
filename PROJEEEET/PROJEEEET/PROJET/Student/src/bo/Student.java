package bo;

import java.util.*;

public class Student  {
    private int id;
    private String cne;
    private String nom;
    private String prenom;
    private int idNiveau;
    private String type;

    //private List<InscriptionAnnuelle> inscriptions;
    public Student(String cne, String nom, String prenom) {
        this.cne = cne;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Student(int id, String cne, String nom, String prenom, int idNiveau, String type) {
        this.id = id;
        this.cne = cne;
        this.nom = nom;
        this.prenom = prenom;
        this.idNiveau = idNiveau;
        this.type = type;

    }


    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    /*public List<InscriptionAnnuelle> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<InscriptionAnnuelle> inscriptions) {
        this.inscriptions = inscriptions;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getIdNiveau() {
        return idNiveau;
    }

    public void setIdNiveau(int idNiveau) {
        this.idNiveau = idNiveau;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   /* @Override
   public String toString() {
        return "Etudiant [cne=" + cne + ", inscriptions=" + inscriptions + "]";
    }

    public static void insert(){

    }*/

}
