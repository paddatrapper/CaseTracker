package com.kritsit.casetracker.shared.domain.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.util.Arrays;

public class EvidenceTest extends TestCase {
    Evidence evidence;

    public EvidenceTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EvidenceTest.class);
    }

    @SuppressFBWarnings("UC_USELESS_OBJECT")
    public void setUp() {
        File serverFile = new File("test.file");
        File localFile = new File("local.file");
        evidence = new Evidence(1, "Test file", serverFile, localFile);
        Evidence e = new Evidence(2, "Test file", serverFile);
        assertNull(e.getLocalFile());
    }

    public void testAccessors() {
        File serverFile = new File("test.file");
        File localFile = new File("local.file");

        assertTrue("Test file".equals(evidence.getDescription()));
        assertTrue(serverFile.getAbsolutePath().equals(evidence.getServerFileLocation()));
        assertTrue(serverFile.equals(evidence.getServerFile()));
        assertTrue(localFile.equals(evidence.getLocalFile()));
        assertTrue(localFile.getAbsolutePath().equals(evidence.getLocalFileLocation()));
        assertTrue(Arrays.equals(evidence.getByteFile(), new byte[0]));
    }

    public void testMutators() {
        evidence.setDescription("Another description");
        File serverFile = new File("server.file");
        evidence.setServerFile(serverFile);
        File localFile = new File("test.file");
        evidence.setLocalFile(localFile);
        evidence.setByteFile((byte[]) null);

        assertTrue(localFile.equals(evidence.getLocalFile()));
        assertTrue(serverFile.getAbsolutePath().equals(evidence.getServerFileLocation()));
        assertTrue("Another description".equals(evidence.getDescription()));
        assertTrue(Arrays.equals(evidence.getByteFile(), new byte[0]));
    }

    public void testToString() {
        File serverFile = new File("test.file");
        String serverString = "Test file (" + serverFile.getAbsolutePath() + ")";
        File localFile = new File("local.file");
        String localString = "Test file (" + localFile.getAbsolutePath() + ")";
        Evidence e = new Evidence(1, "Test file", null, localFile);

        assertTrue(serverString.equals(evidence.toString()));
        assertTrue(localString.equals(e.toString()));
    }

    public void testEquals_Null() {
        assertFalse(evidence.equals(null));
    }
    
    public void testEquals_Class() {
        assertFalse(evidence.equals("test"));
    }

    public void testEquals() {
        File serverFile = new File("test.file");
        File localFile = new File("local.file");
        Evidence e = new Evidence(1, "Test file", serverFile, localFile);
        Evidence otherEvidence = new Evidence(-1, "Another file", serverFile, localFile);
        assertTrue(evidence.equals(e));
        assertFalse(evidence.equals(otherEvidence));
    }
}
