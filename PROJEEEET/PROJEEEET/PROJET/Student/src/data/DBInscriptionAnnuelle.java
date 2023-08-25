package data;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBInscriptionAnnuelle {
    public void addIntoStudent(List<List<Object>> datainsr) throws Exception {
        Connection c = DBConnection.getInstance();
        PreparedStatement stm1 = c.prepareStatement("DELETE FROM STUDENT");
        stm1.executeUpdate();

        PreparedStatement stm = c.prepareStatement("INSERT INTO Student VALUES (?, ?, ?, ?, ?, ?)");

        for (List<Object> rowData : datainsr) {

            int id = ((Double) rowData.get(0)).intValue();
            String cne = (String) rowData.get(1);
            String nom = (String) rowData.get(2);
            String prenom = (String) rowData.get(3);
            int idNiveau = ((Double) rowData.get(4)).intValue();
            String type = (String) rowData.get(5);

            stm.setInt(1, id);
            stm.setString(2, cne);
            stm.setString(3, nom);
            stm.setString(4, prenom);
            stm.setInt(5, idNiveau);
            stm.setString(6, type);
            stm.executeUpdate();

        }
        stm.close();

    }
    public void addIntoInscription(List<List<Object>> datainsr) throws Exception {
        Connection c = DBConnection.getInstance();
        PreparedStatement stm1 = c.prepareStatement("INSERT INTO inscription (annee, type, validation, id_niv, id_student, nom, prenom, cne) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        String validation = null;
        LocalDate date =LocalDate.now();
        int annee = date.getYear();

        for (List<Object> rowData : datainsr){
            int id = ((Double) rowData.get(0)).intValue();
            String cne = (String) rowData.get(1);
            String nom = (String) rowData.get(2);
            String prenom = (String) rowData.get(3);
            int idNiveau = ((Double) rowData.get(4)).intValue();
            String type = (String) rowData.get(5);
            if(type.equals("INSCRIPTION")){
                stm1.setInt(1, annee);
                stm1.setString(2, type);
                stm1.setString(3, validation);
                stm1.setInt(4, idNiveau);
                stm1.setInt(5, id);
                stm1.setString(6, nom);
                stm1.setString(7, prenom);
                stm1.setString(8, cne);
                stm1.executeUpdate();

        }


    }stm1.close();}


        public  void addNew(Object idNiveau, Object id_etudiant) throws Exception {
            LocalDate date = LocalDate.now();
            int year = date.getYear();
            Connection c = DBConnection.getInstance();
            PreparedStatement stm = c.prepareStatement("update inscription" +
                    " set validation=Null, id_niv=?, annee=? where id_student=?");
            stm.setObject(1, idNiveau);
            stm.setInt(2, year);
            stm.setObject(3, id_etudiant);
            stm.executeUpdate();
            stm.close();
        }
        public  List<Object[]> getAllValid() throws DataBaseException, SQLException {
            List<Object[]> resultList1 = new ArrayList<>();
            ResultSet resultSet1 = null;
            Connection c = DBConnection.getInstance();
            PreparedStatement stm = c.prepareStatement("select id_student, nom, prenom, cne, id_niv from inscription " +
                    " where validation = 'V' and type = 'REINSCRIPTION'");

            resultSet1 = stm.executeQuery();
            while (resultSet1.next()) {
                // Récupérer les valeurs de chaque colonne dans une ligne
                Object col1Value = resultSet1.getObject("id_student");
                Object col2Value = resultSet1.getObject("nom");
                Object col3Value = resultSet1.getObject("prenom");
                Object col4Value = resultSet1.getObject("cne");
                Object col5Value = resultSet1.getObject("id_niv");

                // Créer un tableau d'objets contenant les valeurs de la ligne
                Object[] row = new Object[]{col1Value, col2Value, col3Value, col4Value, col5Value};

                // Ajouter le tableau à la liste de résultats
                resultList1.add(row);
            }
            return resultList1;
        }


        public void updateAttribut(Object id_student, Object nom, Object prenom, Object CNE) throws DataBaseException, SQLException {
            Connection c = DBConnection.getInstance();
            PreparedStatement stm = c.prepareStatement("UPDATE inscription SET nom=?, prenom=?, cne=? WHERE id_student=?");
            try {
                stm.setObject(1, nom);
                stm.setObject(2, prenom);
                stm.setObject(3, CNE);
                stm.setObject(4, id_student);
                stm.executeUpdate();
            } finally {
                stm.close();
            }
        }
        public void autoHistorique(int id_student, String nom_excel, String prenom_excel, String cne_excel, String nom_BDD, String prenom_BDD, String cne_BDD)throws DataBaseException, SQLException{
            Connection c = DBConnection.getInstance();
            LocalDate dateActuelle = LocalDate.now();
            Date date = Date.valueOf(dateActuelle);
            PreparedStatement stm = c.prepareStatement("INSERT INTO HistoriqueModifieAuto (id_etudiant, BDD_CNE, BDD_NOM, BDD_PRENOM, excel_CNE, excel_NOM, excel_PRENOM, date_modification) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setInt(1, id_student);
            stm.setString(2, nom_excel);
            stm.setString(3, prenom_excel);
            stm.setString(4, cne_excel);
            stm.setString(5, nom_BDD);
            stm.setString(6, prenom_BDD);
            stm.setString(7, cne_BDD);
            stm.setDate(8, date);
            stm.executeUpdate();

        }



}

