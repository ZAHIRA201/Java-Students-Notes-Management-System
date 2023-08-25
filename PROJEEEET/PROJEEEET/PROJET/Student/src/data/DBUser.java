package data;

import bo.Student;
import bo.Utilisateur;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUser {
    /*private static final Logger LOGGER = Logger.getLogger(Utilisateur.class.getName());

    public void modifierDonneesEtudiant(String ID_ETUDIANT, Student student) {
        try {
            // Établir la connexion à la base de données
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "student", "RX6049@loubna");

            // Demander à l'utilisateur les nouvelles valeurs
            Scanner scanner = new Scanner(System.in);
            System.out.print("Nouveau NOM : ");
            String nouveauNOM = scanner.nextLine();
            System.out.print("Nouveau PRENOM: ");
            String nouveauPRENOM = scanner.nextLine();
            System.out.print("Nouveau CNE : ");
            String nouveauCNE = scanner.nextLine();

            // Exécuter la requête SQL UPDATE pour mettre à jour les données de l'étudiant
            String sql = "UPDATE student SET NOM = ?, PRENOM = ?, CNE = ? WHERE ID_ETUDIANT = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nouveauNOM);
            statement.setString(2, nouveauPRENOM);
            statement.setString(3, nouveauCNE);
            statement.setString(4,  ID_ETUDIANT);
            int lignesModifiees = statement.executeUpdate();

            if (lignesModifiees > 0) {
                System.out.println("Données de l'étudiant mises à jour avec succès !");

                // Insérer un enregistrement dans la table d'historique des modifications
                String historiqueSql = "INSERT INTO historique_modifications (ID_ETUDIANT, ancien_NOM, ancien_PRENOM, ancien_CNE, nouveau_NOM, nouveau_PRENOM, nouveau_CNE) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement historiqueStatement = conn.prepareStatement(historiqueSql);
                historiqueStatement.setString(1, ID_ETUDIANT);
                historiqueStatement.setString(2, getNOM());
                historiqueStatement.setString(3, getPRENOM());
                historiqueStatement.setString(4, getCNE());
                historiqueStatement.setString(5, nouveauNOM);
                historiqueStatement.setString(6, nouveauPRENOM);
                historiqueStatement.setString(7, nouveauCNE);
                historiqueStatement.setString(8, student);
                historiqueStatement.setDate(9, new java.sql.Date(new Date().getTime()));
                historiqueStatement.executeUpdate();
                historiqueStatement.close();
            } else {
                System.out.println("Aucun étudiant trouvé avec le  ID_ETUDIANT spécifié.");
            }

            // close ressources
            statement.close();
            conn.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la modification des données de l'étudiant.", e);
        }
    }


    public String toString() {
        return "Utilisateur [ nom=" + NOM + ", prenom=" + PRENOM + ", CNE=" + CNE+ "]";
    }*/
}
