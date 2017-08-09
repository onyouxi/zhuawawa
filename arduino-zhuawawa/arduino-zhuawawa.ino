
int Pin1 = 2;
int Pin2 = 3;
int Pin3 = 4;
int Pin4 = 5;
int Pin5 = 6;
int Pin6 = 7;

void setup() {
   Serial.begin(9600);
   while (!Serial) {   
    ; // wait for serial port to connect. Needed for native USB port only
   }
   pinMode(Pin1, OUTPUT);//加币
   pinMode(Pin2, OUTPUT);//向左移动
   pinMode(Pin3, OUTPUT);//向右移动
   pinMode(Pin4, OUTPUT);//向上移动
   pinMode(Pin5, OUTPUT);//向下移动
   pinMode(Pin6, OUTPUT);//抓娃娃
   digitalWrite(Pin1, HIGH); 
   digitalWrite(Pin2, HIGH);
   digitalWrite(Pin3, HIGH); 
   digitalWrite(Pin4, HIGH);
   digitalWrite(Pin5, HIGH); 
   digitalWrite(Pin6, HIGH);
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

}

//终止当前行为
void cmdEnd(){
  Serial.println("success");
}

//向左移动
void left(){
  Serial.println("success");
}

//向右移动
void right(){
  Serial.println("success");
}

//向上移动
void up(){
  Serial.println("success");
}

//向下移动
void down(){
  Serial.println("success");
}

//抓娃娃
void zhua(){
  Serial.println("success");
}

//抓娃娃结束
void zhuaEnd(){
   Serial.println("end");
}

//增加一次抓娃娃的机会
void add(){
  Serial.println("success");
}

