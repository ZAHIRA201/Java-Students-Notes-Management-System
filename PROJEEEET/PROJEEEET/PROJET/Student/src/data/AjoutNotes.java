package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjoutNotes {
    //entete[codemodule, session, semestre, niveau, anee, enseignant]
    //data1normal[idstudent, cne, nom, prenom, element1, titre]

    public static void ajout2ElementsSN(Object[] entete, Object[][] data2Normal) throws DataBaseException, SQLException {
        Connection con = DBConnection.getInstance();
        PreparedStatement stm = con.prepareStatement("UPDATE InscriptionElement E " +
                "JOIN InscriptionAnnuelle A ON E.idInsc = A.idInscri" +
                "JOIN InscriptionModule M ON E.codeModule= M.codeModule" +
                "SET E.noteSRN =?" +
                "and M.codeModule=?"+
                "WHERE E.idNiveau=?" +
                "and A.id_student =?" +
                "and E.element=? "
        );

        for (int i = 0; i < data2Normal.length; i++) {
            stm.setObject(1, data2Normal[i][4]); // noteSN
            stm.setObject(2, entete[0]); // codeModule
            stm.setObject(3, entete[3]); // idNiveau
            stm.setObject(4, data2Normal[i][0]); // id_student
            stm.setObject(5, data2Normal[0][5]);//titre de l'element1
            stm.executeUpdate();

        }

        //update pour l'element2
        PreparedStatement stm1 = con.prepareStatement("UPDATE InscriptionElement E " +
                "JOIN InscriptionAnnuelle A ON E.idInsc = A.idInscri" +
                "JOIN InscriptionModule M ON E.codeModule= M.codeModule" +
                "SET E.noteSN =?" +
                "and M.codeModule=?"+
                "WHERE E.idNiveau=?" +
                "and A.id_student =?" +
                "and E.element=? "
        );

        for (int i = 0; i < data2Normal.length; i++) {
            stm1.setObject(1, data2Normal[i][4]); // noteSN
            stm1.setObject(2, entete[0]); // codeModule
            stm1.setObject(3, entete[3]); // idNiveau
            stm1.setObject(4, data2Normal[i][0]); // id_student
            stm1.setObject(5, data2Normal[0][5]);//titre de l'element2
            stm1.executeUpdate();

        }
    }

    //pour la session ratt
    public static void ajout2ElementsSR(Object[] entete, Object[][] data2Ratt) throws DataBaseException, SQLException {
        Connection con = DBConnection.getInstance();
        PreparedStatement stm = con.prepareStatement("UPDATE InscriptionElement E " +
                "JOIN InscriptionAnnuelle A ON E.idInsc = A.idInscri" +
                "JOIN InscriptionModule M ON E.codeModule= M.codeModule" +
                "SET E.noteSR =?" +
                "and M.codeModule=?"+
                "WHERE E.idNiveau=?" +
                "and A.id_student =?" +
                "and E.element=? "
        );

        for (int i = 0; i < data2Ratt.length; i++) {
            stm.setObject(1, data2Ratt[i][4]); // noteSN
            stm.setObject(2, entete[0]); // codeModule
            stm.setObject(3, entete[3]); // idNiveau
            stm.setObject(4, data2Ratt[i][0]); // id_student
            stm.setObject(5, data2Ratt[0][5]);//titre de l'element1
            stm.executeUpdate();

        }

        //update pour l'element2
        PreparedStatement stm1 = con.prepareStatement("UPDATE InscriptionElement E " +
                "JOIN InscriptionAnnuelle A ON E.idInsc = A.idInscri" +
                "JOIN InscriptionModule M ON E.codeModule= M.codeModule" +
                "SET E.noteSR =?" +
                "and M.codeModule=?"+
                "WHERE E.idNiveau=?" +
                "and A.id_student =?" +
                "and E.element=? "
        );

        for (int i = 0; i < data2Ratt.length; i++) {
            stm1.setObject(1, data2Ratt[i][4]); // noteSN
            stm1.setObject(2, entete[0]); // codeModule
            stm1.setObject(3, entete[3]); // idNiveau
            stm1.setObject(4, data2Ratt[i][0]); // id_student
            stm1.setObject(5, data2Ratt[0][5]);//titre de l'element2
            stm1.executeUpdate();

        }
    }

    public static void ajout1ElelementSN(Object[] entete, Object[][] data1Normal) throws DataBaseException, SQLException {
        Connection con = DBConnection.getInstance();
        PreparedStatement stm = con.prepareStatement("UPDATE InscriptionElement E " +
                "JOIN InscriptionModule M ON E.codeModule = M.codeModule " +
                "JOIN inscription A ON M.idInsc = A.id_inscri " +
                "SET E.noteSN = ? " +
                "WHERE E.codeModule = ? " +
                "AND M.idNiveau = ? " +
                "AND A.id_student = ?");

        for (int i = 0; i < data1Normal.length; i++) {
            stm.setObject(1, data1Normal[i][4]); // noteSN
            stm.setObject(2, entete[0]); // codeModule
            stm.setObject(3, entete[3]); // idNiveau
            stm.setObject(4, data1Normal[i][0]); // id_student

            stm.executeUpdate();
        }
    }




    public static void ajout1ElelementSR(Object[] entete, Object[][] data1ratt) throws DataBaseException, SQLException {
        Connection con = DBConnection.getInstance();
        PreparedStatement stm = con.prepareStatement("UPDATE InscriptionElement E " +
                "JOIN InscriptionModule M ON E.codeModule = M.codeModule " +
                "JOIN inscription A ON M.idInsc = A.id_inscri " +
                "SET E.noteSR = ? " +
                "WHERE E.codeModule = ? " +
                "AND M.idNiveau = ? " +
                "AND A.id_student = ?");

        for (int i = 0; i < data1ratt.length; i++) {
            stm.setObject(1, data1ratt[i][4]); // noteSN
            stm.setObject(2, entete[0]); // codeModule
            stm.setObject(3, entete[3]); // idNiveau
            stm.setObject(4, data1ratt[i][0]); // id_student

            stm.executeUpdate();
        }
        stm.executeUpdate();
    }
}