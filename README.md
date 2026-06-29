# Mini Twitter

This project is a simplified version of Twitter built using Java Swing. It allows users to create accounts, organize users into groups, follow other users, post tweets, and view a live news feed through a desktop GUI.

The main goal of this project was to practice object-oriented programming and implement several design patterns in a real application.

## Features

* Create users and groups
* Organize users using a tree structure
* Open individual user dashboards
* Follow other users
* Post tweets
* View a live news feed
* Display total users, groups, and messages
* Calculate the percentage of positive messages

## Design Patterns Used

### Singleton

Used to make sure there is only one Mini Twitter system running.

### Composite

Used so both users and groups can be stored inside the same tree structure.

### Observer

Used to automatically update followers whenever a user posts a new tweet.

### Visitor

Used to calculate statistics like total users, total groups, total messages, and the percentage of positive messages.

## Technologies

* Java
* Java Swing
* Object-Oriented Programming

## Running the Project

Compile all Java files:

```bash
javac *.java
```

Run the project:

```bash
java Driver
```

## Author

Yuvraj Narwal
