package com.veterinaria.proyecto_veterinaria.Entidades;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class HistorialExporterPDF {
    private List<Citas> listaCitas;
    private Long valor;

    public HistorialExporterPDF(List<Citas> listaCitas,Long valor) {
        this.listaCitas = listaCitas;
        this.valor = valor;
    }

    private void escribirCabezeraDeLaTabla(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.RED);
        celda.setPadding(6);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("N° Atención",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Fecha Atención",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Hora Atención",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Descripción",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Observación",fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Tratamiento",fuente));
        tabla.addCell(celda);
    }
    private void escribirDatosDeLaTabla(PdfPTable tabla){
        for(Citas citas : listaCitas){
            if(valor == citas.getMascota().getId()){
                tabla.addCell(String.valueOf(citas.getId()));
                tabla.addCell(citas.getFecha_cita().toString());
                tabla.addCell(citas.getHora_cita());
                tabla.addCell(citas.getServicio().getDescripcion());
                tabla.addCell(citas.getObservaciones());
                tabla.addCell(citas.getTratamiento());
            }
            
        }
    }
    public void exportar(HttpServletResponse response) throws DocumentException, IOException{
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        for(Citas citas : listaCitas){
            if(valor == citas.getMascota().getId()){
                Paragraph titulo = new Paragraph(citas.getMascota().getNombre(), fuente);
                titulo.setAlignment(Paragraph.ALIGN_CENTER);
                documento.add(titulo);
                break;
            }
            
        }
        

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[]{1f,2.3f,2.3f,2.4f,2.3f,2.3f});
        tabla.setWidthPercentage(110);

        escribirCabezeraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);

        documento.add(tabla);
        documento.close();

    }
}