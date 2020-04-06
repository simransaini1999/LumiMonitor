import random
import pyrebase  
import time
import math
import board
import neopixel
import busio
import adafruit_tsl2561
from getpass import getpass

#pip3 install pyrebase

def databaseinit():
    global db
    global login
    config = {
        "apiKey": "AIzaSyAATblfFfI17pRFTFmk7V0SXDTQV10FUDI",
        "authDomain": "lumimonitor-6745f.firebaseapp.com",
        "databaseURL": "https://lumimonitor-6745f.firebaseio.com",
        "projectId": "lumimonitor-6745f",
        "storageBucket": "lumimonitor-6745f.appspot.com",
        "messagingSenderId": "89734507168",
        "appId": "1:89734507168:web:6644f871187a08c3b777c5",
        "measurementId": "G-KC3JKW9Y9L",
        "serviceAccount": "/home/pi/Desktop/LumiMonitor/serviceAccountCredentials.json"
      }
    
    firebase = pyrebase.initialize_app(config)
    auth = firebase.auth()
    
    email = input("Enter Your Email Address: ")
    password = getpass("Enter Password: ")
    login = auth.sign_in_with_email_and_password(email, password)
    
    print ("success!")
    db = firebase.database()
    

def get_values():
    red = db.child("lumiColour/"+login['localId']+"/red").get()
    green = db.child("lumiColour/"+login['localId']+"/green").get()
    blue = db.child("lumiColour/"+login['localId']+"/blue").get()
    affect = db.child("lumiColour/"+login['localId']+"/affectState").get()
    print("red: ",red.val())
    print("blue: ",blue.val())
    print("green: ",green.val())
    print("green: ",green.val())
    print("affect: ",affect.val())
    return(green.val(),red.val(),blue.val(),affect.val())




def main():
    databaseinit()
    
    i2c = busio.I2C(board.SCL, board.SDA)
    tsl = adafruit_tsl2561.TSL2561(i2c)
    
    pixel_pin = board.D18
    num_pixels = 16
    ORDER = neopixel.GRB
    
    pixels = neopixel.NeoPixel(pixel_pin, num_pixels, brightness=0.3, auto_write=False,
                             pixel_order=ORDER)
    
    
    print("Configuring TSL2561...")
    tsl.enabled = True
    time.sleep(1)

    # Set gain 0=1x, 1=16x
    tsl.gain = 0

    # Set integration time (0=13.7ms, 1=101ms, 2=402ms, or 3=manual)
    tsl.integration_time = 1

    print("Getting readings...")

    # Get raw (luminosity) readings individually
    broadband = tsl.broadband
    infrared = tsl.infrared
    
    
    
    
    while True:
        lux = tsl.lux
        ref = get_values()
        affect = ref[3]
        
        if (lux > 750 and not None) and (affect != 1):
            print("Lux = {}".format(lux))
            pixels.fill((0, 0, 0))
            pixels.show()
            time.sleep(0.25)
            
        else:        
            colours = get_values()
            green = colours[0]
            red = colours[1]
            blue = colours[2]
            
              
            pixels.fill((red, green, blue))
            pixels.show()
            time.sleep(0.25)
    
    
if __name__ == '__main__':
    main()

    
    
    
    
    
    
    


  

