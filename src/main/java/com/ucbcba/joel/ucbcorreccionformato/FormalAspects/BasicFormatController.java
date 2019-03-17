package com.ucbcba.joel.ucbcorreccionformato.FormalAspects;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BasicFormatController {
    @RequestMapping("/api/basicFormat/{fileName:.+}")
    public List<BasicFormatReport> getBasicMisstakes(@PathVariable String fileName, @RequestParam(value="figureTableIndexPageEnd") String figureTableIndexPageEnd
            , @RequestParam(value="annexedPage") String annexedPage) {
        List<BasicFormatReport> basicFormatReports = new ArrayList<>();
        String dirPdfFile = "uploads/"+fileName;
        PDDocument pdfdocument = null;
        try {
            pdfdocument = PDDocument.load( new File(dirPdfFile) );
            BasicFormatDetector formatErrorDetector = new BasicFormatDetector(pdfdocument);
            formatErrorDetector.analyzeBasicFormat(figureTableIndexPageEnd,annexedPage);
            basicFormatReports = formatErrorDetector.getBasicFormatReports();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return basicFormatReports;
    }
}
