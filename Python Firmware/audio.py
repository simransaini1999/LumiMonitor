import pyrebase
from getpass import getpass
import vlc

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
    
    
def get_song_file(songName):
    if(songName == "Alphabet Song"):
       songFile = "Alphabet_Song.mp3" 
       return (songFile)
    elif (songName == "Old MacDonald"):
       songFile = "Old_MacDonald.mp3" 
       return (songFile)
    else:
     songFile = "none"
     return songFile

songName = get_song()
fileName = get_song_file(songName)
    
if (fileName != "none"):
    player = vlc.MediaPlayer(fileName)
    player.play()
else:
    player = vlc.MediaPlayer()

    
while True:
    check_song = get_song()
    if (songName != check_song and check_song != "No Song Selected!"):
         player.stop()
         songName = check_song
         fileName = get_song_file(songName)
         player = vlc.MediaPlayer(fileName)
         player.play()
    elif (check_song == "No Song Selected!"):
         player.stop()
            
        
        
        
        
    
        

    

    
       
    
    
    
    
    
    





    
    
    

    

