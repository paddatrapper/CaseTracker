package com.kritsit.casetracker.client.domain.services;

import java.util.List;
import java.io.File;
import com.kritsit.casetracker.shared.domain.model.Case;

public interface IExportService {
    void exportToPDF(List<String> headers, List<String[]> cells, File file);
    void exportCaseToPDF(Case c, File file);
}