 Clinique Médicale - Application de Gestion

Application desktop JavaFX de gestion d'une clinique médicale privée.

 Technologies
- Java 17
- JavaFX 21
- Hibernate 5 (JPA)
- MySQL 8 (Laragon)
- Lombok
- iTextPDF 5
- BCrypt

Architecture

src/main/java/com/clinique/
├── config/       → FactoryJPA
├── model/        → Entités JPA
├── DAO/          → Accès aux données
├── service/      → Logique métier
├── controller/   → Contrôleurs JavaFX
└── util/         → PDF, Navigation, Session

Fonctionnalités
- Authentification sécurisée (BCrypt)
- Gestion des patients (CRUD + recherche)
- Gestion des rendez-vous (+ filtre par médecin)
- Gestion des consultations
- Facturation
- Génération PDF ordonnance, facture, rapport journalier

- Installation

 Prérequis
- Java 17
- Laragon (MySQL)
- IntelliJ IDEA

 Base de données
Démarrez Laragon puis créez la base :
sql
CREATE DATABASE clinique_db;


 Configuration
Modifiez src/main/resources/META-INF/persistence.xml :
xml

Lancement
Ouvrez le projet dans IntelliJ et lancez Launcher.java

 Identifiants par défaut
- Login : admin
- Mot de passe : admin123

 Auteur
ADJI FATOU GUEYE L3GL

Sauvegardez Ctrl+S puis poussez sur GitHub :

Git → Commit → tapez Add README → Commit and Push

Vidéo démo — utilisez Xbox Game Bar

1. Appuyez Win+G → cliquez le bouton  Record
2. Lancez l'application
3. Montrez :
   - Connexion avec admin/admin123
   - Ajout d'un patient
   - Ajout d'un rendez-vous
   - Ajout d'une consultation
   - Génération PDF
   - Rapport journalier
4. Arrêtez l'enregistrement Win+G → Stop
5. La vidéo se sauvegarde dans C:\Users\ADJA\Videos\Captures
6. Félicitations !

le voilà le projet est complet ! On a :

✅ Code complet sur GitHub
✅ README
✅ Diagramme de classes
✅ Vidéo démo



