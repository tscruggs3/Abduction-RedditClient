# Abduction for Reddit
### Final Project (Team H)
### Nathan Mannes, Vermillion Villareal, Thomas Scruggs, Jordan Sybesma

## What Abduction Does:

Abduction is a client for Reddit, one of the most visited web forums on the modern internet.  The program scrapes data from Reddit, notably post content, user data, and comment data, and displays it in an intuitive fashion to the user.  Users can select and view subreddit and post content with an interface designed with good web patterns in mind, catering to beginning users while handling all the content that Reddit has to offer.

## User Interface

### Intermediates vs Beginners

We designed our interface primarily for beginners, keeping the layout of the page similar to the familiar Reddit page while enhancing the visibility of post titles, vote counts, and usernames.  The page also conforms to expected norms of webpage design, placing the title of content or a given subreddit forum at the top left of the page, the menu below the title, and content below that.  Hyperlinks are visible and clickable, easily bringing users to content without hassle.

For intermediate users, we added a search menu for subreddits, accessible through the menu at the top of the page.  This allows users that are more familiar with the Reddit platform to access content that they specifically want to look for while separating more advanced features from the general interface.

## Under The Hood

### Data Structures

We built data structure objects to represent different aspects of the Reddit interface.  For instance, our Post class represents all the data contained in a specific reddit post such as the comment tree, vote count, title, and content.  This way, we were able to effectively encapsulate our web-scraping methods from the rest of the classes in our project and adhere to good object-oriented design standards.

### Design Patterns

We use the Observer and MVC design patterns in the code structure for the client.  The Observer pattern is used to facilitate communication between the user interface and the RedditController class, where specific types of buttons call specific methods that then lead to desired actions.

Further, the bulk of our class design falls under the MVC pattern.  Our RedditScraper class is our model, containing the methods necessary to gather and process the data shown by the client.  The RedditController class is our Controller, taking input from the user interface and calling the proper methods in the RedditScraper.  Finally, the SceneRender interface and its implementing classes PostGUI, ContentGUI, SubredditGUI, and UserGUI represent the View, sending user input to the Controller and encompassing the user interface.

### Why separate the View into several classses and an interface?

Each class contains the methods for a specific type of page to maximize cohesion in reading the code.  We felt that placing all the individual render methods in one class would result in sprawling, unreadable code, and wanted to ensure that our final project was cohesive and readable.

## Executing Abduction

The executable main method for the Abduction client is in the RedditController class.  After excecuting this method, the program will open.

## Known Bugs:

* Closing out of a youtube link while autoplay is enabled results in the video audio playing in the background of the application.  We looked for a fix given that removing all references to the offending Pane object and waiting for garbage collection to take over doesn't seem to work, but unfortunately this issue appears to be inherent to the way JavaFX manages the WebEngine object.