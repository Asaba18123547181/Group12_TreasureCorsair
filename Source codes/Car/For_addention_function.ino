#include "DHT.h"
#include <Wire.h> 
#include <LiquidCrystal_I2C.h> //1602LCD 驱动  引用I2C库
#define DHTPIN 13    // 选择数字输入口

LiquidCrystal_I2C lcd(0x27,20,4);   //寻找LCD地址值

#define DHTTYPE DHT11   // DHT 11接口

unsigned long startTime = 0;
unsigned long currentTime = 0;
bool timerRunning = false;
//记得把这里删掉 这里是设定的结束信号
const int buzzerPin = 12;

DHT dht(DHTPIN, DHTTYPE);

int Led =22; //定义LED 接口
int Shock = A14; //定义震动传感器接口
int val;//定义数字变量val

void setup() {
  pinMode(Led, OUTPUT); //定义LED 为输出接口
  pinMode(Shock, INPUT); //定义震动传感器为输出接口  
  Serial.begin(9600);
  Serial.println(F("DHTxx test!"));
  lcd.init(); // 初始化LCD 
  lcd.backlight(); //
  dht.begin();
  startTime = millis(); // 记录开始时间
  timerRunning = true; // 启动计时器

  
}

void loop() {
  
  delay(2000);

  //读取湿度
  float h = dht.readHumidity();
  // 读取温度为摄氏度
  float t = dht.readTemperature();
  


  // Compute heat index in Celsius (isFahreheit = false)
  float hic = dht.computeHeatIndex(t, h, false);
  lcd.setCursor(0, 1);
  lcd.print(F("Humidity: "));
  lcd.print(h);
  Serial.print(h);
  lcd.setCursor(0, 0);
  lcd.print(F("Temper:"));
  lcd.print(t);
  Serial.print(t);
  lcd.print(F("C "));
  delay(3000);
  lcd.clear();


   if (timerRunning) {
    currentTime = millis() - startTime; // 计算经过的时间
    lcd.print("Elapsed Time:");
    displayTime(currentTime); // 显示时间
    delay(1000);
     lcd.clear();
  }
  
  if (digitalRead(buzzerPin) == LOW && timerRunning) {
    timerRunning = false; // 停止计时器
    lcd.print("Elapsed Time:");
    displayTime(currentTime); // 最后一次显示时间
    delay(3000);
     lcd.clear();
  }



  val = analogRead(Shock); 
  if (val >300) //这个800是随意取的，能区分是哪边就可以
  {
    digitalWrite(Led, HIGH);
    Serial.write("震动");
  }
  else
  {
    digitalWrite(Led,LOW );
    Serial.write("meizhen");
  }
}
void displayTime(unsigned long time) {
  unsigned long seconds = time / 1000; // 将毫秒转换为秒
  unsigned int minutes = seconds / 60; // 计算分钟数
  seconds = seconds % 60; // 计算剩余的秒数

  lcd.setCursor(0, 1);
  if (minutes < 10) {
    lcd.print("0");
  }
  lcd.print(minutes);
  lcd.print(":");
  if (seconds < 10) {
    lcd.print("0");
  }
  lcd.print(seconds);
}

