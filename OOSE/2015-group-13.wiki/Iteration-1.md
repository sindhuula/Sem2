## Vision Statement

The main aim of this project is to develop a client-server system to implement a derivative of the traditional board game "Battleship". The game will encompass a 2 player version where players can challenge each other and a single player mode in which a user plays against an AI. The game is played on a board with 10x10 squares where the player can place their ships. The player also has a view of their opponent's board(without the ships). The player's aim is to sink all of their opponent's ships before the opponent sinks all of their ships. You can find more details about this game [[here.|https://en.wikipedia.org/wiki/Battleship_%28game%29]]
The game can be further extended with features like having more than 2 players, earning more ships, etc.  This project will entail a Java based server and Android client.    

## Feature list

* User can login using his existing Google+ account, Facebook account, or play as guest   
* 1-player mode against computer AI       
* Game dashboard showing game invites, win/loss statistics   
* User records persist (via storage in database)   
* User can view previous game statistics on dashboard   
* Users can play against friends and other users by sending game invites via Facebook, or Google+   
* Game data will be cached for speed of access   
* User can play complete game of battleship   
* User displayed playing grid overlayed on background ocean image(default or user-selected)   
* User can tap grid square to fire (must tap twice to prevent accidental fire)   
* Grid square changes color (red/white/blue) depending on hit or miss   
* User can drag/drop to place ships on game board    
* User can view own game board or opponent's game board   

#### Extended features

* Users can chat in game mode  
* Users can ally with each other  
* AI for 1 player mode can be tuned for different levels of difficulty      
* 2+ player mode against combination of other users or AI  
* Explosion/miss game animations   

## Use cases

**1.1) User logs in**  
  _Actors:_ User  
  _Goal:_ Sign up to set up a record in the game server  
  * User visits landing page
  * User logs in on landing page with Facebook, Google+, or email address (or continues as guest)
  * Application contacts Facebook or Google+ Login API, logs user in
  * User is given unique user-id for backend identification within application for statistics, friends, etc.
  * User taken to game dashboard

**2.1) User creates new game**  
  _Actors:_ User  
  _Goal:_ Create new game, add players to game  
  * From game dashboard, user clicks "Create Game" button
  * User taken to game configuration screen
  * User selects game configurables:
    * Game board background
    * Number of players
  * User clicks "Play!" button
  * Game is created on backend with configured information  
  * User invites other players
    * Friends by name (Facebook, Google+, email)
      * Application uses respective API to send invite to friend
    * Random stranger from game server player queue
    * AI by selecting option
  * The invited user gets an invite (link) on their game dashboard

**2.2) User accepts/rejects game invite**  
  _Actors:_ User, Game client    
  _Goal:_ User can choose to join a game, or delete invite   
  * User receives game request from another user  
  * User selects accept/ reject button  
  * If request is accepted, user is redirected to the game board
    * Backend adds user to requested game        
  * If request is rejected, dialogue box simply closes

**2.3) User joins existing game**  
  _Actors:_ User, Game client  
  _Goal:_ New player is added to an existing game  
  * From game dashboard, user sees game invites
  * User clicks "Accept" button next to desired game invite
  * Backend adds user to requested game   
  * User taken to game screen  

**3.1) User plays game**
  * 3.1.1) User places ships  
    _Actors:_ User  
    _Goal:_ User places ships in desired position  
    * User quick-taps (single tap gesture) ship to rotate 90 degrees
    * User long-taps (tap and hold/drag gesture) ship to drag onto board
    * User presses "Ready" button  
    * Backend adds ships in desired positions to game board    
    * 3.1.1.b.1) if user releases ship outside of a viable space while placing it, the action does not register the user must repeat the action
    * 3.1.1.b.2) if user has yet to place their ships, "Ready" button is inactive

  * 3.1.2) User takes turn  
    _Actors:_ User  
    _Goal:_ User makes a guess on opponent's ship position and attacks it  
    * User taps grid square twice to fire 
    * Backend determines whether move is legal, and determines hit or miss   
    * Tapped square turns red on hit, and blue/white on miss
      * Extended feature: graphics
    * Game changes turn   
   * 3.1.2.b.1) if it is not a player's turn, their board controls are inactive
   * 3.1.2.b.2) if the player double taps a square to fire that is invalid (the player has already fired at that square or is a square on their own board), the action does not register and the user must repeat the action

  * 3.1.3) AI plays game  
    _Actors:_ Game client  
    _Goals:_ AI guesses user's ship position and attacks it  
    * Square on user's grid turns red if hit or blue/white if miss
  * 3.1.4) User wins/loses game  
    _Actors:_ Game client  
    _Goals:_ Game client informs user in the event of victory/ defeat  
    * User receives victory/defeat message
    * User is redirected to game dashboard
      * Extended feature: User's victory counter is incremented in game server database


## User Interface Sketches
### Player Login:
![Player Login](https://github.com/jhu-oose/2015-group-13/wiki/assets/image1.png)

### Player's Game Dashboard:
![Game Dashboard](https://github.com/jhu-oose/2015-group-13/wiki/assets/image2.png)

### Player Selects "Play Game" option:
![Play Game](https://github.com/jhu-oose/2015-group-13/wiki/assets/image3.png)

### Player Selects "1 Player Game" option:
![1 Player Game](https://github.com/jhu-oose/2015-group-13/wiki/assets/image4.png)

### Player Selects "2 Player Game" option:
![2 Player Game](https://github.com/jhu-oose/2015-group-13/wiki/assets/image5.png)

### Player's Board:
![Player's Board](https://github.com/jhu-oose/2015-group-13/wiki/assets/image6.png)

### Player's Board After few Moves:
![Player Later](https://github.com/jhu-oose/2015-group-13/wiki/assets/image7.png)

### Opponent's Board After few Moves:
![Opponent Later](https://github.com/jhu-oose/2015-group-13/wiki/assets/image8.png)

* Battleship image courtesy http://www.designpanoply.com/blog

## Architecture  
* Front end: Android
* Server options:
  * [Java Spring](http://spring.io/) and [Apache Tomcat](http://tomcat.apache.org/)
  * [Spark Java](http://sparkjava.com/)
* Database: [Amazon Web Service Relational Database Service - MySQL](http://aws.amazon.com/rds/mysql/)
* Compression: [Google Snappy](http://github.com/google/snappy)
* Cache options: 
  * [Memcached](http://memcached.org/)
  * [Redis](http://redis.io/)
* Packaging: [Docker](https://www.docker.com/)
* Frameworks / APIs:
  * [Google+ API Login](http://developers.google.com/+/mobile/android/sign-in?hl=en)
  * [Facebook API Login](http://developers.facebook.com/docs/facebook-login/android)

## Game Flow:
![Game Flow Diagram](https://github.com/jhu-oose/2015-group-13/wiki/assets/GameFlow.jpg)
