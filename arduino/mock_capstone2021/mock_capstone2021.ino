void setup() {
  Serial.begin(115200);
}

void loop() {
  long randomTempValue = random(20, 50);
  long randomHumidValue = random(30, 90);


  Serial.print(randomTempValue);
  Serial.print("/");
  Serial.println(randomHumidValue);

  delay(10000);
}
