#include <SoftwareSerial.h>
#include<Servo.h>
#include "DHT.h"
#include <Wire.h> 
#include <LiquidCrystal_I2C.h> //1602LCD 驱动  引用I2C库
//#define DHTPIN 13     // 选择数字输入口

LiquidCrystal_I2C lcd(0x27,20,4);   //寻找LCD地址值
/*
int Led =22; //定义LED 接口
int Shock = A14; //定义震动传感器接口
int val;//定义数字变量val
*/

//定义时间变量
unsigned long startTime = 0;
unsigned long currentTime = 0;
bool timerRunning = false;

/*#define DHTTYPE DHT11   // DHT 11接口
DHT dht(DHTPIN, DHTTYPE);
*/

SoftwareSerial blueTooth(14,15); // RX, TX
int x=0;
int y=0;
int action;
// 定义五种运动状态
#define STOP      0
#define FORWARD   1
#define BACKWARD  2
#define TURNLEFT  3
#define TURNRIGHT 4

/*
//定义云台的两个输入端口
Servo up,down;      
int a=40,b=0;
int c;
*/

// 定义需要用到的引脚
int leftMotor1 = 16;
int leftMotor2 = 17;
int rightMotor1 = 18;
int rightMotor2 = 19;

// 定义超声波传感器引脚
const int trigPin1 = A0;//front
const int echoPin1 = A1;
const int trigPin2 = A2;//left
const int echoPin2 = A3;
const int trigPin3 = A4;//right
const int echoPin3 = A5;
// 定义led引脚
    //const int ledPin = 8;

// 定义迷宫地图的大小
const int mazeSizeX = 10;    // 迷宫的行数
const int mazeSizeY = 10;    // 迷宫的列数

// 定义小车当前位置的坐标
int currentX = 0;            // 当前位置的X坐标
int currentY = 0;            // 当前位置的Y坐标

// 定义小车当前面向的方向
int currentDirection = FORWARD;  // 初始方向为向前

// 定义迷宫地图数组
int maze[mazeSizeX][mazeSizeY];

// 定义记录小车动作和坐标的数组
const int maxSteps = 100;  // 最大步数
int steps = 0;             // 当前步数
int moves[maxSteps];       // 记录动作的数组
int coordinatesX[maxSteps];  // 记录X坐标的数组
int coordinatesY[maxSteps];  // 记录Y坐标的数组

//定义布偶形结束变量state

bool state =0;

// 定义探宝显示模块
#define tcs230_S0 2
#define tcs230_S1 3
#define tcs230_S2 4
#define tcs230_S3 5
#define tcs230_sensorOut 7
#define tcs230_LED 6
int redPin=12; //红色设定9号引脚
int greenPin=11; //10号引脚连接绿色
int bluePin=10; //11号连接蓝色
int frequency=0;
// 规定避障距离
int obstacleDistance = 5;  // 超声波传感器探测的障碍物距离
//电机速度，可以根据需要调整
int speed = 50;
int turnspeed = 100;
int flag = 0;
long duration;
int distance1, distance2, distance3;
bool switchState = true;
void setup() {
  // 初始化串口通信
  Serial.begin(9600);
  // 设置控制电机的引脚为输出状态
  blueTooth.begin(9600); // 设置蓝牙模块的通信波特率
  pinMode(leftMotor1, OUTPUT);
  pinMode(leftMotor2, OUTPUT);
  pinMode(rightMotor1, OUTPUT);
  pinMode(rightMotor2, OUTPUT);
  
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
  pinMode(trigPin3, OUTPUT);
  pinMode(echoPin3, INPUT);

  //pinMode(ledPin, OUTPUT);

  pinMode(tcs230_S0, OUTPUT);
  pinMode(tcs230_S1, OUTPUT);
  pinMode(tcs230_S2, OUTPUT);
  pinMode(tcs230_S3, OUTPUT);
  pinMode(tcs230_LED, OUTPUT);
  pinMode(tcs230_sensorOut, INPUT);
  digitalWrite(tcs230_S0,HIGH);
  digitalWrite(tcs230_S1,LOW);
  digitalWrite(tcs230_LED,HIGH);

/*  pinMode(Led, OUTPUT); //定义LED 为输出接口
  pinMode(Shock, INPUT); //定义震动传感器为输出接口 

  up.attach(9,1000,2000);        //上面的舵机附加到引脚10      
  down.attach(8,1000,2000);       //下面的舵机附加到引脚9
  up.write(a);                   //上面的舵机初始角度为60° 
  down.write(b);                 //下面的舵机初始角度为30°

  Serial.println(F("DHTxx test!"));
  lcd.init(); // 初始化LCD 
  lcd.backlight(); //
  dht.begin();
  startTime = millis(); // 记录开始时间
  timerRunning = true; // 启动计时器 */


  // 初始化迷宫地图数组
  for (int i = 0; i < mazeSizeX; i++) {
    for (int j = 0; j < mazeSizeY; j++) {
      maze[i][j] = 0;  // 0代表未探索的位置
    }
  }


  // 初始化记录动作和坐标的数组
  resetMovesAndCoordinates();
}

