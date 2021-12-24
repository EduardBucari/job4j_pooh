package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<String>> queue = new ConcurrentHashMap<>();
    public  static final String GET = "GET";
    public  static final String POST = "POST";

    @Override
    public Resp process(Req req) {
        Resp resp = new Resp("", "");
        if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedDeque<>());
            queue.get(req.getSourceName()).add(req.getParam());
        }
        if (GET.equals(req.httpRequestType())) {
            String status = "";
            if (!queue.get(req.getSourceName()).isEmpty()) {
                status = "202";
            }
            if (queue.get(req.getSourceName()).isEmpty()) {
                status = "204";
            }
            resp = new Resp(queue.get(req.getSourceName()).poll(), status);
        }
        return resp;
    }
}
