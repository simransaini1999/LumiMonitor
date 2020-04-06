import pyrebase
from getpass import getpass
import vlc
import math
import time

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

def get_song():
    songName = db.child("songPlaying/"+login['localId']+"/songName").get()
    return songName.val()

def get_state():
    songState = db.child("songPlaying/"+login['localId']+"/songState").get()
    return songState.val()
    
    
def get_song_file(songName):
    if(songName == "Alphabet Song"):
       songFile = "The ABC Song.mp3" 
       return (songFile)
    elif (songName == "Old MacDonald"):
       songFile = "Old MacDonald.mp3" 
       return (songFile)
    elif (songName == "Wheels on the Bus"):
       songFile = "Wheels On The Bus.mp3" 
       return (songFile)
    elif (songName == "Twinkle Twinkle Little Star"):
       songFile = "Twinkle Twinkle Little Star.mp3" 
       return (songFile)
    elif (songName == "Down by the Bay"):
       songFile = "Down By The Bay.mp3" 
       return (songFile)
    else:
     songFile = "none"
     return songFile

songName = get_song()
songState = get_state()
fileName = get_song_file(songName)
    
if (fileName != "none" and songState == "Play"):
    player = vlc.MediaPlayer(fileName)
    song_len = player.get_length()
    print ("Playing ", fileName)
    print ("Song length" , song_len)
    player.play()
else:
    player = vlc.MediaPlayer()

    
while True:
    check_song = get_song()
    check_state = get_state()
   
    if (songName != check_song and check_song != "No Song Selected!"): #change song
         player.stop()
         songName = check_song
         fileName = get_song_file(songName)
         player = vlc.MediaPlayer(fileName)
         player.play()
    elif (check_state == "Stop"):
         player.stop()
    elif (check_state == "Pause"):
         player.pause()         
    elif (songName == check_song and check_state == "Play"):
         player.play()
                  
    song_pos = round(player.get_position(),2)
    print (song_pos)
    if (song_pos == 1.0):
        print ("Song ", songName," has finished playing")
        player.stop()
        a = {"songName": "No Song Selected", "songState": "Stop"}    
        try:
            db.child("songPlaying/"+login['localId']).update(a)
            print("Database writing success!")
        except:
            print ("Database cannot be written, check the permission on database")
        time.sleep (1.0)
            
            
        
    
        
            
        
        
        
        
    
        

    

    
       
    
    
    
    
    
    





    
    
    

    

