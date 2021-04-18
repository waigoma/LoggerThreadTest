package Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/*
SLF4JとLogBackをRunnableで使ってみた

Thread:
https://engineer-club.jp/java-thread 参考

SLF4J & LogBack:
https://qiita.com/opengl-8080/items/49719f2d35171f017aa9 参照
*/
public class Main {
    //  このクラスをクラス型で定義(logger用)
    private static final Class<?> main = Main.class;

    // logger実行クラス(シングルトン)
    public static final LoggerManager LOGGER_MANAGER = new LoggerManager();
    // 検証用に別のlogger作ってみた
    private static final Logger log = LoggerFactory.getLogger(main);

    public static void main(String[] args){
        try {
            // logging時にclass入れるようにした
            LOGGER_MANAGER.logging("info", "Mainからこんにちは1", main);
            // Subクラスで動かしてみる
            new Sub().start();

            LOGGER_MANAGER.logging("info", "Mainからこんにちは2", main);

            // ログファイルが実際に書き出されるか実験用
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 100; j++) {
                    LOGGER_MANAGER.logging("warn", "Mainのforからこんにちは" + j, main);
                }
                log.error("別のロガー建ててみたよ");
                Thread.sleep(1000);
            }

            LOGGER_MANAGER.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*
loggerはわんちゃん実行した場所のクラス拾ってくれないかな～みたいな感じで使ってみたけどやっぱダメだった((
loggerしたクラスを知るにはいちいちクラス情報与えないとダメかな？
便利なことにたくさんlogger作っても同じファイル(.log)に保存されるっぽい
 */