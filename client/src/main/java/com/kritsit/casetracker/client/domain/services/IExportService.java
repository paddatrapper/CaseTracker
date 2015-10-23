package com.kritsit.casetracker.client.domain.services;

import java.util.List;
import java.io.File;

public interface IExportService {
    void exportToPDF(List<String> headers, List<String[]> cells, File file);
}


