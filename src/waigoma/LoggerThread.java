import org.slf4j.Logger;

public class LoggerThread implements Runnable {
    private final String type;
    private final String msg;
    private final Logger logger;

    /**
     * 別スレッド対応logger
     *
     * @param type String ログタイプ
     * @param msg String メッセージ
     * @param logger Logger logger
     */
    public LoggerThread(String type, String msg, Logger logger){
        this.type = type;
        this.msg = msg;
        this.logger = logger;
    }

    @Override
    public void run() {
        switch (type){
            case "info":
                logger.info(msg);
                break;
            case "warn":
                logger.warn(msg);
                break;
            case "error":
                logger.error(msg);
                break;
        }
    }
}
