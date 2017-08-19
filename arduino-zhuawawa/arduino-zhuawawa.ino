
int Pin1 = 2;
int Pin2 = 3;
int Pin3 = 4;
int Pin4 = 5;
int Pin5 = 6;
int Pin6 = 12;

int currentPinNum=0;
long time=0;

void setup() {
   Serial.begin(9600);
   while (!Serial) {   
    ; // wait for serial port to connect. Needed for native USB port only
   }
   pinMode(Pin1, OUTPUT);//向前移动
   pinMode(Pin2, OUTPUT);//向左移动
   pinMode(Pin3, OUTPUT);//向右移动
   pinMode(Pin4, OUTPUT);//向后移动
   pinMode(Pin5, OUTPUT);//抓娃娃
   pinMode(Pin6, OUTPUT);//加币
  
}

void loop() {
  while(Serial.available()>0){
    char cmd = Serial.read();
    delay(2);
    if(cmd == 'a'){
        cmdEnd();
    }else if(cmd == 'b'){
        add();
    }else if(cmd == 'c'){
        left();
    }else if(cmd == 'd'){
        right();
    }else if(cmd == 'e'){
        up();
    }else if(cmd == 'f'){
        down();
    }else if(cmd == 'g'){
        zhua();
    }else if(cmd == 'h'){
        zhua();
    }
  }
  if(time != 0 && millis()-time >3000){
      cmdEnd();
  }
  
}

//终止当前行为
void cmdEnd(){
  if(currentPinNum==1){
    digitalWrite(Pin1, LOW);
  }else if(currentPinNum==2){
    digitalWrite(Pin2, LOW);
  }else if(currentPinNum==3){
    digitalWrite(Pin3, LOW);
  }else if(currentPinNum==4){
    digitalWrite(Pin4, LOW);
  }
  currentPinNum=0;
  time=0;
  Serial.println("s");
}

//向左移动
void left(){
  currentPinNum=2;
  digitalWrite(Pin2, HIGH);
  time=millis();
  Serial.println("s");
}

//向右移动
void right(){
  currentPinNum=3;
  digitalWrite(Pin3, HIGH);
  Serial.println("s");
}

//向上移动
void up(){
  currentPinNum=1;
  digitalWrite(Pin1, HIGH);
  Serial.println("s");
}

//向下移动
void down(){
  currentPinNum=4;
  digitalWrite(Pin4, HIGH);
  Serial.println("s");
}

//抓娃娃
void zhua(){
  digitalWrite(Pin5, HIGH);
  delay(200);
  digitalWrite(Pin5, LOW);
  Serial.println("s");
}

//抓娃娃结束
void zhuaEnd(){
   Serial.println("e");
}

//增加一次抓娃娃的机会
void add(){
   digitalWrite(Pin6, HIGH);
   delay(100);
   digitalWrite(Pin6, LOW);
   delay(100);
   digitalWrite(Pin6, HIGH);
   delay(100);
   digitalWrite(Pin6, LOW);
  Serial.println("s");
}

