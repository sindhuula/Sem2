## Vision Statement

The main aim of this project is to develop a client-server system to implement a derivative of the traditional board game "Battleship". The game will encompass a 2 player version where players can challenge each other and a single player mode in which a user plays against an AI. The game can be further extended with features like having more than 2 players, earning more ships, etc.  This project will entail a Java based server and Android client.     

## Feature list

1) User can login using his existing google or facebook ID and password  
2) 1-player mode against computer AI       
3) User records persist (via storage in database)   
4) Users can play against friends and other users   
5) Game data will be cached for speed of access   

#### Extended features

1) Users can chat in game mode  
2) Users can ally with each other  
3) AI for 1 player mode can be tuned for different levels  
4) User can save game and quit (resume later)  
5) 2+ player mode against combination of other users or AI  
6) Explosion/miss game animations   

## Use cases

**1.1) User logs in**  
  _Actors:_ User  
  _Goal:_ Sign up to set up a record in the game server  
  * User visits landing page
  * User logs in on landing page with Facebook, Google+, or email address (or continues as guest)
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
  * User invites other players
    * Friends by name (Facebook, Google+, email, guestname)
    * Random stranger from game server player queue
    * AI by selecting checkbox

**2.2) User joins existing game**  
  _Actors:_ User, AI  
  _Goal:_ New player is added to an existing game  
  * From game dashboard, user sees game invites
  * User clicks "Join" button next to desired game invite
  * User taken to game screen

**3.1) User plays game**
  * 3.1.1) User places ships  
    _Actors:_ User  
    _Goal:_ User places ships in desired position  
    * User quick-taps (single tap gesture) ship to rotate 90 degrees
    * User long-taps (tap and hold/drag gesture) ship to drag onto board
    * Board sets ship position
  * 3.1.2) User takes turn  
    _Actors:_ User  
    _Goal:_ User makes a guess on opponent's ship position and attacks it  
    * User taps grid square twice to fire
    * Tapped square turns red on hit, and blue/white on miss
      * Extended feature: graphics
    * Game changes turn
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