void loop() {
  int frontDistance = measureDistance(trigPin1, echoPin1);;    // 获取左侧超声波传感器的距离
  int leftDistance = measureDistance(trigPin2, echoPin2);;  // 获取前方超声波传感器的距离
  int rightDistance = measureDistance(trigPin3, echoPin3);;  // 获取右侧超声波传感器的距离
  Serial.print("Distance 1: ");
  Serial.print(frontDistance);
  Serial.print(" cm\t");

 Serial.print("Distance 2: ");
  Serial.print(leftDistance);
  Serial.print(" cm\t");

  Serial.print("Distance 3: ");
  Serial.print(rightDistance);
  Serial.println(" cm");
  //led();

 /* val = analogRead(Shock); //震动LED
  if (val >1000) //这个800是随意取的，能区分是哪边就可以
  {
    digitalWrite(Led, HIGH);
  }
  else
  {
    digitalWrite(Led,LOW );
  }
  
 if (blueTooth.available()) {
    // 当手机发送数据到蓝牙模块时
    c = blueTooth.read(); // 读取数据
    blueTooth.print("Received: "); // 打印接收到的消息至蓝牙设备
    blueTooth.println(c);

    Serial.print("Received: "); // 打印接收到的消息至串口监视器
    Serial.println(c);
  }*/

  int CL = color();
  CL = color();
  CL = color();
  CL = color();
  // 判断避障策略
  /*if(CL==2 || CL==3){
    motorRun(STOP);
    action=STOP ;
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println(action);
    delay(1000); 
    motorRun(TURNRIGHT);
    action=TURNRIGHT;
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println(action);
    delay(220);
    motorRun(STOP);
    action=STOP;
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println(action);
    delay(1000);
    motorRun(TURNRIGHT);
    action=TURNRIGHT;
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println(action);
    delay(210);

  } else*/ if (rightDistance > 30 && rightDistance < 400) {
    // 如果右侧无障碍物，向右转
    recordMove(TURNRIGHT);  // 记录动作
    currentDirection = (currentDirection + 1) % 4; // 更新小车的面向
    motorRun(TURNRIGHT);
    delay(200);
    motorRun(BACKWARD);
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println("turn right");
    delay(200);
  } else if (frontDistance > obstacleDistance && frontDistance < 400) {
    // 如果前方无障碍物，前进
    motorRun(BACKWARD);
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println("forward");
    recordMove(FORWARD);  // 记录动作
    updatePosition(FORWARD);
    delay(150);
  } else if (leftDistance > 20 && leftDistance < 400) {
    // 如果左侧无障碍物，向左转
    motorRun(TURNLEFT);
    recordMove(TURNLEFT);  // 记录动作
    currentDirection = (currentDirection + 3) % 4; // 更新小车的面向    
    delay(200);
    motorRun(BACKWARD);
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println("turn left");
    delay(200);
  } else {
    // 如果无路可走，连续运行两次右转
   //flag=0;
   //switchState=0;
    //led();
   motorRun(STOP);
    delay(1000); 
    motorRun(TURNRIGHT);
    recordMove(TURNRIGHT);  // 记录动作
    currentDirection = (currentDirection + 1) % 4; // 更新小车的面
    delay(220);
    motorRun(STOP);
    delay(1000);
    motorRun(TURNRIGHT);
    recordMove(TURNRIGHT);  // 记录动作
    currentDirection = (currentDirection + 1) % 4; // 更新小车的面
    blueTooth.print("action: ");   // 打印计数至串口监视器
    blueTooth.println("go back");
    delay(210);
  }
  CL = 0;
  motorRun(STOP);
  delay(250);

 
  //读取湿度
 /* float h = dht.readHumidity();
  // 读取温度为摄氏度
  float t = dht.readTemperature();
  


  // Compute heat index in Celsius (isFahreheit = false)
  float hic = dht.computeHeatIndex(t, h, false);
  lcd.setCursor(0, 1);
  lcd.print(F("Humidity: "));
  lcd.print(h);
  lcd.setCursor(0, 0);
  lcd.print(F("Temper:"));
  lcd.print(t);
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
  
  if (state == 1 && timerRunning) {
    timerRunning = false; // 停止计时器
    lcd.print("Elapsed Time:");
    displayTime(currentTime); // 最后一次显示时间
    delay(3000);
     lcd.clear();
  }*/
}

// 运动控制函数
void motorRun(int cmd) {
  switch (cmd) {
    case FORWARD:
      digitalWrite(leftMotor1, LOW);
      digitalWrite(leftMotor2, HIGH);
      digitalWrite(rightMotor1, LOW);
      digitalWrite(rightMotor2, HIGH);
      break;
    case BACKWARD:
      digitalWrite(leftMotor1, HIGH);
      digitalWrite(leftMotor2, LOW);
      digitalWrite(rightMotor1, HIGH);
      digitalWrite(rightMotor2, LOW);
      break;
    case TURNLEFT:
      digitalWrite(leftMotor1, LOW);
      digitalWrite(leftMotor2, HIGH);
      digitalWrite(rightMotor1, HIGH);
      digitalWrite(rightMotor2, LOW);
      break;
    case TURNRIGHT:
      digitalWrite(leftMotor1, HIGH);
      digitalWrite(leftMotor2, LOW);
      digitalWrite(rightMotor1, LOW);
      digitalWrite(rightMotor2, HIGH);
      break;
    default:
      digitalWrite(leftMotor1, LOW);
      digitalWrite(leftMotor2, LOW);
      digitalWrite(rightMotor1, LOW);
      digitalWrite(rightMotor2, LOW);
  }
}

