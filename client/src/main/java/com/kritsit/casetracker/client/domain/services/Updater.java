package com.kritsit.casetracker.client.domain.services;

import com.kritsit.casetracker.client.CaseTrackerClient;
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
    
    public Updater(IConnectionService connection) {
        this.connection = connection;
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
        copyDirectory(root, new File(""));
        return getJar(new File(""));
    }

    public void launch(File jar) throws IOException {
        String[] run = {"java", "-jar", jar.getAbsolutePath()};
        Runtime.getRuntime().exec(run);
        restart();
    }

    private ZipFile downloadUpdate() throws IOException {
        logger.info("Downloading update...");
        FileSerializer serializer = new FileSerializer();
        File updateZip = new File("update.zip");
        byte[] updateContent = connection.getUpdate();
        if (updateContent.length == 0) {
            throw new IOException("Unable to download update from server");
        }
        serializer.write(updateZip, updateContent);
        logger.debug("Update downloaded");
        return new ZipFile(updateZip);
    }

    private void unzip(ZipFile file) throws IOException {
        logger.info("Unzipping update...");
        Enumeration e = file.entries();
        root.mkdir();

        while (e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            logger.debug("Extracting {}", entry.toString());
            if (entry.isDirectory()) {
                new File(root, entry.getName()).mkdir();
            } else {
                File current = new File(root, entry.getName());
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
    }

    private void copyDirectory(File source, File destination) 
                                            throws IOException {
        logger.info("Copying files...");
        File[] files = source.listFiles();
        for (File file : files) {
            File destFile = new File(destination, file.getName());
            if (file.isDirectory()) {
                destFile.mkdir();
                copyDirectory(file, destFile);
            } else {
                Files.copy(file.toPath(), destFile.toPath(), 
                        StandardCopyOption.REPLACE_EXISTING);
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
