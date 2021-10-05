#include <Arduino.h>

#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

#include <ESP8266HTTPClient.h>

#include <WiFiClient.h>

ESP8266WiFiMulti WiFiMulti;

void setup() {

  Serial.begin(115200);
  // Serial.setDebugOutput(true);


  for (uint8_t t = 4; t > 0; t--) {
    //Serial.printf("[SETUP] WAIT %d...\n", t);
    //Serial.flush();
    delay(1000);
  }

  WiFi.mode(WIFI_STA);
  WiFiMulti.addAP("SSID", "PASSWORD");

}

void loop() {
  // wait for WiFi connection
  if ((WiFiMulti.run() == WL_CONNECTED)) {

    WiFiClient client;

    HTTPClient http;

    String temp = "";
    String humidity = "";
    String serialRead = "";
    while (Serial.available()) {
      serialRead = Serial.readString();
    }

    int indexSplit = serialRead.indexOf('/');
    if (indexSplit != -1) {
      temp = serialRead.substring(0, indexSplit);
      humidity = serialRead.substring(indexSplit, serialRead.length() - 1);
    }

    if (http.begin(client, "127.0.0.1?temp=" + temp + "&humidity=" + humidity)) { // HTTP

      // start connection and send HTTP header
      int httpCode = http.GET();

      // httpCode will be negative on error
      if (httpCode > 0) {
        // HTTP header has been send and Server response header has been handled


        // file found at server
        if (httpCode == HTTP_CODE_OK || httpCode == HTTP_CODE_MOVED_PERMANENTLY) {
          String payload = http.getString();
          Serial.println(payload);
        }
      } else {
        //Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }

      http.end();
    } else {
      //Serial.printf("[HTTP} Unable to connect\n");
    }
  }

  delay(10000);
}
