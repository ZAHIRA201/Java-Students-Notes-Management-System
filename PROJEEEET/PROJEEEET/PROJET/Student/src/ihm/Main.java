package ihm;

import app.*;
import data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static data.ExcelImporter.importerDonneesSelonConditions;

public class Main {
    public static void main(String[] args) throws Exception {

        if (AppInstaller.checkIfAlreadyInstalled()) {
            System.out.println("Database already exists");
        } else {
            //create the database
            //AppInstaller.run();
            System.out.println("Database created correctelly");
        }
        System.out.println("=====================================================");
        System.out.println("Bienvenue dans le programme de gestion des notes ");
        System.out.println("=====================================================");
        System.out.println();
        System.out.println("Quel traitement souhaitez-vous effectuer ?");
        System.out.println("1- Ajouter un fichier Excel d'inscription");
        System.out.println("2-Affecter les étudiants inscrits à leurs modules et elements ");
        System.out.println("3- traitement sur les etudiants");
        System.out.println("4- Ajouter les notes des etudiants");
        System.out.println("5- structures pédagogiques");
        System.out.println("6- affichage des notes");
        System.out.println("7- Quitter");
        DBStudent ver = new DBStudent();
        ExceptionInscription test = new ExceptionInscription();
        DBInscriptionModule mod = new DBInscriptionModule();
        DBInscriptionAnnuelle insr = new DBInscriptionAnnuelle();

        Scanner sc = new Scanner(System.in);
        int choix1 = sc.nextInt();

        switch (choix1) {
            case 1 -> {
                System.out.println("> S'il vous plaît, saisissez le chemin du fichier Excel :");
                Scanner sc1 = new Scanner(System.in);
                String fichier = sc1.nextLine();
                List<List<Object>> data = test.Inscription(fichier);

                insr.addIntoStudent(data);
                Boolean exist = ver.verifir();
                if (exist) {
                    insr.addIntoInscription(data);
                    test.Reinscription();

                }
            }
            case 2 -> {
                DBInscriptionModule.ajoutModules();
                System.out.println("les etudiants sont ajoutés aux modules concernés par leurs niveaux.");
            }
            case 3 -> {
                System.out.println("Quel traitement souhaitez-vous effectuer ?\n");
                System.out.println("1- Afficher la liste des étudiants d'une classe");
                System.out.println("2- Modifier les informations d'un étudiant");
                System.out.println("3- Quitter la section");
                Scanner sc2 = new Scanner(System.in);
                int choix2 = sc2.nextInt();
                switch (choix2) {
                    case 1:
                        System.out.println("Entrer l'alias de la classe :");
                        Scanner sc3 = new Scanner(System.in);
                        String alias = sc3.next();
                        int id_niv = ver.trouverIdNiveauParAlias(alias);
                        if (id_niv != 0) {
                            ver.AfficherEtudiantsParClass(id_niv);
                        }
                        break;
                    case 2:
                        System.out.println("Entrer l'id_inscription de l'étudiant :");
                        System.out.println("Pour avoir l'id_inscription, consultez l'option : Afficher la liste des étudiants d'une classe\n");
                        Scanner sc4 = new Scanner(System.in);
                        int id_inscri = sc4.nextInt();
                        ver.ModificationEtudiant(id_inscri);
                        break;
                    case 3:
                        break;
                }
            }
            case 4 -> {
                Object[] entete={"M12", "N", "S1", 3, 2023,"X"};
                Object[][] data= {{2, "M131191427", "CAHKIR", "FZ", 16}};

                AjoutNotes.ajout1ElelementSN(entete,data);
            }
            case 5 -> {
                System.out.println("1 -telecharger la derniere version du fichier excel contient les filieres avec modules");
                System.out.println("2 -Importer la structure pédagogique d’une filière apres modification");
                System.out.println("3 -Affichage d'annexe");
                Scanner sc3 = new Scanner(System.in);
                int choix4 = sc3.nextInt();
                switch (choix4) {
                    case 1:
                        System.out.println("Veuillez placer un fichier Excel vide avec le nom \"StructureFiliere\" dans le dossier de votre projet.");
                        System.out.println("Entrez \"done\" lorsque vous avez terminé.");
                        Scanner sc4 = new Scanner(System.in);
                        String confirmation = sc4.next();
                        if(confirmation.equals("done")){FichierExcelModuleDao fichierTelecharger = new FichierExcelModuleDao();
                            fichierTelecharger.telechargerStructure();
                        }else{
                            break;
                        }
                        break;
                    case 2:
                        System.out.println("> remarque: Télécharger la dernière structure à partir de l'option 1.");
                        System.out.println("> remarque: ne fait aucun modification sur l'entete de fichier");
                        System.out.println("  Vous pouvez effectuer les traitements suivants sur le fichier Excel :");
                        System.out.println("   > Modifier/Supprimer/Créer les éléments, les modules, les classes et les filières");
                        System.out.println("   > Associer des modules à une classe (niveau)");
                        System.out.println("   > Associer des éléments à un module");
                        System.out.println("   > Associer des classes à une filière");
                        System.out.println("   > Affecter un coordonnateur à la filière");
                        System.out.print("Si vous avez effectué les modifications souhaitées:");
                        System.out.println("> S'il vous plaît, importez le fichier Excel :");
                        Scanner sc31 = new Scanner(System.in);
                        String pathfichier = sc31.nextLine();
                        FichierExcelModule excelfiliere = new FichierExcelModule();
                        List<List<Object>> datafiliere = excelfiliere.FichierExcelModules(pathfichier);
                        FichierExcelModuleDao filiere = new FichierExcelModuleDao();
                        filiere.AddFiliereModule(datafiliere);
                        break;
                    case 3:
                        AnnexeDao Annexe =new AnnexeDao();
                        Annexe.AfficheAnnexe();
                        break;
                }
            }
            case 6 -> {
                System.out.println("1- telecharger un fichier excel pour la sasie des notes");
                System.out.println("2- telecharger un affichage par module avec la  verifrcation des conditions necessaire");
                Scanner sc31 = new Scanner(System.in);
                int ch = sc31.nextInt();
                ExcelImporter FILE = new ExcelImporter();
                if(ch == 1){
                    FILE.telechargerAffichageVide();
                }else if(ch ==2){
                    ArrayList<ArrayList<String>> donneesExceldonne= FILE.d();
                    String nomFichier = "noteparmodule1mood.xlsx";
                    String[] donneesExcelentete = importerDonneesSelonConditions(nomFichier);
                }else{
                    System.out.println("done");
                }

            }
            case 7 ->System.out.println(" au revoir!");

        }
    }
}




