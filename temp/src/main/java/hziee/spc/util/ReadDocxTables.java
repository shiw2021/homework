package hziee.spc.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

public class ReadDocxTables {
    public static void main(String[] args) throws Exception {
        String path = "C:/Users/shi/IdeaProjects/finalfinal/input.docx";
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(path));
        ReadDocxTables app = new ReadDocxTables();
        app.readRuns(doc);
//        app.readParagraphs(doc);

        app.saveDocument(app.replaceText(doc), "C:/Users/shi/IdeaProjects/finalfinal/output.docx");
    }

    public void readParagraphs(XWPFDocument doc) throws Exception {
        for (XWPFParagraph p : doc.getParagraphs()) {
            System.out.println(p.getText());
        }
    }

    public void readRuns(XWPFDocument doc) throws Exception {
        for (XWPFParagraph p : doc.getParagraphs()) {
            for (XWPFRun run : p.getRuns()) {
                System.out.println(run.getText(0));
            }
        }
    }


    public XWPFDocument replaceText(XWPFDocument doc) throws Exception {
        for (XWPFParagraph p : doc.getParagraphs()) {
            for (XWPFRun run : p.getRuns()) {
                String text = run.getText(0);
                System.out.println(text);
                if (text != null && text.contains("PositionApplyingFor")) {
                    System.out.println("find it");
                    text = text.replace("PositionApplyingFor", "test");
                    run.setText("123", 0);
                }
            }
        }
        return doc;
    }

    public void readTables(XWPFDocument doc) throws Exception {
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    System.out.println(cell.getText());
                }
            }
        }
    }

    public Boolean saveDocument(XWPFDocument doc, String filename) throws Exception {
        try {
            doc.write(new FileOutputStream(filename));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

