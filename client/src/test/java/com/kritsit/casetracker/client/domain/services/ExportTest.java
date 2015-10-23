package com.kritsit.casetracker.client.domain.services;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.nio.file.Files;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

public class ExportTest extends TestCase {

    public ExportTest(String name){
        super(name);
    }
    
    public static Test suite(){
        return new TestSuite(ExportTest.class);
    }
    
    public void testExport(){
        IExportService exportService = new Export();
        List<String> headers = new ArrayList<String>();
        headers.add("first column");
        headers.add("second column");
        List<String[]> rows = new ArrayList<String[]>();
        String[] firstRow = new String[2];
        firstRow[0] = "first row, first column";
        firstRow[1] = "first row, second column";
        rows.add(firstRow);
        String[] secondRow = new String[2];
        secondRow[0] = "second row, first column";
        secondRow[1] = "second row, second column";
        rows.add(secondRow);
        File file = new File("test.pdf");
        exportService.exportToPDF(headers, rows, file);
        assertTrue(file.exists());
        try{
            file.delete();
        }
        catch(Exception e){
            fail();
        } 
        assertFalse(file.exists());
    }  
    
}