# LoggerThreadTest

> マルチスレッド(ExecutorService)
1. 新しいスレッドを作る
2. implements Runnableをしているクラスをインスタンス化
3. スレッドに 2 を登録する
4. 実行

> SLF4J & LogBack
1. Loggerを作る
2. 1 を使ってログを流す


> 実際のコード

`LoggerManager.java`

```Java
public class LoggerManager {
    // 新しいスレッドを作る logger専用スレッドにして定数化
    // "LoggerThread" はスレッドの名前
    private final ExecutorService loggerService = Executors.newSingleThreadExecutor(r -> new Thread(r, "LoggerThread"));

    public void logging(String type, String message, Class<?> clazz) {
        try {
            // 新しいスレッドで動かすクラスをインスタンス化
            LoggerThread lt = new LoggerThread(type, message, LoggerFactory.getLogger(clazz));
            
            // 新しいスレッドに↑で作ったインスタンスを登録
            Future<?> f = loggerService.submit(lt);
            
            // 実行
            f.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 新しいスレッドを閉じるときに使用
    public void shutdown(){
        loggerService.shutdown();
    }
}
```
<br>

`LoggerThread.java`

```Java
public class LoggerThread implements Runnable {
    private final String type;
    private final String msg;
    private final Logger logger;

    // ログタイプ、メッセージ、Loggerをインスタンス化時代入
    public LoggerThread(String type, String msg, Logger logger){
        this.type = type;
        this.msg = msg;
        this.logger = logger;
    }

    // 別スレッドで実行するときに呼ばれる場所
    // 実際にログを流す場所
    @Override
    public void run() {
        switch (type){
            case "info":
                logger.info(msg); // infoログ
                break;
            case "warn":
                logger.warn(msg); // warningログ
                break;
            case "error":
                logger.error(msg); // errorログ
                break;
        }
    }
}
```

> 参考

・ [Javaのスレッド(Thread)を使いこなすコツを、基礎からしっかり伝授](https://engineer-club.jp/java-thread)  
・ [Logback 使い方メモ](https://qiita.com/opengl-8080/items/49719f2d35171f017aa9)
