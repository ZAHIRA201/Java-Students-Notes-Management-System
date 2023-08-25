package app;

import data.DBInscriptionAnnuelle;
import data.DBStudent;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ExceptionInscription {

    public static List<List<Object>> Inscription(String cheminFichier) {


        List<List<Object>> datainsr = new ArrayList<>();

        try {

            FileInputStream fis = new FileInputStream(cheminFichier);

            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);


            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();
            //System.out.println("Value of the first cell: " + cols);
            ArrayList<String> titres = new ArrayList<>();
            titres.add("ID ETUDIANT");
            titres.add("CNE");
            titres.add("NOM");
            titres.add("PRENOM");
            titres.add("ID NIVEAU");
            titres.add("TYPE");
            ArrayList<Integer> erreur = new ArrayList<>();

            if (cols == 6) {
                //verify les titres
                Row row1 = sheet.getRow(0);
                for (int i = 0; i < 6; i++) {
                    XSSFCell cell = (XSSFCell) row1.getCell(i);
                    String valueArray = titres.get(i);
                    if (cell.getStringCellValue().equals(valueArray)) {
                        continue;
                    }else{
                        erreur.add(2);
                    }
                }
                //verifier le type de cellules pour quel soit String
                for (int r = 1; r <= rows; r++) {
                    Row row = sheet.getRow(r);
                    int[] str_cols = {1, 2, 3, 5};
                    for (int j : str_cols) {
                        XSSFCell cell1 = (XSSFCell) row.getCell(j);
                        if (cell1.getCellType() == CellType.STRING) {
                            continue;
                        }else{
                            int rowIndex = cell1.getRowIndex();
                            int columnIndex = cell1.getColumnIndex();
                            System.out.println("Erreur de saisie à la cellule : Ligne " + rowIndex + ", Colonne " + columnIndex);
                        }
                    }
                }
                //verifier le type de cellules pour quel soit int
                for (int k = 1; k <= rows; k++){
                    Row row = sheet.getRow(k);
                    int[] int_cols = {0, 4};
                    for (int j : int_cols) {
                        XSSFCell cell1 = (XSSFCell) row.getCell(j);
                        if (cell1.getCellType() == CellType.NUMERIC) {
                            continue;
                        }else{
                            erreur.add(3);
                        }
                }
                }

                //read data from excel and store to arraylist

                for (int i =1;i<=rows;i++) {
                    Row row = sheet.getRow(i);
                    Cell cell1 = row.getCell(0);
                    Cell cell2 = row.getCell(1);
                    Cell cell3 = row.getCell(2);
                    Cell cell4 = row.getCell(3);
                    Cell cell5 = row.getCell(4);
                    Cell cell6 = row.getCell(5);


                    Object value1 = getCellValue(cell1);
                    Object value2 = getCellValue(cell2);
                    Object value3 = getCellValue(cell3);
                    Object value4 = getCellValue(cell4);
                    Object value5 = getCellValue(cell5);
                    Object value6 = getCellValue(cell6);

                    List<Object> rowData = new ArrayList<>();
                    rowData.add(value1);
                    rowData.add(value2);
                    rowData.add(value3);
                    rowData.add(value4);
                    rowData.add(value5);
                    rowData.add(value6);
                    datainsr.add(rowData);



                }
                workbook.close();
                fis.close();

            }else {
                erreur.add(1);
            }
            for (Integer element : erreur) {
                switch (element){
                    case 1-> System.out.println("erreur :votre fichier excel ne contient pas le meme nombre de colonne que le fichier d'origine");
                    case 2-> System.out.println("erreur :vous avez modifier l'entet de fichier excel s'il vous plait ne fais aucun change dans l'entete de fichier");
                    case 3-> System.out.println("erreur: verifier le type des donnees que vous avez saisie dans les colonne:" +
                            "ID ETUDIANT, NIVEAU" +
                            "  il faut quelle soit des entiers");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datainsr;

    }



    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING -> {
                return cell.getStringCellValue();
            }
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            }
            default -> {
                return null;
            }
        }
    }

     DBInscriptionAnnuelle dbi = new DBInscriptionAnnuelle();
    DBStudent dbs =new DBStudent();

    public void Reinscription() throws Exception {
        List<Object[]> ValideFromStudent = dbs.getAllValidStudent();
        List<Object[]> ValideFromInscription = dbi.getAllValid();

        for (Object[] row : ValideFromStudent){
            for (Object[] row2 : ValideFromInscription) {
                if(row[0].equals(row2[0])){
                    if (!Arrays.equals(row, row2)) {
                        System.out.println("les données ne sont pas similaire: ");
                        System.out.println("Données du fichier excel: " + "[nom: " + row[1] +
                                " prenom: " + row[2] + " cne: " + row[3] + "]");
                        System.out.println("Données du database: " + "[nom: " + row2[1] +
                                " prenom: " + row2[2] + " cne: " + row2[3] + "]");
                        System.out.println("Veuillez faire la mise à jour de la base de données?: Y/N");
                        Scanner sc = new Scanner(System.in);
                        String choix = sc.next();
                        //mise a jour des donnees du base de donnees
                        if (choix.equals("Y")) {
                            dbi.updateAttribut(row[0], row[1], row[2], row[3]);
                            dbi.autoHistorique((Integer) row[0], (String) row2[1], (String) row2[2], (String) row2[3], (String) row[1], (String) row[2], (String) row[3]);
                            System.out.println("database updated successfully!");
                        }
                    } else {
                        System.out.println("no change into the database");
                    }


            }}
        //faire la reinscription : update id niveau et validation sear vide
        for (Object[] roww : ValideFromStudent) {
            dbi.addNew(roww[4], roww[0]);
        }

    }}}






