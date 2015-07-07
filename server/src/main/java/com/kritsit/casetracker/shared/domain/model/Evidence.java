package com.kritsit.casetracker.shared.domain.model;

import com.kritsit.casetracker.shared.domain.FileSerializer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class Evidence implements Serializable {
    private static final long serialVersionUID = 10L;
    private String description;
    private File serverFile;
    private File localFile;
    private byte[] file;

    public Evidence(String description, File serverFile, File localFile) {
        this.description = description;
        this.serverFile = serverFile;
        this.localFile = localFile;
        this.file = null;
    }

    public Evidence(String description, File serverFile) {
        this(description, serverFile, null);
    }

    // Accessor methods:
    public String getDescription() {
        return description;
    }

    public File getLocalFile() {
        return localFile;
    }

    public File getServerFile() {
        return serverFile;
    }

    public String getServerFileLocation() {
        return serverFile.getAbsolutePath();
    }

    public String getLocalFileLocation() {
        return localFile.getAbsolutePath();
    }

    public byte[] getByteFile() {
        return file;
    }

    // Mutator methods:
    public void setDescription(String description) {
        this.description = description;
    }

    public void setServerFile(File serverFile) {
        this.serverFile = serverFile;
    }

    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }

    public void setByteFile(byte[] file) {
        this.file = file;
    }

    public void setByteFile(File file) throws IOException {
        FileSerializer serializer = new FileSerializer();
        this.file = serializer.serialize(file);
    }

    @Override
    public int hashCode() {
        int fileHash = 0;
        if (getServerFile() != null) {
            fileHash += getServerFile().hashCode();
        }
        if (getLocalFile() != null) {
            fileHash += getLocalFile().hashCode();
        }
        return (description.hashCode() + fileHash) / 3;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        String result = description + " ";
        if (getServerFile() != null) {
            result += "(" + getServerFileLocation() + ")";
        } else {
            result += "(" + getLocalFileLocation() + ")";
        }
        return result;
    }
}
