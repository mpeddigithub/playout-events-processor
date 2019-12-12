# playout-events-processor

# to build code
% git clone https://github.com/mpeddigithub/playout-events-processor.git

% cd playout-events-processor

% mvn clean install

# to build docker image
% docker build -t playout-events-processor .

# to deploy docker image
% docker-compose -f 1-docker-compose-db.yml up -d

% docker-compose -f 2-docker-compose-app.yml up -d

% curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:9090/api/contentwatched

## events arrived in sync
% curl -H "Content-Type: application/json" -X POST http://localhost:9090/api/event -d '{"eventTimestamp": 1576063768, "sessionId": "s1", "eventType": { "eventType": "START", "userId": "u1", "contentId": "c1" }}'

% curl -H "Content-Type: application/json" -X POST http://localhost:9090/api/event -d '{"eventTimestamp": 1576064468, "sessionId": "s1", "eventType": { "eventType": "STOP", "userId": "", "contentId": "" }}'

# verify results
% curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:9090/api/contentwatched/u1

## events arrived in async
% curl -H "Content-Type: application/json" -X POST http://localhost:9090/api/event -d '{"eventTimestamp": 1576065768, "sessionId": "s2", "eventType": { "eventType": "STOP", "userId": "", "contentId": "" }}'

% curl -H "Content-Type: application/json" -X POST http://localhost:9090/api/event -d '{"eventTimestamp": 1576064768, "sessionId": "s2", "eventType": { "eventType": "START", "userId": "u2", "contentId": "c2" }}'

# verify results
% curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:9090/api/contentwatched/u2

# to retrieve all content watched records
% curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:9090/api/contentwatched

## to undeploy

% docker-compose -f 2-docker-compose-app.yml down

% docker-compose -f 1-docker-compose-db.yml down