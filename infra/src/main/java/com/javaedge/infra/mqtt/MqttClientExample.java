package com.javaedge.infra.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class MqttClientExample {

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

        // 订阅主题
        mqttClient.subscribe(TOPIC, 0, new MqttPostPropertyMessageListener());
    }

    static class MqttPostPropertyMessageListener implements IMqttMessageListener {

        @Override
        public void messageArrived(String topic, MqttMessage payload) throws Exception {
            System.out.println("reply topic  : " + topic);
            System.out.println("reply payload: " + payload.toString());
        }

    }
}

