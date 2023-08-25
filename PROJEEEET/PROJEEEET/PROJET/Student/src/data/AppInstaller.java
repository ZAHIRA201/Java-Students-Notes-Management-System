package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
public class AppInstaller {
    //private static Logger LOGGER = Logger.getLogger(AppInstaller.class);
    public static void run() throws DataBaseException, SQLException {
        Connection con = DBConnection.getInstance();
        Statement stmt = con.createStatement();

        /*String dropTableSQL2 = "DROP TABLE IF EXISTS inscription";
        stmt.executeUpdate(dropTableSQL2);*/
        //String dropTableSQL1 = "DROP TABLE IF EXISTS Student";
        //stmt.executeUpdate(dropTableSQL1);


        String createTableStudent = """
    CREATE TABLE IF NOT EXISTS Student (
        ID_ETUDIANT INT PRIMARY KEY,
        CNE VARCHAR(50) NOT NULL,
        NOM VARCHAR(50) NOT NULL,
        PRENOM VARCHAR(50) NOT NULL,
        ID_NIVEAU INT NOT NULL,
        TYPE VARCHAR(50) NOT NULL
    )
""";
        stmt.executeUpdate(createTableStudent);

        String createTableInscription = """
    CREATE TABLE IF NOT EXISTS inscription (
        id_inscri INT AUTO_INCREMENT,
        annee INT,
        type VARCHAR(255),
        validation VARCHAR(20),
        id_niv INT,
        id_student INT,
        nom VARCHAR(255),
        prenom VARCHAR(255),
        cne VARCHAR(50),                
        PRIMARY KEY (id_inscri)
    )
""";
        stmt.executeUpdate(createTableInscription);

        String createTableVerification = """
    CREATE TABLE IF NOT EXISTS VERIFICATION (
        id_student INT,
        CNE VARCHAR(255),
        NOM VARCHAR(255),
        PRENOM VARCHAR(255),
        PRIMARY KEY (id_student)
    )
""";
        stmt.executeUpdate(createTableVerification);

        String createTableHistoriqueModifieManuelle = """
    CREATE TABLE IF NOT EXISTS HistoriqueModifieManuelle (
        id_MODIFICATION INT AUTO_INCREMENT,   
        ID_inscription INT,
        ancien_CNE VARCHAR(255),
        ancien_NOM VARCHAR(255),
        ancien_PRENOM VARCHAR(255),
        nouveau_CNE VARCHAR(255),
        nouveau_NOM VARCHAR(255),
        nouveau_PRENOM VARCHAR(255),
        date_modification DATE,
        PRIMARY KEY (id_MODIFICATION)
    )
""";
        stmt.executeUpdate(createTableHistoriqueModifieManuelle);

        String createTableHistoriqueModifieAuto = """
    CREATE TABLE IF NOT EXISTS HistoriqueModifieAuto (
        id_MODIFICATION INT AUTO_INCREMENT,   
        id_etudiant INT,
        BDD_CNE VARCHAR(255),
        BDD_NOM VARCHAR(255),
        BDD_PRENOM VARCHAR(255),
        excel_CNE VARCHAR(255),
        excel_NOM VARCHAR(255),
        excel_PRENOM VARCHAR(255),
        date_modification DATE,
        PRIMARY KEY (id_MODIFICATION)
    )
""";
        stmt.executeUpdate(createTableHistoriqueModifieAuto);

        String createTableAnnexe = """
    CREATE TABLE IF NOT EXISTS Annexe (
        idNiveau VARCHAR(50),
        alias VARCHAR(255)              
    )
""";
        stmt.executeUpdate(createTableAnnexe);

        String createTableAllNiveau = """
    CREATE TABLE IF NOT EXISTS TableAllNiveau (
        idNiveau int,
        alias VARCHAR(50),
        semestre VARCHAR(50),
        codeModule VARCHAR(50),
        titre VARCHAR(255),
        element VARCHAR(255),
        enseignant VARCHAR(50),
        cordinnateur VARCHAR(50)
    )
""";
        stmt.executeUpdate(createTableAllNiveau);

        String createTableInscriptionModule = """
    CREATE TABLE IF NOT EXISTS InscriptionModule (
        id INT AUTO_INCREMENT,
        idNiveau INT,
        codeModule VARCHAR(20),
        idInsc INT,
        noteSN VARCHAR(20),
        noteSR VARCHAR(20),
        validation VARCHAR(20),
        PRIMARY KEY (id)
    )
""";
        stmt.executeUpdate(createTableInscriptionModule);

        String createTableInscriptionElement = """
    CREATE TABLE IF NOT EXISTS InscriptionElement (
        id INT AUTO_INCREMENT,
        codeModule VARCHAR(20),
        element VARCHAR(20),
        idInsc INT,
        noteSN INT,
        noteSR INT,
        noteFinale INT,
        validation VARCHAR(20),
        PRIMARY KEY (id)
    )
""";
        stmt.executeUpdate(createTableInscriptionElement);

        String createIndex = "CREATE INDEX id_cod ON TableAllNiveau (codeModule)";
        stmt.executeUpdate(createIndex);

        String alterTableInscriptionModule = """
    ALTER TABLE InscriptionModule
    ADD CONSTRAINT fk_idModule FOREIGN KEY (codeModule)
    REFERENCES TableAllNiveau (codeModule)
""";
        stmt.executeUpdate(alterTableInscriptionModule);

        String creatIndex = "CREATE INDEX id_id ON InscriptionModule (idInsc)";
        stmt.executeUpdate(creatIndex);

        String alterTableInscriptionElement2 = """
    ALTER TABLE InscriptionElement
    ADD CONSTRAINT fk_idinscription FOREIGN KEY (idInsc)
    REFERENCES InscriptionModule (idInsc)
""";
        stmt.executeUpdate(alterTableInscriptionElement2);

        stmt.close();



    }

    public static  boolean checkIfAlreadyInstalled() throws IOException {
        String userHomeDirectory = System.getProperty("user.home");
        Properties dbProperties = DbPropertiesLoader.loadPoperties("conf.properties");
        String dbName = dbProperties.getProperty("db.name");
        String dataBaseFile = userHomeDirectory+"\\"+dbName+".mv.db";
        //LOGGER.debug("End");
        return FileManager.fileExists(dataBaseFile);
    }

}

