package yxl.client.TestApp.fileio;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ReadFile {
    public List<String> readFile(File file) {
        BufferedReader reader = null;
        StringBuilder data = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;

            //按行读，并把每次读取的结果保存在line字符串中
            while ((line = reader.readLine()) != null) {
                data.append(line);
                System.out.println(line);
            }
            //关闭流
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //当由于异常情况，上面关闭流程序没有执行时
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] data1 = data.toString().split("_EOF_");
        List<String> datas = new ArrayList<>();
        Collections.addAll(datas, data1);
        return datas;
    }
}
