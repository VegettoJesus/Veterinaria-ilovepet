package com.veterinaria.proyecto_veterinaria.Entidad_usuario;

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

public class EmpleadoExporterPDF {
    private List<Empleado_Login> listaEmpleados;

    public EmpleadoExporterPDF(List<Empleado_Login> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
    
    private void escribirCabeceraDeLaTabla(PdfPTable tabla){
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.RED);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("ID", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("DNI", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("NOMBRE", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("APELLIDO", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("F. NACIMIENTO", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("CELULAR", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("EMAIL", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("DIRECCION", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("USUARIO", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("SEXO", fuente));
        tabla.addCell(celda);

    }
    private void escribirDatosDeLaTabla(PdfPTable tabla){
        for(Empleado_Login empleado : listaEmpleados){
            tabla.addCell(String.valueOf(empleado.getId()));
            tabla.addCell(String.valueOf(empleado.getDni()));
            tabla.addCell(empleado.getNombre());
            tabla.addCell(empleado.getApellido());
            tabla.addCell(empleado.getFecha_Nacimiento().toString());
            tabla.addCell(String.valueOf(empleado.getCelular()));
            tabla.addCell(empleado.getEmail());
            tabla.addCell(empleado.getDireccion());
            tabla.addCell(empleado.getUsuario());
            tabla.addCell(empleado.getSexo());
        }
    }
    public void exportar(HttpServletResponse response) throws DocumentException, IOException{
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();
        
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Lista de empleados", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);
        
        PdfPTable tabla = new PdfPTable(10);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(15);
        tabla.setWidths(new float[] {1f,2.3f,2.3f,2.3f,2f,2f,2f,2f,2f,2f});
        tabla.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(tabla);
        escribirDatosDeLaTabla(tabla);
        documento.add(tabla);

        documento.close();
    }
}
