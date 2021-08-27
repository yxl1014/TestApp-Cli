package yxl.client.TestApp.Util;

import yxl.client.TestApp.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class LocalList<T> {
    //task数据集合
    private List<T> lists;
    //上次取的下标的下一个
    private int lastindex = 0;

    //往前取值的下标
    private int limitindex = -1;

    //长度
    private int len;

    public LocalList(List<T> lists) {
        this.lists = lists;
        this.len = lists.size();
    }

    //取size大小的task
    public List<T> getTasks(int size) {
        List<T> datas = new ArrayList<>(size);
        int overindex = lastindex + size;
        for (int i = lastindex; i < len && i < overindex; i++) {
            datas.add(lists.get(i));
        }
        lastindex = overindex;
        return datas;
    }
    //默认取10个
    public List<T> getTasks() {
        int size=10;
        List<T> datas = new ArrayList<>(size);
        int overindex = lastindex + size;
        for (int i = lastindex; i < len && i < overindex; i++) {
            datas.add(lists.get(i));
        }
        lastindex = overindex;
        return datas;
    }

    //取最后取值下标前size大小的task
    public List<T> getLastTasks(int size) {
        List<T> datas = new ArrayList<>(size);
        limitindex = Math.max(lastindex - size, 0);
        for (int i = limitindex; i < len && i < lastindex; i++) {
            datas.add(lists.get(i));
        }
        return datas;
    }

    public void updateData(List<T> datas){
        this.lists =datas;
        this.len=datas.size();
        this.lastindex=0;
        this.limitindex=-1;
    }
}
