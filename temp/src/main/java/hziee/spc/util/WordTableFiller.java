package hziee.spc.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import hziee.spc.entity.Applicant;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class WordTableFiller {
    //返回填充后的文档
    public String fill(Applicant obj) throws IOException {
        String docPath = "C:\\Users\\shi\\IdeaProjects\\finalfinal\\input.docx";
        String outputPath = "C:\\Users\\shi\\IdeaProjects\\finalfinal\\output.docx";
        // 加载文档
        XWPFDocument doc = new XWPFDocument(new FileInputStream(docPath));
        String fieldName = "";
        try {
            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    //记录row的索引
                    AtomicInteger rowIndex = new AtomicInteger(table.getRows().indexOf(row));
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String text = cell.getText();
                        //如果包含#，则替换
                        if (text.contains("#")) {
                            String[] parts = text.split("#");
                            fieldName = parts[1].trim();
                            //首字母大写
                            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                            Object value = obj.getClass().getMethod("get" + fieldName).invoke(obj);
                            if (value != null) {
                                if (value instanceof Date) {
                                    XWPFParagraph para = cell.getParagraphs().get(0);
                                    para.removeRun(0);
                                    para.createRun().setText(new java.text.SimpleDateFormat("yyyy-MM-dd").format((Date) value));
                                } else if (value instanceof Boolean) {
                                    String result = (Boolean) value ? "是" : "否";
                                    cell.removeParagraph(0);
                                    cell.setText(result);
                                } else {
                                    //替换文本
                                    cell.removeParagraph(0);
                                    cell.setText(value.toString());
                                }
                            }
                        }
                        if (text.contains("$address")) {
                            String address = obj.getJuZhuProvince() + obj.getJuZhuCity();
                            cell.removeParagraph(0);
                            cell.setText(address);
                        }
                        if (text.contains("$hasRelation")) {
                            String result;
                            if (obj.getHasRelationship()) {
                                result = "是";
                                result += " " + obj.getRelator();
                            } else {
                                result = "否";
                            }
                            cell.removeParagraph(0);
                            cell.setText(result);
                        }
                        //填充教育背景,起止日期、就读学校、所学专业
                        //找到第一行，第一列
                        if (text.contains("&edu")) {
                            obj.getBackgroundEdus().iterator().forEachRemaining(backgroundEdu -> {
                                XWPFTableRow row1 = table.getRow(rowIndex.get());
                                String startEnd = backgroundEdu.getDateStart().toString() + "-" + backgroundEdu.getDateEnd().toString();
                                XWPFTableCell cell1 = row1.getCell(1);
                                cell1.removeParagraph(0);
                                cell1.setText(startEnd);
                                XWPFTableCell cell2 = row1.getCell(2);
                                cell2.removeParagraph(0);
                                cell2.setText(backgroundEdu.getSchool());
                                XWPFTableCell cell3 = row1.getCell(3);
                                cell3.removeParagraph(0);
                                cell3.setText(backgroundEdu.getMajor());
                                rowIndex.getAndIncrement();
                            });

                        }
                        //填充工作经历
                        if (text.contains("&work")) {
                            obj.getBackgroundWorkings().iterator().forEachRemaining(backgroundWorking -> {
                                XWPFTableRow row1 = table.getRow(rowIndex.get());
                                String startEnd = backgroundWorking.getDateStart().toString() + "-" + backgroundWorking.getDateEnd().toString();
                                XWPFTableCell cell1 = row1.getCell(1);
                                cell1.removeParagraph(0);
                                cell1.setText(startEnd);
                                XWPFTableCell cell2 = row1.getCell(2);
                                cell2.removeParagraph(0);
                                cell2.setText(backgroundWorking.getCompany());
                                XWPFTableCell cell3 = row1.getCell(3);
                                cell3.removeParagraph(0);
                                cell3.setText(backgroundWorking.getPosition());
                                //离职原因
                                XWPFTableCell cell4 = row1.getCell(4);
                                cell4.removeParagraph(0);
                                cell4.setText(backgroundWorking.getResignReasion());
                                rowIndex.getAndIncrement();
                            });

                        }
                        //填充家庭成员
                        if (text.contains("&family")) {
                            obj.getApplicantsRelationships().iterator().forEachRemaining(backgroundFamily -> {
                                XWPFTableRow row1 = table.getRow(rowIndex.get());
                                XWPFTableCell cell1 = row1.getCell(1);
                                cell1.removeParagraph(0);
                                cell1.setText(backgroundFamily.getName());
                                XWPFTableCell cell5 = row1.getCell(2);
                                cell5.removeParagraph(0);
                                cell5.setText(String.valueOf(backgroundFamily.getAge()));
                                XWPFTableCell cell2 = row1.getCell(3);
                                cell2.removeParagraph(0);
                                cell2.setText(backgroundFamily.getRelationship());
                                XWPFTableCell cell3 = row1.getCell(4);
                                cell3.removeParagraph(0);
                                cell3.setText(backgroundFamily.getCompany());
                                XWPFTableCell cell4 = row1.getCell(5);
                                cell4.removeParagraph(0);
                                cell4.setText(backgroundFamily.getPosition());
                                rowIndex.getAndIncrement();
                            });

                        }
                        //保证人签名
                        if (text.contains("baozhengren")) {
                            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                                List<XWPFRun> runs = paragraph.getRuns();
                                for (XWPFRun xwpfRun : runs) {
                                    if (xwpfRun.getText(0).contains("baozhengren")) {
                                        ResourceLoader resourceLoader = new DefaultResourceLoader();
                                        Resource resource = resourceLoader.getResource("classpath:static/" + obj.getSignatureUrl());
                                        xwpfRun.setText("", 0);
                                        xwpfRun.addPicture(resource.getInputStream(), XWPFDocument.PICTURE_TYPE_JPEG, "sig.jpg", Units.toEMU(70), Units.toEMU(30));
                                    }
                                }

                            }
                        }
                        //插入头像
                        if (text.contains("$photo")) {
                            //相对路径
                            ResourceLoader resourceLoader = new DefaultResourceLoader();
                            Resource resource = resourceLoader.getResource("classpath:static/" + obj.getPortraitUrl());
                            cell.removeParagraph(0);

                            XWPFParagraph paragraph = cell.addParagraph();
                            XWPFRun run = paragraph.createRun();
                            run.addPicture(resource.getInputStream(), XWPFDocument.PICTURE_TYPE_JPEG, "1.jpg", Units.toEMU(33*2), Units.toEMU(48*2));
                        }
                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                String text = p.getText();
                for (XWPFRun run : runs) {
                    if (run.getText(0) != null && run.getText(0).contains("ApplyingFor")) {
                        System.out.println(run.getText(0));
                        fieldName = run.getText(0);
                        //首字母大写
                        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Object value = obj.getClass().getMethod("get" + fieldName).invoke(obj);
                        if (value != null) {
                            if (value instanceof Date) {
                                run.setText(new java.text.SimpleDateFormat("yyyy-MM-dd").format((Date) value), 0);
                            } else {
                                run.setText(value.toString(), 0);
                            }
                        }
                    }
                    if (run.getText(0) != null && run.getText(0).contains("Date")) {

                        System.out.println(run.getText(0));
                        fieldName = run.getText(0);
                        //首字母大写
                        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Object value = obj.getClass().getMethod("get" + fieldName).invoke(obj);
                        if (value != null) {
                            if (value instanceof Date) {
                                run.setText(new java.text.SimpleDateFormat("yyyy-MM-dd").format((Date) value), 0);
                            } else {
                                run.setText(value.toString(), 0);
                            }
                        }
                    }
                }
            }

            // 保存文档

            FileOutputStream out = new FileOutputStream(outputPath);

            doc.write(out);
            out.close();
        } catch (Exception e) {
            System.out.println(fieldName);
            e.printStackTrace();
        } finally {
            doc.close();
        }
        return outputPath;

    }
}
