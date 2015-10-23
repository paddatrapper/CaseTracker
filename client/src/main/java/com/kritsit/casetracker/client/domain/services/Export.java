package com.kritsit.casetracker.client.domain.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Export implements IExportService{

    private final Logger logger = LoggerFactory.getLogger(Export.class);

    public void exportToPDF(List<String> headers, List<String[]> cells, File file){

        Document document = new Document();

        try {
	    logger.info("Creating file output");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(createTable(headers, cells));
            document.close();
	    logger.info("export to PDF: success");
        } catch (DocumentException e) {
            logger.error("Error while exporting to PDF");
            logger.error(e.toString());
        } catch (FileNotFoundException e) {
            logger.error("Error while exporting to PDF");
            logger.error(e.toString());
        }          
    }
  
    private PdfPTable createTable(List<String> headers, List<String[]> cells){
       
        PdfPTable table = new PdfPTable(headers.size());
	Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	logger.info("Filling in the table");	

	for(int i=0; i<headers.size(); i++){
	    PdfPCell cell = new PdfPCell(new Phrase(headers.get(i), boldFont));
	    table.addCell(cell);
	}
       
        for(int j=0; j<cells.size(); j++){
		for(int k=0; k < cells.get(j).length; k++){
		    PdfPCell cell = new PdfPCell(new Phrase(cells.get(j)[k]));
		    table.addCell(cell);
		}		           
        }           
        return table;           
    }
}