// 获取超声波传感器测距结果
int measureDistance(int trigPin, int echoPin) {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2); 
  
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  
  duration = pulseIn(echoPin, HIGH);
  
  // 将声波传播时间转换为距离（厘米）
  int distance = duration / 58.2; // 将声波传播速度设定为约每微秒58.2厘米
  
  return distance;
}
//岔路探测函数
/*void forkDectect(int rightDistance,int frontDistance,int leftDistance)
{
  if ((rightDistance > 35 && rightDistance < 400!=0)+(frontDistance > obstacleDistance && frontDistance < 400!=0)+(leftDistance > 35 && leftDistance < 400!=0)>=2)
  {
    flag=1;
  }
}

//亮灯逻辑函数
void led()
{
    if (flag == 1 && switchState == 0) {
    switchState = !switchState;
  }
    if (switchState) {
    digitalWrite(ledPin, HIGH);
  } else {
    digitalWrite(ledPin, LOW);
  }
}*/

//TCS230颜色传感器获取RGB值
int tcs230_Getcolor(char data)
{
  int frequency = 0;
  switch(data)
  {
    case 'R':
    {
      digitalWrite(tcs230_S2,LOW);
      digitalWrite(tcs230_S3,LOW);
      int frequency1 = pulseIn(tcs230_sensorOut, LOW);
      frequency1 = map(frequency1, 25, 72, 255, 0);
      frequency=frequency1;
      break;
    }
    case 'G':
    {
      digitalWrite(tcs230_S2,HIGH);
      digitalWrite(tcs230_S3,HIGH);
      int frequency2 = pulseIn(tcs230_sensorOut, LOW);
      frequency2 = map(frequency2, 30, 90, 255, 0);
      frequency=frequency2;
      break;
    }
    case 'B':
    {
      digitalWrite(tcs230_S2,LOW);
      digitalWrite(tcs230_S3,HIGH);
      int frequency3 = pulseIn(tcs230_sensorOut, LOW);
      frequency3 = map(frequency3, 25, 70, 255, 0);
      frequency=frequency3;
      break;
    }
    default:
      return -1;
  }
  
  if (frequency < 0)
    frequency = 0;
  if (frequency > 255)
    frequency = 255;
  return frequency;
}

int color(){
  if (tcs230_Getcolor('R')) {
    Serial.println("RED");
     analogWrite(redPin,255);
     analogWrite(greenPin,0);
     analogWrite(bluePin,0);
     return 1;
 
  }
  else if (tcs230_Getcolor('B')) {
    Serial.println("BLUE");
    analogWrite(redPin,0);
     analogWrite(greenPin,0);
     analogWrite(bluePin,255);
     return 2;
 
  }
  else if (tcs230_Getcolor('G')) {
    Serial.println("GREEN");
    analogWrite(redPin,0);
     analogWrite(greenPin,255);
     analogWrite(bluePin,0);
     return 3;
  }
  else return 0;
}

/*void gimbalControler(int a,int b)
{
    int value = c; // 获取当前元素的值

    if (value == 1) {
      a=a+30;          //当遍历的元素为1，上舵机向上转动30°
    } else if (value == 2) {
      a=a-30;            //当遍历的元素为2，上舵机向下转动30°
    } else if (value == 3) {
      b=b+30;            //当遍历的元素为3，下舵机向左转动30°
    } else if (value == 4) {
      b=b-30;            //当遍历的元素为4，下舵机向右转动30°
    }
  

  delay(1000); // 等待1秒钟
  up.write(a);
  down.write(b);
}*/

// 重置动作和坐标数组
void resetMovesAndCoordinates() {
  steps = 0;
  for (int i = 0; i < maxSteps; i++) {
    moves[i] = 0;
    coordinatesX[i] = 0;
    coordinatesY[i] = 0;
  }
}

// 更新迷宫地图
void updateMaze() {
  maze[currentX][currentY] = 1;  // 当前位置已探索
}

// 更新小车的位置
void updatePosition(int cmd) {
  switch (cmd) {
    case FORWARD:
      if (currentDirection == FORWARD && currentX < mazeSizeX - 1) {
        currentX++;
      } else if (currentDirection == BACKWARD && currentX > 0) {
        currentX--;
      } else if (currentDirection == TURNLEFT && currentY > 0) {
        currentY--;
      } else if (currentDirection == TURNRIGHT && currentY < mazeSizeY - 1) {
        currentY++;
      }
      break;
  }
}

// 记录动作
void recordMove(int move) {
  if (steps < maxSteps) {
    moves[steps] = move;
    coordinatesX[steps] = currentX;
    coordinatesY[steps] = currentY;
    steps++;
  }
}

//运动显示函数
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