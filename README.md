library-java-pinoccio
=====================

Java library for the Pinoccio API w/ Example app.

How to
======
1. Download the compiled jar [here](http://haifisch.ninja/library-java-pinoccio.zip)
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
Turn your LED on;

    pinoccioAPI.turnLEDOn(TROOP_ID_INT, SCOUT_ID_INT, SESSION_TOKEN_STRING);

Turn your LED off;

    pinoccioAPI.turnLEDOff(TROOP_ID_INT, SCOUT_ID_INT, SESSION_TOKEN_STRING);

Get all troops in account;

    pinoccioAPI.troopsInAccount(SESSION_TOKEN_STRING);
