[//]: # (TODO: Add Logo)

[//]: # (TODO: Add images of Figma design)

# Eispunkt

[Eispunkt](https://github.com/2425-4bhitm-itp/eispunkt)
is a mobile application, that allows ice-stock sport clubs to manage and play tournaments.

# Features

Our goal is tho have a full on tournament management system for ice-stock sport clubs
with multiple game modes, statistics,
especially a user-friendly interface and much more.
The application is aimed to follow the Upper Austrian ice-stock sport rules.

The following features are currently implemented:

- Two teams have to be created and filled with 4 players each
- A Game with 3 Round and 6 Turns can be played
- The winner of the game is determined by the points of the teams
- 2 Points are awarded to the team with the biggest sum of points from all turns.
- 1 Point is awarded to the teams if it is a draw.
- The team who won the most points in all 3 rounds wins the game.

## Contributors

- Marvin Anderson
- Florian Hayer
- Vanesa Catic
- Atilla Simsek

## How to contribute

As this is a school project, we are currently not looking for contributors.
But if you have any suggestions or improvements, feel free to make a pull request or contact us.

## How to launch the application

- Clone the repository
- docker-compose -f ./docker/docker-compose.yml up
- cd quarkus
- mvn quarkus:dev --clean
- Open the browser and go to http://localhost:8080
- (Only for desktop) Inspect page and go into mobile view
- Enjoy the app