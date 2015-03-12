package com.kritsit.casetracker.shared.domain.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class Evidence {
    private String description;
    private File serverFile;
    private File localFile;
    private BufferedImage image;

    public Evidence(String description, File serverFile, File localFile) {
        this.description = description;
        this.serverFile = serverFile;
        this.localFile = localFile;
        this.image = null;
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

    public String getServerFileLocation() {
        return serverFile.getAbsolutePath();
    }

    public String getLocalFileLocation() {
        return localFile.getAbsolutePath();
    }

    public BufferedImage getImage() {
        return image;
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        String result = "Evidence: ";
        result += description + " ";
        result += "(" + getServerFileLocation() + ")";
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evidence other = (Evidence) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.serverFile, other.serverFile);
    }
}
