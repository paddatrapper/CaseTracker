package com.kritsit.casetracker.shared.domain.model;

import com.kritsit.casetracker.shared.domain.FileSerializer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Evidence implements Serializable {
    private static final long serialVersionUID = 10L;
    private int id;
    private String description;
    private File serverFile;
    private File localFile;
    private byte[] file;

    public Evidence(int id, String description, File serverFile, File localFile) {
        this.id = id;
        this.description = description;
        this.serverFile = serverFile;
        this.localFile = localFile;
        this.file = null;
    }

    public Evidence(int id, String description, File serverFile) {
        this(id, description, serverFile, null);
    }

    // Accessor methods:
    public int getId() {
        return id;
    }

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
        if (file == null) {
            return new byte[0];
        } else {
            byte[] buffer = Arrays.copyOf(file, file.length);
            return buffer;
        }
    }

    // Mutator methods:
    public void setId(int id) {
        this.id = id;
    }

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
        if (file == null) {
            this.file = new byte[0];
        } else {
            byte[] buffer = Arrays.copyOf(file, file.length);
            this.file = buffer;
        }
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
        return (id + description.hashCode() + fileHash) / 3;
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
