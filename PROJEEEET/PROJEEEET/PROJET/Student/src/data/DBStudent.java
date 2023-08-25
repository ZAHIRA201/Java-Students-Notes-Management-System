package data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBStudent {

    public Boolean verifir() throws Exception{
        List<Integer> liste = new ArrayList<>();
        Connection c = DBConnection.getInstance();
        PreparedStatement stm = c.prepareStatement("SELECT ID_ETUDIANT from student where type ='INSCRIPTION'");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            int idStudent = rs.getInt("ID_ETUDIANT");
            liste.add(idStudent);
        }

        rs.close();
        stm.close();
        List<Integer> liste1 = new ArrayList<>();
        PreparedStatement stm1 = c.prepareStatement("SELECT ID_ETUDIANT from student where type ='REINSCRIPTION'");
        ResultSet rs1 = stm1.executeQuery();
        while (rs1.next()) {
            int idStudent = rs1.getInt("ID_ETUDIANT");
            liste1.add(idStudent);
        }

        rs1.close();
        stm1.close();
        List<Integer> liste2 = new ArrayList<>();
        PreparedStatement stm2 = c.prepareStatement("SELECT id_student from inscription");
        ResultSet rs2 = stm2.executeQuery();
        while (rs2.next()) {
            int idStudent = rs2.getInt("id_student");
            liste2.add(idStudent);
        }

        rs2.close();
        stm2.close();

        boolean tousDifferents = true;
        for (int id : liste) {
            if (liste2.contains(id)) {
                System.out.println("l'etudiant avec l'id: "+ id + " et de type: 'INSCRIPTION' est deja exist dans la BDD");
                tousDifferents = false;
                break;
            }
        }

        boolean tousSimilaire = true;
        for (int id : liste1) {
            if (!liste2.contains(id)) {
                System.out.println("l'etudiant avec l'id: "+ id + " et de type: 'REINSCRIPTION' ne exist pas dans la BDD");
                tousSimilaire = false;
                break;
            }
        }


        boolean verifier_existance = false;
        if (tousDifferents && tousSimilaire) {
            verifier_existance = true;
        }

    return verifier_existance;
    }
    public List<Object[]> getAllValidStudent() throws SQLException, DataBaseException {
        List<Object[]> resultList = new ArrayList<>();
        ResultSet resultSet = null;
        Connection c = DBConnection.getInstance();
        PreparedStatement stm = c.prepareStatement("select ID_ETUDIANT, NOM, PRENOM, CNE, ID_NIVEAU from Student " +
                "where type = 'REINSCRIPTION'");
        resultSet = stm.executeQuery();
        while (resultSet.next()) {
            // Récupérer les valeurs de chaque colonne dans une ligne
            Object col1Value = resultSet.getObject("ID_ETUDIANT");
            Object col2Value = resultSet.getObject("NOM");
            Object col3Value = resultSet.getObject("PRENOM");
            Object col4Value = resultSet.getObject("CNE");
            Object col5Value = resultSet.getObject("ID_NIVEAU");

            // Créer un tableau d'objets contenant les valeurs de la ligne
            Object[] row = new Object[]{col1Value, col2Value, col3Value, col4Value, col5Value};

            // Ajouter le tableau à la liste de résultats
            resultList.add(row);
        }

        return resultList;
    }


    public int trouverIdNiveauParAlias(String Alias) throws Exception {
        int id_niveau = 0;
        Connection c = DBConnection.getInstance();
        PreparedStatement stm = c.prepareStatement("SELECT idNiveau FROM Annexe WHERE alias = ?");
        stm.setString(1, Alias);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            id_niveau = rs.getInt("idNiveau");
        }
        return id_niveau;
    }

    public void AfficherEtudiantsParClass(int id_niveau) throws SQLException, DataBaseException {
        List<Object[]> resultList = new ArrayList<>();
        ResultSet resultSet = null;
        Connection c = DBConnection.getInstance();
        PreparedStatement stm = c.prepareStatement("select * from inscription " +
                "where id_niv =?");
        stm.setInt(1, id_niveau);
        resultSet = stm.executeQuery();
        while (resultSet.next()) {
            // Récupérer les valeurs de chaque colonne dans une ligne
            Object col1Value = resultSet.getObject("id_inscri");
            Object col2Value = resultSet.getObject("annee");
            Object col3Value = resultSet.getObject("type");
            Object col4Value = resultSet.getObject("validation");
            Object col5Value = resultSet.getObject("id_niv");
            Object col6Value = resultSet.getObject("id_student");
            Object col7Value = resultSet.getObject("nom");
            Object col8Value = resultSet.getObject("prenom");
            Object col9Value = resultSet.getObject("cne");

            Object[] row = new Object[]{col1Value, col2Value, col3Value, col4Value, col5Value, col6Value, col7Value, col8Value, col9Value};
            resultList.add(row);
        }
        for (Object[] row : resultList) {
            String[] columnNames = {"id_inscri", "annee", "type", "validation", "id_niv", "id_student", "nom", "prenom", "cne"};
            for (int i = 0; i < row.length; i++) {
                System.out.print(columnNames[i] + ": " + row[i] + " ");
            }
            System.out.println();
        }

    }
    public void ModificationEtudiant(int id_inscription) throws SQLException, DataBaseException {
        Connection c = DBConnection.getInstance();
        PreparedStatement stm = c.prepareStatement("SELECT nom, prenom, cne, id_student FROM inscription WHERE id_inscri = ?");
        stm.setInt(1, id_inscription);
        ResultSet rs = stm.executeQuery();

        Object[] row = new Object[4];
        if (rs.next()) {
            row[0] = rs.getString("nom");
            row[1] = rs.getString("prenom");
            row[2] = rs.getString("cne");
            row[3] = rs.getInt("id_student");
        }
        System.out.println("Vous souhaitez modifier ces informations de cet étudiant : Y/N\n");
        System.out.println("Nom : " + row[0] + ", Prénom : " + row[1] + ", CNE : " + row[2]);
        Scanner sc = new Scanner(System.in);
        String choix = sc.next();
        if (choix.equals("Y")) {
            System.out.println("Entrer le nouveau nom :\n");
            Scanner sc1 = new Scanner(System.in);
            String nv_nom = sc1.next();
            System.out.println("Entrer le nouveau prénom :\n");
            Scanner sc2 = new Scanner(System.in);
            String nv_prenom = sc2.next();
            System.out.println("Entrer le nouveau CNE :\n");
            Scanner sc3 = new Scanner(System.in);
            String nv_cne = sc3.next();
            int count = 0;
            try {
                stm = c.prepareStatement("UPDATE inscription SET nom = ?, prenom = ?, cne = ? WHERE id_inscri = ?");
                stm.setString(1, nv_nom);
                stm.setString(2, nv_prenom);
                stm.setString(3, nv_cne);
                stm.setInt(4, id_inscription);
                count = stm.executeUpdate();

                stm = c.prepareStatement("UPDATE student SET NOM = ?, PRENOM = ?, CNE = ? WHERE ID_ETUDIANT = ?");
                int ID_ETUDIANT = (Integer) row[3];
                stm.setString(1, nv_nom);
                stm.setString(2, nv_prenom);
                stm.setString(3, nv_cne);
                stm.setInt(4, ID_ETUDIANT);
                count += stm.executeUpdate();

                // Vérifier le nombre de lignes affectées par les requêtes
                if (count > 0) {
                    System.out.println("Données de l'étudiant mises à jour avec succès !");
                } else {
                    System.out.println("Aucune donnée mise à jour pour l'étudiant.");
                }
            } finally {
                if (stm != null) {
                    stm.close();
                }
            }

            if (count != 0) {
                LocalDate dateActuelle = LocalDate.now();
                Date date = Date.valueOf(dateActuelle);
                stm = c.prepareStatement("INSERT INTO HistoriqueModifieManuelle (ID_inscription, ancien_CNE, ancien_NOM, ancien_PRENOM, nouveau_CNE, nouveau_NOM, nouveau_PRENOM, date_modification) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                stm.setInt(1, id_inscription);
                stm.setString(2, (String) row[2]);
                stm.setString(3, (String) row[0]);
                stm.setString(4, (String) row[1]);
                stm.setString(5, nv_cne);
                stm.setString(6, nv_nom);
                stm.setString(7, nv_prenom);
                stm.setDate(8, date);
                stm.executeUpdate();
            }
        } else {
            System.out.println("Aucune modification effectuée.");
        }
    }




}

