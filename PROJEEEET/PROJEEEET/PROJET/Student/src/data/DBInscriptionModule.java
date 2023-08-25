package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBInscriptionModule {



    public static int[][] getPair() throws DataBaseException, SQLException {
        Connection c = DBConnection.getInstance();
        PreparedStatement stm = c.prepareStatement("SELECT id_niv, id_inscri FROM Inscription GROUP BY id_niv, id_inscri;");
        ResultSet rs = stm.executeQuery();
        int deuxId[][] = new int[100][2];

        int index = 0;
        while (rs.next() && index < 100) {
            deuxId[index] = new int[]{rs.getInt("id_niv"), rs.getInt("id_inscri")};
            index++;
        }
        return deuxId;
    }

    public static void ajoutModules() throws SQLException, DataBaseException {
        Connection c = DBConnection.getInstance();

        PreparedStatement stm = c.prepareStatement("SELECT idNiveau, codeModule, element FROM TableAllNiveau");
        ResultSet rs = stm.executeQuery();
        Object listModuleElement[][] = new Object[100][3];

        int index = 0;
        while (rs.next() && index < 100) {
            listModuleElement[index] = new Object[]{
                    rs.getInt("idNiveau"),
                    rs.getString("codeModule"),
                    rs.getString("element")
            };
            index++;
        }

        for (int i = 0; i < index; i++) {
            PreparedStatement stm3 = c.prepareStatement("INSERT INTO InscriptionModule (idNiveau, codeModule, idInsc) VALUES (?, ?, ?)");
            stm3.setInt(1, (int) listModuleElement[i][0]);
            stm3.setString(2, (String) listModuleElement[i][1]);
            stm3.setInt(3, getPair()[i][1]);
            stm3.executeUpdate();

            PreparedStatement stm4 = c.prepareStatement("INSERT INTO InscriptionElement (codeModule, element, idInsc) VALUES (?, ?, ?)");
            stm4.setString(1, (String) listModuleElement[i][1]);
            stm4.setString(2, (String) listModuleElement[i][2]);
            stm4.setInt(3, getPair()[i][1]);
            stm4.executeUpdate();
        }
    }
}
