package ownpli.v2.ownplicollector.rabbitmq;

import java.util.List;

public interface Producer<T> {
    void produce(String exchange, String routingKey, List<T> message);
}
