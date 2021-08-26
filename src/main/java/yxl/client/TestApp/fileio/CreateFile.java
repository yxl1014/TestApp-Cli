package yxl.client.TestApp.fileio;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
public class CreateFile {

    public File createFile(String filename) {
        String path= Objects.requireNonNull(this.getClass().getResource("/")).getPath();//获取当前路径
        File f=new File(path+"conf/"+filename);//创建文件类
        if (!f.exists()){//判断该文件是否存在
            try {
                f.createNewFile();//不存在则创建新文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public File createTempFile(String filename){
        String path= Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        File f=new File(path+"conf/temp/"+filename);
        if (!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        f.deleteOnExit();//设置虚拟机关闭时删除该文件
        return f;
    }
}
