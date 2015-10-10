**Method Type:** POST   
**URL:** /battleship/v1/auth/login   
**Response JSON:**   
```JSON
{    
  "userID":"myUserID",   
  "settings":{     
    "displayName":"Guest",   
    "wins":0,   
    "losses":0,   
    "defaultBackgroundImage":"foo.jpg"   
  }   
}
```   
**Success Code:** ```201```   
**Description:** Logs in as guest, creating new User with userID returned   

---

**Method Type:** POST   
**URL:** /battleship/v1/auth/login/:googleID   
**Response JSON:**   
```JSON
{     
  "userID":"myUserID",   
  "settings":{     
    "displayName":"myGoogle+DisplayName",   
    "wins":4,   
    "losses":2,   
    "defaultBackgroundImage":"foo.jpg"   
  }   
}
```  
**Success Code:** ```201``` if new User created, ```200``` if User found in backend   
**Description:** Logs in as Google+ name. If name not found on backend, new User created with default settings. If User found, return User settings.   

---

**Method Type:** POST   
**URL:** /battleship/v1/games   
**Request JSON:**  
```JSON 
{     
  "userID":"myUserID",   
  "challengedUserIDs":[     
    "challengedID1",   
    "challengedID2",   
    "challengedID3"   
  ],   
  "gameSettings":{     
    "boardType":"myBoardType",    
    "boardSize":10   
  }   
}   
```
**Response JSON:**
```JSON   
{    
  "gameID":"myGameID"   
}   
```
**Success Code:** ```201```  
**Failure Code:** ```404``` if challenged User ID not found   
**Description:** User creates a new game and challenges users to the game. Backend instantiates game with desired settings and invites users to game.   

---

**Method Type:** PUT   
**URL:** /battleship/v1/games/respond/:gameID  
**Request JSON:**   
```JSON
{     
  "userID":"myUserID",   
  "challengeResponse":true   
}   
```
**Response JSON:**  
```JSON 
{}   
```
**Success Code:** ```200```   
**Failure Code:** ```404``` if game ID not found   
**Description:** User creates a new game and challenges users to the game. Backend instantiates game with desired settings and invites users to game.   

---

**Method Type:** PUT   
**URL:** /battleship/v1/games/join/:gameID  
**Request JSON:**   
```JSON
{     
  "userID":"myUserID",   
  "gameID":"gameID"  
}   
```
**Response JSON:**  
```JSON 
{    
  "gameID":"gameID",
  "ships":[
    "SHIP_CARRIER",
    "SHIP_DESTROYER",
    "TANK",
    "SHIP_SUBMARINE"
  ]
}   
```
**Success Code:** ```200```   
**Failure Code:** ```404``` if game ID not found   
**Description:** User creates a new game and challenges users to the game. Joins game, and sends back to User a list of ships User can place on board  

---
**Method Type:** PUT   
**URL:** /battleship/v1/games/:gameID/move   
**Request JSON:**   
```JSON
{     
  "userID":"myUserID",   
  "x":3,   
  "y":4   
}   
```
**Response JSON:**  
```JSON
{    
  "moveResult":"MISS"   
}    
```
**Success Code:** ```200```   
**Failure Code:** ```410``` if already fired that spot, ```404``` if spot not on board or move invalid     
**Description:** User strikes point at X and Y. Returns result of move: ```SHIP_SUNK```, ```HIT```, ```MISS```   

---

**Method Type:** GET   
**URL:** /battleship/v1/games/:gameID/state   
**Request JSON:**   
```JSON
{     
  "userID":"myUserID" 
}   
```
**Response JSON:**  
```JSON
{   
  "userID":"myUserID",
  "ships":[
    [
      [1,2], [1,3], [1,4]
    ],
    [
      [8,2], [8,3], [8,4], [8,5]
    ],
    [
      [4,0], [5,0]
    ]
  ],
  "moves":[
    {"x":2, "y":4, "result":"HIT"},
    {"x":2, "y":9, "result":"MISS"},
    {"x":5, "y":4, "result":"HIT"}
  ],
  "gameState":{
    "state":"GAME_OVER", "player":"guest2348vs3X2"
  }
}    
```
**Success Code:** ```200```   
**Failure Code:** ```404``` if game doesn't exist
**Description:** User gets game state for own board (only sees his/her ships, and his/her moves and results). Result takes form: ```HIT```, ```MISS```. GameState state object takes form: ```WAITING_FOR_PLAYERS```, ```PLAYER_TURN```, ```GAME_OVER```, while GameState player object points to a player whose turn it is, or player who won


---

**Method Type:** PUT      
**URL:** /battleship/v1/games/:gameID/setpiece   
**Request JSON:**   
```JSON
{     
  "userID":"myUserID",
  "piece":[
    [1,2],
    [1,3],
    [1,4]
  ],
  "pieceType":"SHIP_CARRIER"
}   
```
**Response JSON:**  
```JSON
{}    
```
**Success Code:** ```200```   
**Failure Code:** ```410``` if could not place piece      
**Description:** Places 1 ship on board at a time. Validates each ship as it comes in, making sure there is no overlap between ships, and PieceType matches the tiles available (ie, Ships can only be placed over water, and tanks can only be placed over land). PieceTypes are: ```SHIP_CARRIER```, ```SHIP_DESTROYER```, ```SHIP_SUBMARINE```, ```TANK```, etc.


