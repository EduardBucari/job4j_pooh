package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TopicService implements Service {
    ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedDeque<String>>> topics = new ConcurrentHashMap<>();
    public static final String GET = "GET";
    public static final String POST = "POST";

    @Override
    public Resp process(Req req) {
        String status = "";
        Resp resp;
        if (POST.equals(req.httpRequestType())) {
            if (topics.get(req.getSourceName()) != null) {
                for (ConcurrentLinkedDeque<String> queue : topics.get(req.getSourceName()).values()) {
                    queue.add(req.getParam());
                }
            }
        }
        if (GET.equals(req.httpRequestType())) {
            if (topics.containsKey(req.getSourceName())) {
                if (topics.get(req.getSourceName()).containsKey(req.getParam())) {
                    if (!topics.get(req.getSourceName()).get(req.getParam()).isEmpty()) {
                        return new Resp(topics.get(req.getSourceName()).get(req.getParam()).poll(), "200");
                    } else {
                        status = "204";
                    }
                } else {
                    topics.get(req.getSourceName()).put(req.getParam(), new ConcurrentLinkedDeque<>());
                    status = "204";
                }
            } else {
                topics.put(req.getSourceName(), new ConcurrentHashMap<>());
                topics.get(req.getSourceName()).put(req.getParam(), new ConcurrentLinkedDeque<>());
            }
        }
        resp = new Resp("", status);
        return resp;
    }
}
