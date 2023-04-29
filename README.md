# CS109_Project
The java project of CS109 in SUSTech is a Jungle chess game.
New functions:
1. add enum class GameMode in GameController.java as a static instance.
2. add enum class PlayerType to indicate the user is AI or human.(This is an information needed when updating player score)
3. add readUser, writeUser, login, register and updateUserScore (when winning) fuction.
4. add file load and save function (including invalid input indentifying and handling)
5. add comment for all methods in GameListener.java
6. add PVE mode. you can turn to this mode by setting gameMode in controller to GameMode.PVE.(undo method adjusted. In PVE mode it will undo two moves, one from player and one from AI)