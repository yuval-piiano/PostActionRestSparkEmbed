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