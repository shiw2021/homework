package hziee.spc.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateDataFile {

    public static void generateDataFile(Object data, String filePath) {
        filePath="C:/Users/shi/IdeaProjects/finalfinal/pydoc/date";
        try {
            // 将Java对象转换为字符串
            String dataStr = data.toString();

            // 创建一个新文件并写入数据
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(dataStr);
            writer.close();

            System.out.println("Data file generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating data file: " + e.getMessage());
        }
    }

}
