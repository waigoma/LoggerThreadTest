import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class Sub {
    // Mainから呼ばれる
    public void start() throws ExecutionException, InterruptedException {
        // Mainクラスが代入されてるloggerで別スレッドでinfoログを流す
        Main.LOGGER_MANAGER.logging("info", "Subからこんにちは1");
        // nextメソッドを呼ぶ
        next();
    }

    private void next() throws ExecutionException, InterruptedException {
        // loggerにSubクラスを代入してみる
        Main.LOGGER_MANAGER.setLogger(LoggerFactory.getLogger(Sub.class));
        // Subクラスが代入されているloggerで別スレッドでinfoログを流す
        Main.LOGGER_MANAGER.logging("info", "Subからこんにちは2");
    }
}