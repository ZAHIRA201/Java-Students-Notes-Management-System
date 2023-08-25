package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FichierExcelModule {
    public  List<List<Object>> FichierExcelModules(String excelFilier) {
        List<List<Object>> dataFromExcel = new ArrayList<>();
        try {
            try (FileInputStream fis = new FileInputStream(excelFilier)) {
                Workbook workbook = new XSSFWorkbook(fis);

                int numSheets = workbook.getNumberOfSheets();
                ArrayList<String> titres = new ArrayList<>();
                titres.add("idNiveau");
                titres.add("alias");
                titres.add("semestre");
                titres.add("codeModule");
                titres.add("titre");
                titres.add("element");
                titres.add("enseignant");
                titres.add("cordinnateur");
                ArrayList<Integer> erreurdesaisie = new ArrayList<>();
                for (int sheetIndex = 0; sheetIndex < numSheets; sheetIndex++) {
                    Sheet sheet = workbook.getSheetAt(sheetIndex);
                    int rows = sheet.getLastRowNum();
                    int cols = sheet.getRow(0).getLastCellNum();

                    if (cols == 8) {
                        Row row1 = sheet.getRow(0);
                        for (int i = 0; i < 8; i++) {
                            XSSFCell cell = (XSSFCell) row1.getCell(i);
                            String valueArray = titres.get(i);
                            if (!cell.getStringCellValue().equals(valueArray)) {
                                erreurdesaisie.add(2);
                            }
                        }

                        for (int r = 1; r < rows; r++) {
                            Row row = sheet.getRow(r);
                            int[] str_cols = {1, 2, 3, 4, 5, 6, 7};
                            for (int j : str_cols) {
                                XSSFCell cell1 = (XSSFCell) row.getCell(j);
                                if (cell1 != null && cell1.getCellType() != CellType.STRING) {
                                    int rowIndex = cell1.getRowIndex();
                                    int columnIndex = cell1.getColumnIndex();
                                    System.out.println("Erreur de saisie à la cellule : Ligne " + rowIndex + ", Colonne " + columnIndex);
                                } else {
                                }
                            }

                        }
                        for (int i = 1; i <= rows; i++) {
                            Row row = sheet.getRow(i);
                            Cell cell1 = row.getCell(0);
                            Cell cell2 = row.getCell(1);
                            Cell cell3 = row.getCell(2);
                            Cell cell4 = row.getCell(3);
                            Cell cell5 = row.getCell(4);
                            Cell cell6 = row.getCell(5);
                            Cell cell7 = row.getCell(6);
                            Cell cell8 = row.getCell(7);



                            Object value1 = getCellValue(cell1);
                            Object value2 = getCellValue(cell2);
                            Object value3 = getCellValue(cell3);
                            Object value4 = getCellValue(cell4);
                            Object value5 = getCellValue(cell5);
                            Object value6 = getCellValue(cell6);
                            Object value7 = getCellValue(cell7);
                            Object value8 = getCellValue(cell8);


                            List<Object> rowData = new ArrayList<>();
                            rowData.add(value1);
                            rowData.add(value2);
                            rowData.add(value3);
                            rowData.add(value4);
                            rowData.add(value5);
                            rowData.add(value6);
                            rowData.add(value7);
                            rowData.add(value8);

                            dataFromExcel.add(rowData);

                        }
                        workbook.close();
                        fis.close();
                    } else {
                        erreurdesaisie.add(1);
                    }
                    for (Integer element : erreurdesaisie) {
                        switch (element) {
                            case 1 ->
                                    System.out.println("erreur : votre fichier excel ne contient pas le même nombre de colonne que le fichier d'origine");
                            case 2 ->
                                    System.out.println("erreur : vous avez modifié l'entête du fichier excel. Veuillez ne faire aucun changement dans l'entête du fichier");
                        }
                    }

                    if (rows == 0 && cols == 0) {
                        break;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataFromExcel;
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
                double numericValue = cell.getNumericCellValue();
                return String.valueOf(numericValue);
            }
            default -> {
                return null;
            }
        }
    }

}

