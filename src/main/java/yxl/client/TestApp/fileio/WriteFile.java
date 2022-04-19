package yxl.client.TestApp.fileio;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class WriteFile {

    public boolean writeFile(File f, String data) {
        boolean isok;

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            writer.write(data+"_EOF_");
            //关闭流
            writer.close();
            isok = true;
        } catch (IOException e) {
            e.printStackTrace();
            isok = false;//抛错，并
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isok;
    }

    public boolean appendFile(File f, String data){
        boolean isok;

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(f,true));
            writer.write(data+"$EOF#");
            //关闭流
            writer.close();
            isok = true;
        } catch (IOException e) {
            e.printStackTrace();
            isok = false;//抛错，并
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isok;
    }
}
