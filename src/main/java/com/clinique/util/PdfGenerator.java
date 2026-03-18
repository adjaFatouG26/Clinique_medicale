package com.clinique.util;

import com.clinique.model.Consultation;
import com.clinique.model.Facture;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;
import com.clinique.model.RendezVous;
import com.clinique.model.Consultation;
import java.time.LocalDate;
import java.util.List;

public class PdfGenerator {

    private static final Font TITRE = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font SOUS_TITRE = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font NORMAL = new Font(Font.FontFamily.HELVETICA, 12);
    private static final Font GRAS = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    //  Ordonnance
    public static void genererOrdonnance(Consultation c, String cheminFichier) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(cheminFichier));
            doc.open();

            // En-tête clinique
            ajouterEntete(doc);

            // Titre
            Paragraph titre = new Paragraph("ORDONNANCE MÉDICALE", TITRE);
            titre.setAlignment(Element.ALIGN_CENTER);
            doc.add(titre);
            doc.add(Chunk.NEWLINE);

            // Infos patient
            doc.add(new Paragraph("Patient : " + c.getPatient().getPrenom()
                    + " " + c.getPatient().getNom(), GRAS));
            doc.add(new Paragraph("Médecin : " + c.getMedecin().toString(), NORMAL));
            doc.add(new Paragraph("Date     : " + c.getDate().toLocalDate(), NORMAL));
            doc.add(Chunk.NEWLINE);

            // Diagnostic
            doc.add(new Paragraph("Diagnostic :", SOUS_TITRE));
            doc.add(new Paragraph(c.getDiagnostic(), NORMAL));
            doc.add(Chunk.NEWLINE);

            // Prescription
            doc.add(new Paragraph("Prescription :", SOUS_TITRE));
            doc.add(new Paragraph(c.getPrescription(), NORMAL));
            doc.add(Chunk.NEWLINE);

            // Observations
            doc.add(new Paragraph("Observations :", SOUS_TITRE));
            doc.add(new Paragraph(c.getObservations(), NORMAL));
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);

            // Signature
            Paragraph signature = new Paragraph("Signature du médecin : ________________", NORMAL);
            signature.setAlignment(Element.ALIGN_RIGHT);
            doc.add(signature);

            doc.close();
            System.out.println("Ordonnance générée : " + cheminFichier);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Facture
    public static void genererFacture(Facture f, String cheminFichier) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(cheminFichier));
            doc.open();

            // En-tête clinique
            ajouterEntete(doc);

            // Titre
            Paragraph titre = new Paragraph("FACTURE", TITRE);
            titre.setAlignment(Element.ALIGN_CENTER);
            doc.add(titre);
            doc.add(Chunk.NEWLINE);

            // Infos
            doc.add(new Paragraph("N° Facture : " + f.getId(), GRAS));
            doc.add(new Paragraph("Date       : " + f.getDateEmission().toLocalDate(), NORMAL));
            doc.add(new Paragraph("Patient    : "
                    + f.getConsultation().getPatient().getPrenom()
                    + " " + f.getConsultation().getPatient().getNom(), NORMAL));
            doc.add(new Paragraph("Médecin    : "
                    + f.getConsultation().getMedecin().toString(), NORMAL));
            doc.add(Chunk.NEWLINE);

            // Tableau montant
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            PdfPCell cell1 = new PdfPCell(new Phrase("Prestation", GRAS));
            PdfPCell cell2 = new PdfPCell(new Phrase("Montant", GRAS));
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell1);
            table.addCell(cell2);

            table.addCell(new Phrase("Consultation médicale", NORMAL));
            table.addCell(new Phrase(f.getMontantTotal() + " FCFA", NORMAL));

            doc.add(table);
            doc.add(Chunk.NEWLINE);

            // Paiement
            doc.add(new Paragraph("Mode de paiement : " +
                    (f.getModePaiement() != null ? f.getModePaiement() : "Non défini"), NORMAL));
            doc.add(new Paragraph("Statut           : " + f.getStatutPaiement(), GRAS));

            doc.close();
            System.out.println("Facture générée : " + cheminFichier);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  En-tête commune
    private static void ajouterEntete(Document doc) throws DocumentException {
        // Logo textuel stylisé
        Font logoFont = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, new BaseColor(0, 102, 204));
        Paragraph logo = new Paragraph("🏥 CLINIQUENaby MÉDICALE PRIVÉE", logoFont);
        logo.setAlignment(Element.ALIGN_CENTER);
        doc.add(logo);

        Font adresseFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
        Paragraph adresse = new Paragraph(
                "Dakar, Sénégal  |  Tél: +221 78 518 47 09  |  cliniqueNaby@email.com", adresseFont);
        adresse.setAlignment(Element.ALIGN_CENTER);
        doc.add(adresse);

        LineSeparator line = new LineSeparator();
        line.setLineColor(new BaseColor(0, 102, 204));
        line.setLineWidth(2f);
        doc.add(new Chunk(line));
        doc.add(Chunk.NEWLINE);
    }
    public static void genererRapportJournalier(
            List<RendezVous> rdvList,
            List<Consultation> consultations,
            String cheminFichier) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(cheminFichier));
            doc.open();

            ajouterEntete(doc);

            // Titre
            Paragraph titre = new Paragraph(
                    "RAPPORT JOURNALIER — " + LocalDate.now(), TITRE);
            titre.setAlignment(Element.ALIGN_CENTER);
            doc.add(titre);
            doc.add(Chunk.NEWLINE);

            // Section RDV
            doc.add(new Paragraph("RENDEZ-VOUS DU JOUR", SOUS_TITRE));
            doc.add(Chunk.NEWLINE);

            PdfPTable tableRdv = new PdfPTable(4);
            tableRdv.setWidthPercentage(100);
            tableRdv.setWidths(new float[]{2f, 2f, 2f, 1.5f});

            // En-têtes
            for (String h : new String[]{"Patient", "Médecin", "Heure", "Statut"}) {
                PdfPCell cell = new PdfPCell(new Phrase(h, GRAS));
                cell.setBackgroundColor(new BaseColor(0, 102, 204));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableRdv.addCell(cell);
            }

            // Données
            for (RendezVous r : rdvList) {
                tableRdv.addCell(new Phrase(r.getPatient().toString(), NORMAL));
                tableRdv.addCell(new Phrase(r.getMedecin().toString(), NORMAL));
                tableRdv.addCell(new Phrase(r.getDateHeure().toLocalTime().toString(), NORMAL));
                tableRdv.addCell(new Phrase(r.getStatut().toString(), NORMAL));
            }
            doc.add(tableRdv);
            doc.add(Chunk.NEWLINE);

            // Section Consultations
            doc.add(new Paragraph("CONSULTATIONS DU JOUR", SOUS_TITRE));
            doc.add(Chunk.NEWLINE);

            PdfPTable tableConsult = new PdfPTable(3);
            tableConsult.setWidthPercentage(100);

            for (String h : new String[]{"Patient", "Médecin", "Diagnostic"}) {
                PdfPCell cell = new PdfPCell(new Phrase(h, GRAS));
                cell.setBackgroundColor(new BaseColor(0, 102, 204));
                tableConsult.addCell(cell);
            }

            for (Consultation c : consultations) {
                tableConsult.addCell(new Phrase(c.getPatient().toString(), NORMAL));
                tableConsult.addCell(new Phrase(c.getMedecin().toString(), NORMAL));
                tableConsult.addCell(new Phrase(c.getDiagnostic() != null ?
                        c.getDiagnostic() : "-", NORMAL));
            }
            doc.add(tableConsult);
            doc.add(Chunk.NEWLINE);

            // Résumé
            doc.add(new Paragraph("RÉSUMÉ", SOUS_TITRE));
            doc.add(new Paragraph("Total RDV : " + rdvList.size(), NORMAL));
            doc.add(new Paragraph("Total Consultations : " + consultations.size(), NORMAL));

            doc.close();
            System.out.println("Rapport généré : " + cheminFichier);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}