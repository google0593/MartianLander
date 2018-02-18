Martian Lander - Assignment 2

Min: SDK 22
IDE: Android Studio 2.2
Developed By: Nikolai Jucutan - 1419170

===================================================================================================
Design

Gameplay - For this game i did not implement accelerometer because i think its awkward.
The problem with the accelerometer is they're not precise, 
it means they're hard to control and this is frustrating for gamers.

Controls - And also for the controls instead of one tap and go. i used onTouch listeners instead.
So when you hold a button (directions) the spaceship will go that way depending on the button you are pressing.

The reason why the 'UP' button is on the left side of the screen, is so that the users can press 2 buttons at the same time.
i.e. if the user want to go upper left or upper right. Restart is on top so users will not accidentally press the button.

===================================================================================================

Main Extensions

1. Screen Resolution Friendly

The purpose of this class is to ensure the compatibility of the app for all screen sizes.
Another purpose of this class is to make it easier to draw the terrain/path of the application
and also to make it easier to implement 'collision' part of the assignment.
It will make it easier because if you need to get the screen size, convert dp to px (vice versa) you can just call this class.
I used this on my touchpaint assignment assignment as well and this code is really useful.
    
2. Infinity Scroll Background

To make the application/game more attractive and appealing to the users, i implemented a custom background.
Because a plain black color background is too dull.
I also think the game is more fun and more engaging this way.

As a game developer we must always find a way to make our user happy.

3. Sounds Effect/Background Music -> http://www.freesound.org/people/Cyberkineticfilms/sounds/130203/
 
Music and sounds effects are one of the most important element of gaming. Sounds connect the game world and the player surroundings.
Sounds make things more 'realistic'. Sounds can also inform player when they do something. In this case
a sound plays when a button is pressed. Gaming without sound is just plain boring, just imagine your self watching
an action movie without sounds.

4. User Interface

Start up screen - I thought of this because i think a game that starts immediately is not really the best way to design games.
start up screen/splash screen is like a cover of a book. it gives the player a hint what the game is all about and 
what they're going to expect.

Application Icon - https://cdn.brainpop.com/science/space/mars/icon.png
When a user browse through the appstore the first thing they see is the Title and the Icon of your application.
First impression is always important. As a gamer my self, sometimes when i download games from the playstore and see
a game with an unattractive icon i just skip it and continue scrolling. This is why i inluded a custom icon instead
of the default android one.

Custom Made Fuel Gauge - Instead of numbers, i thought it would be more attractive if i use 'graphics' instead.
There is also an indicator that when the fuel is empty the border turns to red.

===================================================================================================

Other Extensions

1.custom terrain   	-> (Mars Terrain) http://opengameart.org/content/dirty-burnt-and-light-sand	
2.custom spaceship 	-> (Spaceship) http://millionthvector.blogspot.co.nz/p/free-sprites_12.html
3.sprite animations 	-> (Main Explosion) http://www.appuntidigitali.it/site/wp-content/uploads/explosion-sprite.png
		   	-> (Burning Wreckage) https://s-media-cache-ak0.pinimg.com/originals/fd/1c/31/fd1c31aed1e5de3f869c38b996baa792.jpg
4.restart button

Resources

stackeroverflow.com

online-audio-converter.com

freesound.org

opengameart.org

YouTube.com

Unitec Samples
