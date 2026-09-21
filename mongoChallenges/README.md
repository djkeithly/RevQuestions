# MongoDB Challenges

This directory will contain all of the MongoDB challenges given by Revature.

## GadgetGalaxy.mongodb.js

This file contains the challenges done on 9/21/2026 on Week 5 under the MongoDB slides (23-24). Some of the separate challenges were combined if it looked like they could be done in one line and did not explicitly state to be done in one line.

This file requires a mongo database base to be set up to connect to. This uses the MongoDB extension for VSCode and the compile instructions inside of the MongoDB slides. For ease of access they are located here.

'''bash
docker run -d --name mongodb-sandbox -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password mongo:8
'''

To connect the database using the MongoDB extension, adding a connection without modifying the user connection so long as it is pointing to the default port of 27017 will connect it appropriately.

To see each output from find work correctly, the other outputs will need to be commented out or just highlighting the code up until what you want to see will show each output. Simply running the file will only show the last method with an output to be executed
