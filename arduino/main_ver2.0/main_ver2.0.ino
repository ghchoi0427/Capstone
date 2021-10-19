//________________________________온습도 센서
#include <DHT.h>
#define DHTPIN A1
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);
//______________________________________________LCD 
#include <LiquidCrystal_I2C_Hangul.h>
LiquidCrystal_I2C_Hangul lcd(0x27,16,2);
#include <Wire.h> 
//__________________________________________________블루투스
#include <SoftwareSerial.h>
SoftwareSerial bluetooth(2,3);
SoftwareSerial bt2(4,5);
// _________________________________________________________________dts-300 온도센서
#include<SPI.h>
  
#define OBJECT  0xA0      // 대상 온도 커맨드
#define SENSOR  0xA1      // 센서 온도 커맨드

const int chipSelectPin  = 10;
unsigned char Timer1_Flag = 0;
int  iOBJECT, iSENSOR;  // 부호 2byte 온도 저장 변수 
//________________________________________________________________________________시간 측정
unsigned long previousMillis = 0;
unsigned long currentMillis = 0; 

void setup() {
  
  digitalWrite(chipSelectPin , HIGH);    // SCE High Level
  pinMode(chipSelectPin , OUTPUT);        // SCE OUTPUT Mode
  SPI.setDataMode(SPI_MODE3);            // SPI Mode 
  SPI.setClockDivider(SPI_CLOCK_DIV16);  // 16MHz/16 = 1MHz
  SPI.setBitOrder(MSBFIRST);             // MSB First
  SPI.begin();                           // Initialize SPI
    
  delay(500);                             // Sensor initialization time 
  Timer1_Init();                          // Timer1 setup : 500ms(2Hz) interval
  interrupts();                           // enable all interrupts

  Serial.begin(115200);
 dht.begin();
  bluetooth.begin(9600);
  bt2.begin(9600);

lcd.init(); 
lcd.backlight();
lcd.setCursor(0,1); 
  lcd.print("Lamp:");
  
lcd.setCursor(5,1);
  lcd.print("OFF");  
lcd.setCursor(9,1);
lcd.print("Pump");
lcd.setCursor(13,1);
lcd.print("Off");
bt2.write("1");

}

int SPI_COMMAND(unsigned char cCMD)
{
    unsigned char T_high_byte, T_low_byte;
    digitalWrite(chipSelectPin , LOW);  // SCE Low Level
    delayMicroseconds(10);              // delay(10us)
    SPI.transfer(cCMD);                // transfer  1st Byte
    delay(10);                          // delay(10ms)        
    T_low_byte = SPI.transfer(0x22);   // transfer  2nd Byte
    delay(10);                          // delay(10ms) 
    T_high_byte = SPI.transfer(0x22);  // transfer  3rd Byte
    delayMicroseconds(10);              // delay(10us)
    digitalWrite(chipSelectPin , HIGH); // SCE High Level 
    
    return (T_high_byte<<8 | T_low_byte);  // 온도값 return 
}
ISR(TIMER1_OVF_vect){        // interrupt service routine (Timer1 overflow)
  TCNT1 = 34286;            // preload timer : 이 값을 바꾸지 마세요.
  Timer1_Flag = 1;          // Timer 1 Set Flag
}
void Timer1_Init(void){
  TCCR1A = 0;
  TCCR1B = 0;
  TCNT1 = 34286;            // preload timer 65536-16MHz/256/2Hz
  TCCR1B |= (1 << CS12);    // 256 prescaler 
  TIMSK1 |= (1 << TOIE1);   // enable timer overflow interrupt
}

void loop() {

  if(Timer1_Flag){                                       // 500ms마다 반복 실행(Timer 1 Flag check)
    Timer1_Flag = 0;                                    // Reset Flag
    iOBJECT = SPI_COMMAND(OBJECT);     // 대상 온도 Read 
    delay(10);                                           // 10ms : 이 라인을 지우지 마세요 
    iSENSOR = SPI_COMMAND(SENSOR);      // 센서 온도 Read
  }
  
  currentMillis = millis();
   int h = dht.readHumidity();
  int t = dht.readTemperature();
  unsigned long timer;
// 줄 1, 2 중 택일 
//아래줄1 그래프 보기 용
//Serial.print(float(iSENSOR)/100,2);Serial.print("  ");Serial.println(float(iOBJECT)/100,2);
  //아래줄 2
  //Serial.print("Ambient = "); Serial.print(float(iSENSOR)/100,2);Serial.println("℃ ");Serial.print("Object = "); Serial.print(float(iOBJECT)/100,2);Serial.println("℃ ");Serial.print("Humidity = ");Serial.print(h);Serial.println("% ");Serial.println();
  Serial.print(int((iOBJECT)/100));
  Serial.print("/");
  Serial.println(h);
  //Serial.print(t);Serial.println("℃ Sub");
 // Serial.println();
delay(500);

lcd.setCursor(0,0);
  lcd.print(float(iOBJECT)/100,2); lcd.print("C");delay(500);
lcd.setCursor(9,0);
lcd.print(h);lcd.print("%");
  
if(h>50){timer = 3600000;};
if(h<=50){timer = 1800000;};
  
if(currentMillis - previousMillis > timer){
 previousMillis = currentMillis;
   bt2.write("1");delay(2000);bt2.write("0");
 };

if(float(iOBJECT)/100,2<23){
 bluetooth.write("1");lcd.setCursor(5,1);lcd.print("ON ");
 };
if(float(iOBJECT)/100,2>=28){
  bluetooth.write("0");lcd.setCursor(5,1);lcd.print("OFF");
 };
if(float(iOBJECT)/100,2>=30){
  bt2.write("1");lcd.setCursor(13,1);lcd.print(" ON");
};
if(float(iOBJECT)/100,2<30) {
  bt2.write("0");lcd.setCursor(13,1);lcd.print("Off");
};



}
