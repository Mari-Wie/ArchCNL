## Build image

### Open a terminal console
Open terminal windows:
```
<strg>+<alt>+<t>
```

### Optional: Login to Docker repository (dockerhub)
```
docker login
```

### Build .jar in PROD mode
```
mvn clean package -P prod
```

### Build docker image
You should be in *web-ui* directory
```
docker build .
```

### Run new image local
```
docker run -it -p 8081:8080 <HASH_OF_IMAGE>
```

### Tag and push new image to docker hub
```
docker tag <HASH_OF_IMAGE> mariwie/archcnl:0.0.1-SNAPSHOT
docker push mariwie/archcnl:0.0.1-SNAPSHOT
```

<br />

## Run in DEV mode (as .war)
```
mvn jetty:run -P develop
```

<br />

## Run full app with Stardog as docker-compose
You should be in *web-ui/docker* directory

### Run the command to start
```
docker-compose -f docker-compose.yml up
```

### Run the command to stop
```
docker-compose -f docker-compose.yml down
```

<br />

## Run browser inside docker-compose network
You should be in *web-ui/docker* directory

### Download security settings 
```
wget https://raw.githubusercontent.com/jfrazelle/dotfiles/master/etc/docker/seccomp/chrome.json -O ~/chrome.json
```

### Allow temporarily access to the X-session
```
xhost local:root
```

### Run the command to start
```
docker-compose -f docker-compose-browser.yml up
```

### Run the command to stop
```
docker-compose -f docker-compose-browser.yml down
```

### Troubleshooting
If you see this error message, you forgot to run "xhost local:root" (See above):
```
cannot open display: unix:0
```