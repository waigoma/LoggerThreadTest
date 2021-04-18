package Test;

import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/*
LoggerManagerとLoggerThreadでやってるけど、まとめられるよね、多分。
どっちがいいのかはわからない。
 */
public class LoggerManager {
    // スレッドを定義 logger専用スレッドにして定数化
    private final ExecutorService loggerService = Executors.newSingleThreadExecutor(r -> new Thread(r, "LoggerThread"));

    public void logging(String type, String message, Class<?> clazz) {
        try {
            // 別スレッドで動かすクラスをインスタンス化
            LoggerThread lt = new LoggerThread(type, message, LoggerFactory.getLogger(clazz));
            // スレッドに↑インスタンスをFuture<?>クラスに代入
            Future<?> f = loggerService.submit(lt);
            // 実行
            f.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void shutdown(){
        loggerService.shutdown();
    }
}
