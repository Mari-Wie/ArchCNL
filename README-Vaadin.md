# Vaadin Getting started readme

## Installation

Everything is fetched by Maven automatically.


## Run development server

We need to execute the goal "jetty:run" on the web-ui child module.

		mvn -pl web-ui jetty:run

Then navigate to http://localhost:8080 to see the web UI.

## Build web archive (.war)

WAR file is built during maven `package` phase:

		mvn package


## Enable production mode

To run server in production mode or build web archive in production mode
just add `-Pproduction` to the maven calls above.
