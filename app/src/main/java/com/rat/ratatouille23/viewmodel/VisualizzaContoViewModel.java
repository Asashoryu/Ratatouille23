package com.rat.ratatouille23.viewmodel;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rat.ratatouille23.model.Ingrediente;
import com.rat.ratatouille23.model.IngredientePortata;
import com.rat.ratatouille23.model.Menu;
import com.rat.ratatouille23.model.Ordinazione;
import com.rat.ratatouille23.model.Portata;
import com.rat.ratatouille23.model.PortataOrdine;
import com.rat.ratatouille23.model.StoricoOrdinazioniChiuse;
import com.rat.ratatouille23.model.Tavolo;
import com.rat.ratatouille23.repository.IngredientiRepository;
import com.rat.ratatouille23.repository.OrdinazioniRepository;
import com.rat.ratatouille23.repository.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class VisualizzaContoViewModel extends ViewModel {
    private Repository repository;

    private OrdinazioniRepository ordinazioniRepository;

    private IngredientiRepository ingredientiRepository;

    private Tavolo tavolo;
    private Ordinazione ordinazione;

    private StoricoOrdinazioniChiuse storicoOrdinazioniChiuse;

    private Context context;
    public MutableLiveData<ArrayList<PortataOrdine>> listaPortateConto = new MutableLiveData<ArrayList<PortataOrdine>>();

    public MutableLiveData<Boolean> tornaIndietro = new MutableLiveData<>(false);

    public MutableLiveData<Float> costoTotaleConto = new MutableLiveData<>(0.0f);

    public MutableLiveData<String> messaggioVisualizzaConto = new MutableLiveData<>("");

    public VisualizzaContoViewModel() {
        repository = Repository.getInstance();
        Repository.visualizzaContoViewModel = this;
        ordinazioniRepository = new OrdinazioniRepository();
        ingredientiRepository = new IngredientiRepository();

        tavolo = repository.getTavoloSelezionato();
        ordinazione = tavolo.getOrdinazione();
        storicoOrdinazioniChiuse = repository.getStoricoOrdinazioniChiuse();

        aggiornaListaPortateConto();
        aggiornaCostoTotaleConto();
    }

    public void setFragmentContext(Context context) {
        this.context = context;
    }

    public void aggiornaListaPortateConto() {
        listaPortateConto.setValue(ordinazione.getPortateOrdine());
        System.err.println("aggiornamento lista portate conto fatto");
    }

    public void aggiornaCostoTotaleConto() {
        costoTotaleConto.setValue(ordinazione.getCostoTotalePortateOrdine());
        System.err.println("aggiornamento costo totale conto fatto");
    }

    public void salvaPDF() {
        generateOrderPdf(context);
    }

    public void generateOrderPdf(Context context) {
        // Create a new document using iText library
        Document document = new Document();
        try {
            // Set the file path and create the file with a unique name
            Integer tableName = ordinazione.getTavolo().getId();
            String fileName = "order_" + tableName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")) + ".pdf";
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadsDir, fileName);
            FileOutputStream fos = new FileOutputStream(file);

            // Create a PdfWriter instance to write to the document
            PdfWriter.getInstance(document, fos);

            // Open the document and add content
            document.open();
            document.add(new Paragraph("Order Details"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Order ID: " + new Random().nextInt((1500 - 100) + 1) + 100));
            document.add(new Paragraph("Tavolo: " + tableName));
            document.add(new Paragraph("Portate:"));

            for (PortataOrdine portataOrdine : ordinazione.getPortateOrdine()) {
                document.add(new Paragraph(portataOrdine.getPortata().getNome() + " x" + portataOrdine.getQuantita() + " " + portataOrdine.getCostoTotalePortataOrdine()));
            }
            document.add(new Paragraph("Total: " + ordinazione.getCostoTotalePortateOrdine()));

            // Close the document and file output stream
            document.close();
            fos.close();

            // Show a success message to the user
            setMessaggioVisualizzaConto("PDF saved to Downloads folder.");

            // Notify the media scanner about the new file so that it can be indexed
            MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, null);

        } catch (IOException | DocumentException e) {
            // Handle any exceptions that occur during PDF creation
            setMessaggioVisualizzaConto("Error generating PDF.");
            e.printStackTrace();
        }
    }

    public void chiudiConto() {
        try {
            chiudiConto(ordinazione);
            setTornaIndietro();
        } catch (IOException e) {
            setMessaggioVisualizzaConto(e.getMessage());
        }
    }

    public void chiudiConto(Ordinazione ordinazione) throws IOException {
        ordinazioniRepository.updateContoIsChiusoBackend(ordinazione.getId(), true);
        storicoOrdinazioniChiuse.chiudiOrdinazione(ordinazione);

        riduciQuantitaIngredientiOrdinazione(ordinazione);
    }

    public void riduciQuantitaIngredientiOrdinazione(Ordinazione ordinazione) throws IOException {
        for (PortataOrdine portataOrdine : ordinazione.getPortateOrdine()) {
            Portata portata = portataOrdine.getPortata();
            System.err.println("Nome portata: " + portata.getNome());
            System.out.println("ecco i suoi ingredienti");
            portata.getIngredientiPortata().forEach(ingredientePortata -> System.out.println(ingredientePortata.getIngrediente().getNome()));
            System.out.println("fine ingredienti");
            for (IngredientePortata ingredientePortata : portata.getIngredientiPortata()) {
                System.err.println("Nome ingrediente: " + ingredientePortata.getIngrediente().getNome());
                Ingrediente ingrediente = ingredientePortata.getIngrediente();
                float quantitaIngrediente = ingrediente.getQuantita();
                quantitaIngrediente = quantitaIngrediente - (portataOrdine.getQuantita() * ingredientePortata.getQuantita());
                // riduci in backend
                ingredientiRepository.updateIngredientQuantityBackend(ingrediente.getNome(), quantitaIngrediente);
                ingrediente.setQuantita(quantitaIngrediente);
            }
        }
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
