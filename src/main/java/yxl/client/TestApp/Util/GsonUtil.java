package yxl.client.TestApp.Util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.Ut;

import java.util.List;


public class GsonUtil {
    private static Gson gson=new Gson();

    public static<T> String toJson(T t){
        return gson.toJson(t);
    }
    public static<T> T fromJson(String json,Class<T> clazz){
        return gson.fromJson(json,clazz);
    }
    public static<T> List<Task> list_TaskfromJson(String json){return gson.fromJson(json,new TypeToken<List<Task>>(){}.getType());}
    public static<T> List<Ut> list_UtfromJson(String json){return gson.fromJson(json,new TypeToken<List<Ut>>(){}.getType());}
}
