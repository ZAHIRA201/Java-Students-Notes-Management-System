package data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

public class FichierExcelModuleDao {
    public void AddFiliereModule(List<List<Object>> dataFromExcel) throws DataBaseException, SQLException {
        Connection c = DBConnection.getInstance();

        try (PreparedStatement truncateStm = c.prepareStatement("DELETE FROM TableAllNiveau")) {
            truncateStm.executeUpdate();
        }

        // Insérer les nouvelles données dans la table
        try (PreparedStatement insertStm = c.prepareStatement("INSERT INTO TableAllNiveau (idNiveau, alias, semestre, codeModule, titre, element, enseignant, cordinnateur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            for (List<Object> rowData : dataFromExcel) {
                String idNiveau = String.valueOf(rowData.get(0));
                String nomNiveau = (String) rowData.get(1);
                String niveau = (String) rowData.get(2);
                String semestre = (String) rowData.get(3);
                String codeModule = (String) rowData.get(4);
                String module = (String) rowData.get(5);
                String element = (String) rowData.get(6);
                String enseignant = (String) rowData.get(7);


                insertStm.setString(1, idNiveau);
                insertStm.setString(2, nomNiveau);
                insertStm.setString(3, niveau);
                insertStm.setString(4, semestre);
                insertStm.setString(5, codeModule);
                insertStm.setString(6, module);
                insertStm.setString(7, element);
                insertStm.setString(8, enseignant);

                insertStm.executeUpdate();
            }
        }

        c.close();
    }


    public void telechargerStructure() {
        String tableName = "TableAllNiveau";
        String excelFilePath = "StructureFiliere.xlsx";

        try (Connection c = DBConnection.getInstance();
             PreparedStatement distinctNiveauxStm = c.prepareStatement("SELECT DISTINCT idNiveau FROM " + tableName + " WHERE idNiveau IS NOT NULL");
             ResultSet distinctNiveauxResultSet = distinctNiveauxStm.executeQuery()) {

            Workbook workbook = new XSSFWorkbook();

            while (distinctNiveauxResultSet.next()) {
                String ID = distinctNiveauxResultSet.getString("idNiveau");
                Sheet sheet = workbook.createSheet(" " + ID);

                PreparedStatement selectNiveauStm = c.prepareStatement("SELECT * FROM " + tableName + " WHERE idNiveau = ?");
                selectNiveauStm.setString(1, ID);
                ResultSet niveauResultSet = selectNiveauStm.executeQuery();

                // Créer l'en-tête du tableau Excel
                Row headerRow = sheet.createRow(0);
                ResultSetMetaData metaData = niveauResultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Cell cell = headerRow.createCell(i - 1);
                    cell.setCellValue(columnName);

                    // Ajuster la taille des cellules de l'en-tête
                    sheet.autoSizeColumn(i - 1);
                }

                // Insérer les données dans le tableau Excel
                int rowNumber = 1;
                while (niveauResultSet.next()) {
                    Row dataRow = sheet.createRow(rowNumber++);
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = niveauResultSet.getObject(i);
                        Cell cell = dataRow.createCell(i - 1);
                        if (value != null) {
                            if (value instanceof String) {
                                cell.setCellValue((String) value);
                            } else if (value instanceof Integer) {
                                cell.setCellValue((Integer) value);
                            } else if (value instanceof Double) {
                                cell.setCellValue((Double) value);
                            } // Ajoutez d'autres types de données selon vos besoins
                        }
                    }
                }

                // Ajuster la taille des cellules de données
                for (int i = 0; i < columnCount; i++) {
                    sheet.autoSizeColumn(i);
                }

                niveauResultSet.close();
                selectNiveauStm.close();
            }

            // Enregistrement du fichier Excel
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Exportation des données vers Excel terminée avec succès.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            throw new RuntimeException(e);
        }
    }

}




