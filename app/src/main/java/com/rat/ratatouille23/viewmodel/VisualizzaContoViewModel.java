package com.rat.ratatouille23.viewmodel;

import android.os.Environment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfWriter;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.StoricoOrdinazioniChiuse;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class VisualizzaContoViewModel extends ViewModel {
    Repository repository;

    Tavolo tavolo;

    Menu menu;

    Ordinazione ordinazione;

    public MutableLiveData<ArrayList<Portata>> listaPortateConto = new MutableLiveData<ArrayList<Portata>>();

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<Float> costoTotaleConto = new MutableLiveData<>(0.0f);

    public MutableLiveData<String> messaggioVisualizzaConto = new MutableLiveData<>("");

    public VisualizzaContoViewModel() {
        repository = Repository.getInstance();
        repository.setVisualizzaContoViewModel(this);

        menu = repository.getMenu();
        tavolo = repository.getTavoloSelezionato();
        ordinazione = tavolo.getOrdinazione();
        aggiornaListaPortateConto();
        aggiornaCostoTotaleConto();
    }

    public void aggiornaListaPortateConto() {
        listaPortateConto.setValue(ordinazione.getPortate());
        System.err.println("aggiornamento lista portate conto fatto");
    }

    public void aggiornaCostoTotaleConto() {
        costoTotaleConto.setValue(ordinazione.getCostoTotalePortate());
        System.err.println("aggiornamento costo totale conto fatto");
    }

    public void salvaPDF() {
        generateOrderPdf();
    }

    public void generateOrderPdf() {

        // Create a new document using iText library
        Document document = new Document();
        try {
            // Set the file path and create the file
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/order.pdf";
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);

            // Create a PdfWriter instance to write to the document
            PdfWriter.getInstance(document, fos);

            // Open the document and add content
            document.open();
            document.add(new Paragraph("Order Details"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Order ID: " +  new Random().nextInt((1500 - 100) + 1) + 100));
            document.add(new Paragraph("Tavolo: " + ordinazione.getTavolo().getNome()));
            document.add(new Paragraph("Portate:"));

            for (Portata portata : ordinazione.getPortate()) {
                document.add(new Paragraph(portata.getNome() + " " + portata.getCosto()));
            }
            document.add(new Paragraph("Total: " + ordinazione.getCostoTotalePortate()));

            // Close the document and file output stream
            document.close();
            fos.close();

            // Show a success message to the user
            setMessaggioVisualizzaConto("PDF saved to Downloads folder.");

        } catch (IOException | DocumentException e) {
            // Handle any exceptions that occur during PDF creation
            setMessaggioVisualizzaConto("Error generating PDF.");
            e.printStackTrace();
        }
    }

    public void chiudiConto() {
        repository.getStoricoOrdinazioniChiuse().chiudiOrdinazione(ordinazione);
        setTornaIndietro();
    }

    public void setTornaIndietro() {
        tornaIndietro.setValue(true);
    }

    public void setFalseTornaIndietro() {
        tornaIndietro.setValue(false);
    }

    public void setMessaggioVisualizzaConto(String nuovoMessaggioVisualizzaConto) {
        messaggioVisualizzaConto.setValue(nuovoMessaggioVisualizzaConto);
    }

    public String getMessaggioVisualizzaConto() {
        return messaggioVisualizzaConto.getValue();
    }

    public Boolean isNuovoMessaggioVisualizzaConto() {
        return getMessaggioVisualizzaConto() != "";
    }

    public void cancellaMessaggioVisualizzaConto() {
        messaggioVisualizzaConto.setValue("");
    }

}
