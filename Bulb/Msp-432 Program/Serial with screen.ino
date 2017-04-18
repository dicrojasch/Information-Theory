#include "msp.h"
#include "LCD_5110.h"

LCD_5110 myScreen;
char buf[5];
int entradas = 0;

void setup () {
     pinMode( 19, OUTPUT );
     Serial.begin(9600);
     Serial.flush(); 
     myScreen.begin();
     myScreen.setFont(0);
     myScreen.text(0, 0, "Iniciando..");      
     //while(!Serial) { ; }
}
void loop () {
  
     if( Serial.available() > 0 ){
       myScreen.clear();
         myScreen.text(0, 0, "Entro");      
          entradas++;
          int dato;
          dato = Serial.read();
          if( dato > 0 ){
              digitalWrite( 19, HIGH );
              myScreen.text(0, 4, "on");              
          }else{            
              digitalWrite( 19, LOW );
              myScreen.text(0, 4, "off"); 
          }
          sprintf(buf,"%d",dato);   
          myScreen.text(0, 2, buf);
          sprintf(buf,"%d",entradas);   
          myScreen.text(2, 2, buf);

     }
     
     
     
     

}
