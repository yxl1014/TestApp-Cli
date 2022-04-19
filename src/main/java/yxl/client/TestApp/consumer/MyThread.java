package yxl.client.TestApp.consumer;

public class MyThread extends Thread {

    private MyRunnable run;

    public MyThread(Runnable target) {
        super(target);
        this.run = (MyRunnable) target;
    }

    public void stop0() {
        super.interrupt();
        this.stop();
        run.close();
    }
}
