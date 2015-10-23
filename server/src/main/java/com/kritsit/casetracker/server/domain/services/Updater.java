package com.kritsit.casetracker.server.domain.services;

import com.kritsit.casetracker.shared.domain.FileSerializer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;

public class Updater implements IUpdateService {
    private final Logger logger = LoggerFactory.getLogger(Updater.class);
    private File root;

    public Updater() {
        root = new File("");
    }

    public boolean isUpdateRequired(String currentVersion) throws IOException {
        logger.debug("Checking for update for client version {}", currentVersion);
        File client = findClientArchive();
        String requiredVersion = getLatestVersion(client);
        return !currentVersion.equals(requiredVersion);
    }

    public byte[] getUpdate() throws IOException {
        File client = findClientArchive();
        FileSerializer serializer = new FileSerializer();
        return serializer.serialize(client);
    }

    private File findClientArchive() throws FileNotFoundException {
        logger.debug("Finding client archive...");
        File[] files = root.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.contains("client") && fileName.endsWith(".jar")) {
                return file;
            }
        }
        throw new FileNotFoundException("Unable to find client archive in " + 
                root.getAbsolutePath());
    }

    private String getLatestVersion(File client) throws IOException {
        logger.debug("Running client to get version...");
        Process clientProcess = new ProcessBuilder("java", "-jar", 
                client.getAbsolutePath(), "--version").start();
        InputStreamReader inReader = 
            new InputStreamReader(clientProcess.getInputStream());
        BufferedReader in = new BufferedReader(inReader);
        String line;
        while ((line = in.readLine()) != null) {
            logger.debug("Output: {}", line);
            if (line.contains("Version: ")) {
                return line.replace("Version: ", "");
            }
        }
        throw new IOException("Unable to find version of client archive " + 
                client.getAbsolutePath());
    }
}
