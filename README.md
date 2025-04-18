Main.java:
Where the game starts. It sets up the game window, draws things on the screen, and controls the game loop. 
It controls the player, mines, and checks if they collide.
The game updates the score based on how far the player moves and shows it on the screen.

2. Player.java:
This represents the player.
It moves based on the WASD keys and tracks the score based on how far the player moves.
It checks if the player collides with a mine.
It draws the player on the screen (as a blue circle).
3. Mine.java:
This represents the mines that appear in the game.
It draws the mines and checks if they collide with the player.
