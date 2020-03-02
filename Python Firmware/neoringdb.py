import random
import pyrebase  
import time
import math
import board
import neopixel
from getpass import getpass

#pip3 install pyrebase

config = {
        "apiKey": "AIzaSyAATblfFfI17pRFTFmk7V0SXDTQV10FUDI",
    "authDomain": "lumimonitor-6745f.firebaseapp.com",
    "databaseURL": "https://lumimonitor-6745f.firebaseio.com",
    "projectId": "lumimonitor-6745f",
    "storageBucket": "lumimonitor-6745f.appspot.com",
    "messagingSenderId": "89734507168",
    "appId": "1:89734507168:web:6644f871187a08c3b777c5",
    "measurementId": "G-KC3JKW9Y9L"
}
  
  
  
  
firebase = pyrebase.initialize_app(config)
  
auth = firebase.auth()
  
print ("hello")
  
email = input("Enter Your Email Address: ")
password = getpass("Enter Password: ")
  
login = auth.sign_in_with_email_and_password(email, password)
  
print ("success!")
db = firebase.database()

def get_values():
    red = db.child("lumiColour/"+login['localId']+"/red").get()
    green = db.child("lumiColour/"+login['localId']+"/green").get()
    blue = db.child("lumiColour/"+login['localId']+"/blue").get()
    print("red: ",red.val())
    print("blue: ",blue.val())
    print("green: ",green.val())
    return(green.val(),red.val(),blue.val())





pixel_pin = board.D18
num_pixels = 16
ORDER = neopixel.GRB

pixels = neopixel.NeoPixel(pixel_pin, num_pixels, brightness=0.3, auto_write=False,
                           pixel_order=ORDER)


while True:
    colours = get_values()
    green = colours[0]
    red = colours[1]
    blue = colours[2]
    pixels.fill((red, green, blue))
    pixels.show()
    time.sleep(0.25)

    
    
    
    
    
    
    


  

