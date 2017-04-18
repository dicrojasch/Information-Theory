#include "msp.h"
#include "LCD_5110.h"

LCD_5110 myScreen;
char buf[5];
int entradas = 0;

void setup () {
     pinMode( 19, OUTPUT );
     Serial.begin(9600);
     Serial.flush(); 
}
void loop () {
  
     if( Serial.available() > 0 ){
          entradas++;
          int dato;
          dato = Serial.read();
          if( dato > 0 ){
              digitalWrite( 19, HIGH );             
          }else{            
              digitalWrite( 19, LOW );
          }
     }
     
     
     
     

}
