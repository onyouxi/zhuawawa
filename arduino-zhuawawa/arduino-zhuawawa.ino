

void setup() {
   Serial.begin(9600);
   while (!Serial) {   
    ; // wait for serial port to connect. Needed for native USB port only
   }
}

void loop() {
  while(Serial.available()>0){
    char cmd = Serial.read();
    delay(2);
    if(cmd == 'a'){
        Serial.println("liuwei");
    }else if(cmd == 'b'){
        Serial.println("liuwei123");
    }
  }
  
}
