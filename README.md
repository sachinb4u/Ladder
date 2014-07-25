Ladder
======

Author - Sachin Bhosale     http://www.linkedin.com/in/sachinbhosale

Features
--------
  - Game can support multiple player
  - Game can support any valid combination of rows and columns
  - Game uses random algorithm to get dice value


Output
------
  - game-output.txt file is added for sample output
  - it take players names for configured number of players
  - refer Game picture in word document for easy understanding however ladders and snakes can be configured to different values
  

Configuration
-------------
  - src/main/resources/config.properties contains the default game configuration
  - use optionally can build the configuration programmtically
  - current program uses configuration from config.properties.
  - change values in cofig.properties to try out different options of rows, columns, ladders, snakes and noOfPlayers
  
Validations
-----------
  - basic configurations are covered
  - Ladder & Snakes validation
    - ladder should always go from smaller cell to greater cell
    - snake should always go from greater cell to smaller cell
    - ladder and snake cannot be present on same cell. 
    
Unit Tests
----------
  - Unit tests for few classes are added
  - Update : 25 July 2014 - testcases added
            - DiceRollerTest
            - GameControllerTest
            - GameRuleTest
            - PlayerTest
