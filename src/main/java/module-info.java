module com.clinique {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires itextpdf;
    requires jbcrypt;
    opens com.clinique to javafx.fxml;
    opens com.clinique.controller to javafx.fxml;
    opens com.clinique.model to org.hibernate.orm.core, java.persistence;

    exports com.clinique;
}