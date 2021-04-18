package Test;

import java.util.concurrent.ExecutionException;

public class Sub {
    private final Class<?> sub = Sub.class;

    // Mainから呼ばれる
    public void start() throws ExecutionException, InterruptedException {
        Main.LOGGER_MANAGER.logging("info", "Subからこんにちは1", sub);
        // nextメソッドを呼ぶ
        next();
    }

    private void next() {
        // Subクラスが代入されているloggerで別スレッドでinfoログを流す
        Main.LOGGER_MANAGER.logging("info", "Subからこんにちは2", sub);
    }
}