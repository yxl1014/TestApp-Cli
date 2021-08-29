package yxl.client.TestApp.fileio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileImpl {

    @Autowired
    private CreateFile createFile;
    @Autowired
    private WriteFile writeFile;
    @Autowired
    private ReadFile readFile;

    public File createFile(String filename){
        return createFile.createFile(filename);
    }

    public File createTempFile(String filename){
        return createFile.createFile(filename);
    }

    public List<String> readFile(String filename) {
        return readFile.readFile(filename);
    }

    public boolean writeFile(File f, String data) {
        return writeFile.writeFile(f, data);
    }

    public boolean appendFile(File f, String data) {
        return writeFile.appendFile(f, data);
    }
}
