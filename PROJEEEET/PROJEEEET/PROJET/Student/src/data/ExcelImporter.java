package data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class ExcelImporter {

    public  ArrayList<ArrayList<String>> d() {
        String nomFichier = "noteparmodule1mood.xlsx";
        ArrayList<ArrayList<String>> donneesExcel = importerDonnees(nomFichier);

        // les données importées

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("noteparmodule1mood.xlsx");
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Sheet sheet = workbook.getSheetAt(0);

        int startRow = 6;
        for (int rowIdx = startRow; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            int numColumns = row.getLastCellNum();

            if (numColumns == 5) {
                // Vérification de la condition 1
                Cell cellCol5 = row.getCell(4);
                if (cellCol5 != null && cellCol5.getCellType() == CellType.NUMERIC) {
                    double value = cellCol5.getNumericCellValue();
                    if (value >= 0.0 && value <= 20.0 && cellCol5.getCellType() != CellType.BLANK) {
                        // La condition 1 est satisfaite
                        System.out.println("Condition 1 : Ligne " + (rowIdx + 1) + ", Colonne 5 est valide.");
                    } else {
                        // La condition 1 n'est pas satisfaite
                        System.out.println("Condition 1 : Ligne " + (rowIdx + 1) + ", Colonne 5 est invalide.");
                    }
                } else {
                    // La condition 1 n'est pas satisfaite
                    System.out.println("Condition 1 : Ligne " + (rowIdx + 1) + ", Colonne 5 est invalide.");
                }
            } else if (numColumns == 6) {
                // Vérification de la condition 2
                Cell cellCol5 = row.getCell(4);
                Cell cellCol6 = row.getCell(5);
                if (cellCol5 != null && cellCol5.getCellType() == CellType.NUMERIC &&
                        cellCol6 != null && cellCol6.getCellType() == CellType.NUMERIC) {
                    double valueCol5 = cellCol5.getNumericCellValue();
                    double valueCol6 = cellCol6.getNumericCellValue();
                    if (valueCol5 >= 0.0 && valueCol5 <= 20.0 && valueCol6 >= 0.0 && valueCol6 <= 20.0 &&
                            cellCol5.getCellType() != CellType.BLANK && cellCol6.getCellType() != CellType.BLANK) {
                        // La condition 2 est satisfaite
                        System.out.println("Condition 2 : Ligne " + (rowIdx + 1) + ", Colonnes 5 et 6 sont valides.");
                    } else {
                        // La condition 2 n'est pas satisfaite
                        System.out.println("Condition 2 : Ligne " + (rowIdx + 1) + ", Colonnes 5 et 6 sont invalides.");
                    }
                } else {
                    // La condition 2 n'est pas satisfaite
                    System.out.println("Condition 2 : Ligne " + (rowIdx + 1) + ", Colonnes 5 et 6 sont invalides.");
                }
            } else if (numColumns == 7) {
                // Vérification de la condition 3
                Cell cellCol5 = row.getCell(4);
                Cell cellCol6 = row.getCell(5);
                Cell cellCol7 = row.getCell(6);
                if (cellCol5 != null && cellCol5.getCellType() == CellType.NUMERIC &&
                        cellCol6 != null && cellCol6.getCellType() == CellType.NUMERIC &&
                        cellCol7 != null && cellCol7.getCellType() == CellType.NUMERIC) {
                    double valueCol5 = cellCol5.getNumericCellValue();
                    double valueCol6 = cellCol6.getNumericCellValue();
                    double valueCol7 = cellCol7.getNumericCellValue();
                    if (valueCol5 >= 0.0 && valueCol5 <= 20.0 && valueCol6 >= 0.0 && valueCol6 <= 20.0 &&
                            valueCol7 >= 0.0 && valueCol7 <= 20.0 &&
                            cellCol5.getCellType() != CellType.BLANK && cellCol6.getCellType() != CellType.BLANK &&
                            cellCol7.getCellType() != CellType.BLANK) {
                        // La condition 3 est satisfaite
                        System.out.println("Condition 3 : Ligne " + (rowIdx + 1) + ", Colonnes 5, 6 et 7 sont valides.");
                    } else {
                        // La condition 3 n'est pas satisfaite
                        System.out.println("Condition 3 : Ligne " + (rowIdx + 1) + ", Colonnes 5, 6 et 7 sont invalides.");
                    }
                } else {
                    // La condition 3 n'est pas satisfaite
                    System.out.println("Condition 3 : Ligne " + (rowIdx + 1) + ", Colonnes 5, 6 et 7 sont invalides.");
                }
            } else {
                // Nombre de colonnes invalide
                System.out.println("Nombre de colonnes invalide pour la ligne " + (rowIdx + 1) + ".");
            }
        }

        try {
            workbook.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            fileInputStream.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        for (ArrayList<String> ligneDonnees : donneesExcel) {
            for (String donnee : ligneDonnees) {
                System.out.print(donnee + " ");
            }
            System.out.println();
        }
        return donneesExcel;
    }


    public static ArrayList<ArrayList<String>> importerDonnees(String nomFichier) {
        ArrayList<ArrayList<String>> donnees = new ArrayList<>();

        try {
            FileInputStream fichier = new FileInputStream(new File(nomFichier));
            Workbook classeur = new XSSFWorkbook(fichier);
            Sheet feuille = classeur.getSheetAt(0); // Première feuille du classeur

            for (int rowIdx = 4; rowIdx <= feuille.getLastRowNum(); rowIdx++) {
                Row ligne = feuille.getRow(rowIdx);
                ArrayList<String> ligneDonnees = new ArrayList<>();

                for (int colIdx = 0; colIdx < ligne.getLastCellNum(); colIdx++) {
                    Cell cellule = ligne.getCell(colIdx);

                    if (cellule != null) {
                        cellule.setCellType(CellType.STRING);
                        ligneDonnees.add(cellule.getStringCellValue());
                    } else {
                        ligneDonnees.add("");
                    }
                }

                donnees.add(ligneDonnees);
            }

            classeur.close();
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return donnees;
    }

    public static String[] importerDonneesSelonConditions(String nomFichier) {
        String[] donnees = new String[6];

        try {
            FileInputStream fichier = new FileInputStream(new File(nomFichier));
            Workbook classeur = new XSSFWorkbook(fichier);
            Sheet feuille = classeur.getSheetAt(0); // Première feuille du classeur

            // Condition 1 : Cellule 2, Ligne 1 dans l'index 0 du tableau
            Cell cellule1 = feuille.getRow(1).getCell(1);
            donnees[0] = getCellValue(cellule1);

            // Condition 2 : Cellule 4, Ligne 2 dans l'index 1 du tableau
            Cell cellule2 = feuille.getRow(2).getCell(3);
            donnees[1] = getCellValue(cellule2);

            // Condition 3 : Cellule 4, Ligne 1 dans l'index 2 du tableau
            Cell cellule3 = feuille.getRow(1).getCell(3);
            donnees[2] = getCellValue(cellule3);

            // Condition 4 : Cellule 2, Ligne 3 dans l'index 3 du tableau
            Cell cellule4 = feuille.getRow(3).getCell(1);
            donnees[3] = getCellValue(cellule4);

            // Condition 5 : Cellule 4, Ligne 3 dans l'index 4 du tableau
            Cell cellule5 = feuille.getRow(3).getCell(3);
            donnees[4] = getCellValue(cellule5);

            // Condition 6 : Cellule 2, Ligne 2 dans l'index 5 du tableau
            Cell cellule6 = feuille.getRow(2).getCell(1);
            donnees[5] = getCellValue(cellule6);

            classeur.close();
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return donnees;
    }

    private static String getCellValue(Cell cell) {
        if (cell != null) {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        }
        return "";
    }
    public void telechargerAffichageVide() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrer le niveau:");
        String niveau = scanner.nextLine();

        System.out.println("Entrer le module:");
        String module = scanner.nextLine();

        System.out.println("Entrer le semestre:");
        String semestre = scanner.nextLine();

        System.out.println("Entrer la session:");
        String session = scanner.nextLine();


        String tableName = "Student";
        String excelFilePath = "noteparmodule1.xlsx";
        Connection c = DBConnection.getInstance();
        DBStudent trouveniv = new DBStudent();
        int nv = trouveniv.trouverIdNiveauParAlias(niveau);

        // Requête pour récupérer les étudiants
        PreparedStatement selectListeEtudiantsStm = c.prepareStatement("SELECT ID_ETUDIANT, CNE, NOM, PRENOM FROM " + tableName + " where ID_NIVEAU = ?");
        selectListeEtudiantsStm.setInt(1, nv);
        ResultSet selectListeEtudiants = selectListeEtudiantsStm.executeQuery();

        // Requête pour récupérer l'enseignant
        PreparedStatement selectensegnntStm = c.prepareStatement("SELECT enseignant,element FROM tableallniveau where titre = ? and alias =?");
        selectensegnntStm.setString(1, module);
        selectensegnntStm.setString(2, niveau);
        ResultSet enseignantResult = selectensegnntStm.executeQuery();


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Affichage");
        // Création de la ligne d'informations générales
        Row row0 = sheet.createRow(0);
        Cell cell00 = row0.createCell(0);
        cell00.setCellValue("MODULE:");
        Cell cell01 = row0.createCell(1);
        cell01.setCellValue(module);
        Cell cell02 = row0.createCell(2);
        cell02.setCellValue("SEMESTRE:");
        Cell cell03 = row0.createCell(3);
        cell03.setCellValue(semestre);

        // Récupération de l'enseignant
        if (enseignantResult.next()) {
            String enseignant = enseignantResult.getString("enseignant");
            Row row1 = sheet.createRow(1);
            Cell cell10 = row1.createCell(0);
            cell10.setCellValue("ENSEIGNANT:");
            Cell cell11 = row1.createCell(1);
            cell11.setCellValue(enseignant);
            Cell cell12 = row1.createCell(2);
            cell12.setCellValue("SESSION:");
            Cell cell13 = row1.createCell(3);
            cell13.setCellValue(session);
        }

        // Ligne d'informations sur la classe
        Row row2 = sheet.createRow(2);
        Cell cell20 = row2.createCell(0);
        cell20.setCellValue("CLASSE:");
        Cell cell21 = row2.createCell(1);
        cell21.setCellValue(niveau);
        Cell cell22 = row2.createCell(2);
        cell22.setCellValue("ANNEE:");
        Cell cell23 = row2.createCell(3);
        int annee = Calendar.getInstance().get(Calendar.YEAR);
        cell23.setCellValue(annee);

        // Ligne d'en-têtes des colonnes
        if (enseignantResult.next()) {
            String element = enseignantResult.getString("element");
            Row row4 = sheet.createRow(4);
            Cell cell40 = row4.createCell(0);
            cell40.setCellValue("ID");
            Cell cell41 = row4.createCell(1);
            cell41.setCellValue("CNE");
            Cell cell42 = row4.createCell(2);
            cell42.setCellValue("Nom");
            Cell cell43 = row4.createCell(3);
            cell43.setCellValue("Prénom");
            Cell cell45 = row4.createCell(4);
            cell45.setCellValue(element);
        }

        int rowNumber = 5;
        while (selectListeEtudiants.next()) {
            Row dataRow = sheet.createRow(rowNumber++);
            for (int i = 1; i < 5; i++) {
                Object value = selectListeEtudiants.getObject(i);
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
        sheet.setColumnWidth(0, 4000); // Colonne ID
        sheet.setColumnWidth(1, 6000); // Colonne CNE
        sheet.setColumnWidth(2, 8000); // Colonne Nom
        sheet.setColumnWidth(3, 8000); // Colonne Prénom
        sheet.setColumnWidth(4, 4000);
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        selectListeEtudiants.close();
        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        System.out.println("Exportation avec succès.");

    }

}

