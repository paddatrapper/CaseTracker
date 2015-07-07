package com.kritsit.casetracker.shared.domain;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSerializer {
    private final Logger logger = LoggerFactory.getLogger(FileSerializer.class);

    public FileSerializer() {}

    public byte[] serialize(File f) throws IOException {
        logger.info("Serializing file {}", f.getAbsolutePath());
        byte[] outputBytes;
        try (FileInputStream input = new FileInputStream(f);
                ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] b  = new byte[1024];
            for (int i; (i = input.read(b)) != -1; i++) {
                output.write(b, 0, i);
            }
            outputBytes = output.toByteArray();
        }
        return outputBytes;
    }

    public void write(File f, byte[] bytes) throws IOException {
        logger.info("Writing to file {}", f.getAbsolutePath());
        boolean dirExists = f.mkdirs();
        boolean fileExists = f.delete();
        if (dirExists && fileExists) {
            try (FileOutputStream output = new FileOutputStream(f)) {
                output.write(bytes);
                output.flush();
            }
        } else {
            throw new IOException("Unable to write evidence to disk");
        }
    }
}
