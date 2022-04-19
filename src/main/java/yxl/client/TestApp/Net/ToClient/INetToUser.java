package yxl.client.TestApp.Net.ToClient;

import yxl.client.TestApp.entity.T_result;
import yxl.client.TestApp.entity.Task;
import yxl.client.TestApp.entity.Ut_working;

public interface INetToUser {
    T_result sendMessage(Task task, Ut_working utw);
}
