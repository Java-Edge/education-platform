package com.javaedge.infra.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class MqttPublisherExample {

    private static final String BROKER_URI = "tcp://broker.emqx.io:1883";
    private static final String CLIENT_ID = "mqtt-java-client-example";
    private static final String TOPIC = "$iot/device1/user/fortest";

    public static void main(String[] args) throws MqttException {
        IMqttClient mqttClient = new MqttClient(BROKER_URI, CLIENT_ID);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        mqttClient.connect(options);
        System.out.println("Connected to MQTT broker");

        // Paho MQTT发布消息。
        String content = "{\"id\":\"1\",\"version\":\"1.0\",\"params\":{\"LightSwitch\":1}}";
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(0);
        mqttClient.publish(TOPIC, message);
    }
}

