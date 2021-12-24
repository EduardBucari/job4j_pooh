package ru.job4j.pooh;

/**
 * class Req - класс, служит для парсинга входящего запроса.
 * httpRequestType - GET или POST. Он указывает на тип запроса.
 * poohMode - указывает на режим работы: queue или topic.
 * sourceName - имя очереди или топика.
 * param - содержимое запроса.
 */
public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {

        return new Req(null, null, null, null);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
