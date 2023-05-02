# CS109_Project
The java project of CS109 in SUSTech is a Jungle chess game.

## New functions:

### 4/29
1. add enum class GameMode in GameController.java as a static instance.
2. add enum class PlayerType to indicate the user is AI or human.(This information is needed when updating player score)
3. add readUser, writeUser, login, register and updateUserScore (when winning) function.
4. add file load and save function (including invalid input identifying and handling)
5. add comment for all methods in GameListener.java
6. add PVE mode. you can turn to this mode by setting gameMode in controller to GameMode.PVE.(undo method adjusted. In PVE mode it will undo two moves, one from player and one from AI)
7. modify the getPassword function in User.java. Now it will compare passwords by hashcode, and the getPassword function is deleted. All this makes user's password safer. 

### 5/1

1. add online PVP mode.
2. more function in local test mode, now you can choose game mode.
3. add playback function.
4. add difficulty choosing function in PVE mode.(Medium AI haven't finished yet)

### 5/2

1. add player lock.(stop user from making moves when it's not his turn)
2. online PVP mode now receive opponent's profile in user2.(Show opponent's name and score)
3. User class has getWinRate method now.(different comparator can be used to sort user list now)   CAUTION: a player with zero game played will be treated as a player with 50% win rate.
4. modified the login method, now it will login to user2 if user1 already logged in. This means onPlayerSelectLocalPVPMode no longer needs input. And you should call the login method again before player select local PVP mode.
5. add turn counter
6. playback function now can be used in every mode.
7. add timer for every mode. If time is up, AI will take over and perform a random move.
8. add a medium AI.(dump greedy AI copied from https://github.com/jimmylaw21/CS109-2023-Sping-ChessDemo). （我只是强迫症犯了一定要把三个AI难度写全，然后就从SA那边抄过来了，你完全可以写一个更好的）