import org.slf4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LoggerManager {
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void logging(String type, String message) throws ExecutionException, InterruptedException {
        // 別スレッドで動かすクラスをインスタンス化
        LoggerThread lt = new LoggerThread(type, message, logger);
        // 新しいシングルスレッド作る
        ExecutorService service = Executors.newSingleThreadExecutor();
        // Future<?>クラスに新しいシングルスレッドで動かすクラスを登録
        Future<?> f = service.submit(lt);
        // 実行
        f.get();
        // 実行終了後スレッドを切る
        service.shutdown();
    }
}
