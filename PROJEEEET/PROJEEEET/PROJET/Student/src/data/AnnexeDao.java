package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnnexeDao {
    public void AfficheAnnexe() throws DataBaseException, SQLException {
        Connection c = DBConnection.getInstance();


        String selectQuery = "SELECT DISTINCT idNiveau, alias FROM TableAllNiveau";
        PreparedStatement selectStatement = c.prepareStatement(selectQuery);
        ResultSet resultSet = selectStatement.executeQuery();

        String dropQuery = "DELETE FROM ANNEXE";
        PreparedStatement dropStatement = c.prepareStatement(dropQuery);
        dropStatement.execute();

        String insertQuery = "INSERT INTO Annexe (idNiveau, alias) VALUES (?, ?)";
        PreparedStatement insertStatement = c.prepareStatement(insertQuery);
        while (resultSet.next()) {
            String idNiveau = resultSet.getString("idNiveau");
            String alias = resultSet.getString("alias");
            insertStatement.setString(1, idNiveau);
            insertStatement.setString(2, alias);
            insertStatement.executeUpdate();
        }

        // Afficher le contenu de la table "Annexe"
        String selectAllQuery = "SELECT * FROM Annexe";
        PreparedStatement selectAllStatement = c.prepareStatement(selectAllQuery);
        ResultSet annexeResultSet = selectAllStatement.executeQuery();

        // Parcourir les résultats et afficher les données
        System.out.println("Voici les alias des niveaux ainsi que leur identifiant :");
        while (annexeResultSet.next()) {
            int idNiveau = annexeResultSet.getInt("idNiveau");
            String alias_niveau = annexeResultSet.getString("alias");

            System.out.println("id_niveau: " + idNiveau + ", alias: " + alias_niveau);
        }

        // Fermer les ressources
        resultSet.close();
        selectStatement.close();
        insertStatement.close();
        annexeResultSet.close();
        selectAllStatement.close();
        c.close();
    }

}
