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
    // SLF4Jのloggerをおいておくクラス(シングルトン)
    public static final LoggerManager LOGGER_MANAGER = new LoggerManager();
    // 検証用に別のlogger作ってみた
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        // SLF4JのLoggerFactoryでロガーを取得し(Mainクラス代入)LoggerManagerクラスにセットする
        LOGGER_MANAGER.setLogger(LoggerFactory.getLogger(Main.class));

        try {
            // Mainクラスが入ったロガーで別スレッドでinfoログを流す
            LOGGER_MANAGER.logging("info", "Mainからこんにちは1");
            // Subクラスで動かしてみる
            new Sub().start();
            // SubクラスでロガーにSubクラスを代入してMainから実行する
            LOGGER_MANAGER.logging("info", "Mainからこんにちは2");

            // ログファイルが実際に書き出されるか実験用
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 100; j++) {
                    LOGGER_MANAGER.logging("warn", "Mainのforからこんにちは" + j);
                }
                log.error("別のロガー建ててみたよ");
                Thread.sleep(1000);
            }
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