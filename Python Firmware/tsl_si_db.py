import time
import board
import busio
import adafruit_si7021
import random
import pyrebase
from getpass import getpass
import adafruit_tsl2561
import datetime
import math

#pip3 install pyrebase

def timestamp():
    timestp = datetime.datetime.now().timestamp()
    return timestp


config = {
    "apiKey": "AIzaSyAATblfFfI17pRFTFmk7V0SXDTQV10FUDI",
    "authDomain": "lumimonitor-6745f.firebaseapp.com",
    "databaseURL": "https://lumimonitor-6745f.firebaseio.com",
    "projectId": "lumimonitor-6745f",
    "storageBucket": "lumimonitor-6745f.appspot.com",
    "messagingSenderId": "89734507168",
    "appId": "1:89734507168:web:6644f871187a08c3b777c5",
    "measurementId": "G-KC3JKW9Y9L",
    "serviceAccount": "/home/pi/Desktop/serviceAccountCredentials.json"

}
  
  
firebase = pyrebase.initialize_app(config)
  
auth = firebase.auth()
  
print ("hello")
  
email = input("Enter Your Email Address: ")
password = getpass("Enter Password: ")
  
login = auth.sign_in_with_email_and_password(email, password)
  
print ("success!")
db = firebase.database()

i2c = busio.I2C(board.SCL, board.SDA)
sensor = adafruit_si7021.SI7021(i2c)
tsl = adafruit_tsl2561.TSL2561(i2c)


print("Configuring TSL2561...")
# Enable the light sensor
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
    humidity = sensor.relative_humidity
    temperature = sensor.temperature
    print("\nTemperature: %0.1f C" % temperature)
    print("Humidity: %0.1f %%" % humidity)
    if lux is not None:
        print("Lux = {}".format(lux))
    else:
        print("Lux value is None. Possible sensor underrange or overrange.")
        lux = 0
        
        
    a = {"awakenTime": str(int(math.ceil(timestamp()))), "humidity": str(int(math.ceil(humidity))),
       "lightValue": str(int(math.ceil(lux))), "temperature": str(int(math.ceil(temperature)))
       }
    
    try:
     db.child("userdata/"+login['localId']).push(a)
     print("Database writing success!")
    except:
     print ("Database cannot be written, check the permission on database")
    
    time.sleep(30.0)
    













