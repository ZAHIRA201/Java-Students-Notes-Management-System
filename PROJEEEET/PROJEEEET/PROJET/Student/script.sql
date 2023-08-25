--la creation des tables se fait automatiquement par la classe TableInstaller une fois le programme est executé,
--par la suite vous pouvez la commenter, sinon voici les instructions sql :

CREATE DATABASE project_team;
USE project_team;

CREATE TABLE IF NOT EXISTS Student (
        ID_ETUDIANT INT PRIMARY KEY,
        CNE VARCHAR(50) NOT NULL,
        NOM VARCHAR(50) NOT NULL,
        PRENOM VARCHAR(50) NOT NULL,
        ID_NIVEAU INT NOT NULL,
        TYPE VARCHAR(50) NOT NULL
    );

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
    );

CREATE TABLE IF NOT EXISTS VERIFICATION (
        id_student INT,
        CNE VARCHAR(255),
        NOM VARCHAR(255),
        PRENOM VARCHAR(255),
        PRIMARY KEY (id_student)
    );

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
    );

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
    );

CREATE TABLE IF NOT EXISTS Annexe (
        idNiveau VARCHAR(50),
        alias VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS TableAllNiveau (
        idNiveau int,
        alias VARCHAR(50),
        semestre VARCHAR(50),
        codeModule VARCHAR(50),
        titre VARCHAR(255),
        element VARCHAR(255),
        enseignant VARCHAR(50),
        cordinnateur VARCHAR(50)
    );


CREATE TABLE IF NOT EXISTS InscriptionModule (
        id INT AUTO_INCREMENT,
        idNiveau INT,
        codeModule VARCHAR(20),
        idInsc INT,
        noteSN VARCHAR(20),
        noteSR VARCHAR(20),
        validation VARCHAR(20),
        PRIMARY KEY (id)
    );


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
    );

CREATE INDEX id_cod ON TableAllNiveau (codeModule);

ALTER TABLE InscriptionModule
    ADD CONSTRAINT fk_idModule FOREIGN KEY (codeModule)
    REFERENCES TableAllNiveau (codeModule);

CREATE INDEX id_id ON InscriptionModule (idInsc);

ALTER TABLE InscriptionElement
    ADD CONSTRAINT fk_idinscription FOREIGN KEY (idInsc)
    REFERENCES InscriptionModule (idInsc);

insert into inscription(annee, type, validation, id_niv, id_student, nom, prenom,cne)
values(2022, "REINSCRIPTION", "V", 2, 2, "CHAKIR", "FZ", "M131191427");
insert into inscription(annee, type, validation, id_niv, id_student, nom, prenom,cne)
values(2022, "REINSCRIPTION", "V", 3, 3, "BOUKAYOUA", "LOUBNA", "E1245678889");
