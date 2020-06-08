# Post-Action-Rest-Spark-Embed

How to build it?
- mvn clean install

How to run it ?
- java java -jar target/parse-jar-with-dependencies.jar 

How request should look like?
- {userId: ..., gameId: ...., action: ...}

How to test it?
- curl -X POST localhost:4567/actions -d "{"userId": 111, "gameId": 222, "action": "joined-the-game"}"

How to stop it?
- [CTRL]+[C] in console

# Run-as-contaier in Docker
How to build the image (in projec's directory)?
- docker build --tag parse:0.0.1 .

How to run the image?
- docker run --publish 4567:4567 --detach --name pp parse:0.0.1

How to save built image to file?
- docker save parse > parse_0_0_1.tar

How to load the image from sent archive?
- docker load < parse_0_0_1.tar