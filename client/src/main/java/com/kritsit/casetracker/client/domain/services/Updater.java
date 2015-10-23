package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.CaseTrackerClient;
import com.kritsit.casetracker.client.domain.ui.controller.UpdateController;
import com.kritsit.casetracker.shared.domain.FileSerializer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Updater implements IUpdateService {
    private final Logger logger = LoggerFactory.getLogger(Updater.class);
    private IConnectionService connection;
    private File root;
    private UpdateController controller;
    
    public Updater(IConnectionService connection, UpdateController controller) {
        this.connection = connection;
        this.controller = controller;
        root = new File("update");
    }

    public boolean checkForUpdate(String currentVersion) {
        logger.debug("Checking if update is required");
        return connection.checkForUpdate(currentVersion);
    }

    public File update() throws IOException {
        logger.info("Updating client...");
        ZipFile updateZip = downloadUpdate();
        unzip(updateZip);
        copyDirectory(root, new File("."));
        cleanUp();
        return getJar(new File("."));
    }

    public void launch(File jar) throws IOException {
        String[] run = {"java", "-jar", jar.getAbsolutePath()};
        Runtime.getRuntime().exec(run);
        restart();
    }

    private ZipFile downloadUpdate() throws IOException {
        logger.info("Downloading update...");
        controller.setStatus("Downloading client from the server...");
        FileSerializer serializer = new FileSerializer();
        File updateZip = new File("update.zip");
        byte[] updateContent = connection.getUpdate();
        if (updateContent.length == 0) {
            throw new IOException("Unable to download update from server");
        }
        serializer.write(updateZip, updateContent);
        logger.debug("Update downloaded");
        controller.setStatus("Update downloaded");
        return new ZipFile(updateZip);
    }

    private void unzip(ZipFile file) throws IOException {
        logger.info("Unzipping update...");
        controller.setStatus("Unzipping update...");
        Enumeration e = file.entries();
        root.mkdir();

        while (e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            logger.debug("Extracting {}", entry.toString());
            controller.setStatus("Extracting " + entry.toString() + "...");
            if (entry.isDirectory()) {
                new File(entry.getName()).mkdir();
            } else {
                File current = new File(entry.getName());
                current.createNewFile();
                int buffer = 2048;
                try (
                    BufferedInputStream in = 
                        new BufferedInputStream(file.getInputStream(entry));
                    FileOutputStream fos = new FileOutputStream(current);
                    BufferedOutputStream out = 
                        new BufferedOutputStream(fos, buffer);
                ) {
                    int count;
                    byte[] data = new byte[buffer];
                    while ((count =in.read(data, 0, buffer)) != -1) {
                        out.write(data, 0, count);
                    }
                    out.flush();
                }
            }
        }
        logger.debug("Update unzipped");
        controller.setStatus("Update unzipped");
    }

    private void copyDirectory(File source, File destination) 
                                            throws IOException {
        File[] files = source.listFiles();
        for (File file : files) {
            File destFile = new File(destination, file.getName());
            if (file.isDirectory()) {
                destFile.mkdir();
                copyDirectory(file, destFile);
            } else {
                logger.info("Copying file: {}", file.getAbsolutePath());
                controller.setStatus("Copying file: " + file.getAbsolutePath());
                Files.copy(file.toPath(), destFile.toPath(), 
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private void cleanUp() throws IOException {
        logger.info("Performing clean up...");
        controller.setStatus("Performing clean up...");
        File update = new File("update.zip");
        update.delete();
        empty(root);
        root.delete();
        logger.debug("Clean up completed");
        controller.setStatus("Clean up completed.");
    }

    private void empty(File directory) throws IOException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                empty(file);
            } else {
                file.delete();
            }
        }
    }

    private File getJar(File root) {
        logger.debug("Finding jar...");
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.getName().contains("client") && 
                    file.getName().endsWith(".jar")) {
                logger.debug("Jar found: {}", file);
                return file;
            }
        }
        logger.warn("Jar archive not found");
        return null;
    }

    private void restart() throws IOException {
        logger.info("Restarting to update");
        connection.close();
        System.exit(0);
    }
}
