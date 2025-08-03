package org.gbl.kernel.infra.rabbitmq.config;

public class RabbitMQProperties {
    private String exchange;
    private String queue;
    private String routingKey;
    private String dlx;
    private String dlqRoutingKey;
    private int ttl;

    public String exchange() {
        return exchange;
    }

    public String queue() {
        return queue;
    }

    public String routingKey() {
        return routingKey;
    }

    public String dlx() {
        return dlx;
    }

    public String dlqRoutingKey() {
        return dlqRoutingKey;
    }

    public int ttl() {
        return ttl;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setDlx(String dlx) {
        this.dlx = dlx;
    }

    public void setDlqRoutingKey(String dlqRoutingKey) {
        this.dlqRoutingKey = dlqRoutingKey;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}