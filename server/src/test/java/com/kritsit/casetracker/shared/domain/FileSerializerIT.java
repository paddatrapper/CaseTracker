package com.kritsit.casetracker.shared.domain;

import static org.mockito.Mockito.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.IOException;

public class FileSerializerIT extends TestCase {
    private FileSerializer serializer;

    public FileSerializerIT(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(FileSerializerIT.class);
    }

    public void setUp() {
        serializer = new FileSerializer();
    }

    public void testSerialize() throws IOException {
        File f = new File("src/test/resources/file-test.txt");
        byte[] result = serializer.serialize(f);
        assertNotNull(result);
    }

    public void testWrite() throws IOException {
        File f = new File("src/test/resources/file-test.txt");
        byte[] result = serializer.serialize(f);
        File destinationFile = new File("src/test/resources/test-write.txt");
        serializer.write(destinationFile, result);
        assertTrue(destinationFile.isFile());
    }

    public void tearDown() {
        File f = new File("src/test/resources/test-write.txt");
        if (f.exists()) {
            boolean isSuccess = f.delete();
            if (!isSuccess) {
                System.out.println("Unable to delete file");
            }
        }
    }
}
