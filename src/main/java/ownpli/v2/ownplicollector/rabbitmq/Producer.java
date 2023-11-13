package ownpli.v2.ownplicollector.rabbitmq;

public interface Producer<T> {
    void produce(String exchange, String routingKey, T message);
}
