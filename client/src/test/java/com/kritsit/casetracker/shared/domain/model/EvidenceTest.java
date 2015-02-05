package com.kritsit.casetracker.shared.domain.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

public class EvidenceTest extends TestCase {
    Evidence evidence;

    public EvidenceTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EvidenceTest.class);
    }

    public void setUp() {
        File serverFile = new File("test.file");
        File localFile = new File("local.file");
        evidence = new Evidence("Test file", serverFile, localFile);
    }

    public void testAccessors() {
        File serverFile = new File("test.file");
        File localFile = new File("local.file");

        assertTrue("Test file".equals(evidence.getDescription()));
        assertTrue(serverFile.getAbsolutePath().equals(evidence.getServerFileLocation()));
        assertTrue(localFile.equals(evidence.getLocalFile()));
        assertTrue(localFile.getAbsolutePath().equals(evidence.getLocalFileLocation()));
        assertTrue(evidence.getImage() == null);
    }

    public void testMutators() {
        evidence.setDescription("Another description");
        File serverFile = new File("server.file");
        evidence.setServerFile(serverFile);
        File localFile = new File("test.file");
        evidence.setLocalFile(localFile);
        evidence.setImage(null);

        assertTrue(localFile.equals(evidence.getLocalFile()));
        assertTrue(serverFile.getAbsolutePath().equals(evidence.getServerFileLocation()));
        assertTrue("Another description".equals(evidence.getDescription()));
        assertTrue(evidence.getImage() == null);
    }

    public void testToString() {
        File serverFile = new File("test.file");
        String toString = "Evidence: Test file (" + serverFile.getAbsolutePath() + ")";
        assertTrue(toString.equals(evidence.toString()));
    }

    public void testEquals_Null() {
        assertFalse(evidence.equals(null));
    }
    
    public void testEquals_Class() {
        assertFalse(evidence.equals("test"));
    }

    public void testEquals_Description() {
        File serverFile = new File("test.file");
        File localFile = new File("local.file");
        Evidence e = new Evidence("wrong description", serverFile, localFile);
        assertFalse(evidence.equals(e));
    }

    public void testEquals() {
        File serverFile = new File("test.file");
        File localFile = new File("local.file");
        Evidence e = new Evidence("Test file", serverFile, localFile);
        assertTrue(evidence.equals(e));
    }
}
