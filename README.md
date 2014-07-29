library-java-pinoccio
=====================

Java library for the Pinoccio API w/ Example app.

How to
======
1. Download the compiled jar [here](https://github.com/Pinoccio/library-java-pinoccio/releases)
2. Import the jar to your project
3. Import the library into your Java source
    import io.pinocc.pinocico.java.*;
4. Initialize a new API variable
    PinoccioAPI pinoccioAPI = new PinoccioAPI();


Functions
=========
Initialize the API and create variables to use globally or locally;
```java
PinoccioAPI pinoccioAPI = new PinoccioAPI(); // init local variable
int TROOP_ID_INT = 4; // define troop id
int SCOUT_ID_INT = 2; // define scout id in troop
String SESSION_TOKEN_STRING = "q4vn2g1smdcmb5efuco1eoj184"; // define token
```
Login with credentials, will be null if login is invalid.
```java
pinoccioAPI.loginWithCredentials("dylan@pinocc.io", "password12345"); // Returns JsonObject
```
Logout of session
```java
pinoccioAPI.logoutWithSession(SESSION_TOKEN_STRING); // returns true/false
```
Turn your LED on;
```java
pinoccioAPI.turnLEDOn(TROOP_ID_INT, SCOUT_ID_INT, SESSION_TOKEN_STRING); // Void
```
Turn your LED off;
```java
pinoccioAPI.turnLEDOff(TROOP_ID_INT, SCOUT_ID_INT, SESSION_TOKEN_STRING); // Void
```
Get all troops in account;
```java
pinoccioAPI.troopsInAccount(SESSION_TOKEN_STRING); // Returns JsonArray
```
Get all scouts in troop;
```java
pinoccioAPI.scoutsInTroop(TROOP_ID_INT,SESSION_TOKEN_STRING); // Returns JsonArray
```
Run bitlash command on scout, We handle URL encoding for you :);
```java
pinoccioAPI.runBitlashCommand(TROOP_ID_INT, SCOUT_ID_INT,"print temperature.f",SESSION_TOKEN_STRING); // Returns JsonObject
```
