This is the manifest to lift the Gatling service.

To run the project, docker and docker-compose must be installed.

The project initially connects to AWS through an ansible image and proceeds to deploy an instance with gatling.

Said instance will execute the gatling service and once it finishes doing the tests, it allows to show them through HTTP by its public IP.

-------------------------------------------------- -------------------------------------------------- -------------------------------------------------- --

Before starting:

A) The AWS credentials, instance and Gatling configurations must be completed in the ".env" file inside the "files/config/" folder

B) all the configuration is done in the file PerfTestConfig.scala which is located in /files/config/app/simulations/

C) Create an ssh key pair (private and public), id_rsa_gatling and id_rsa_gatling.pub. Put them in the /files/config/scripts/ directory

*Optional, you can change the public and private key in "/files/config/scripts", in turn you have to modify the value of the variable public_key_name and private_key_name. [This key will be used to deploy the instance on AWS]

-------------------------------------------------- -------------------------------------------------- -------------------------------------------------- --

Once the public key has been uploaded and the values of the variables in the .env file have been modified, we can proceed to build the image:

- Build image:

docker build . -t gatlingimg

Once the image is built we must:

- Create Instance for gatling in AWS (approximate deployment time 2 and a half minutes)

docker-compose up -d && docker exec -it gatling bash -c "chmod +x /home/scripts/*.sh && cd /home/scripts/ && ./create.sh"

To carry out the corresponding load tests:

- Run Gatling Tests (also update test variable parameters)

docker exec -it gatling bash -c "cd /home/scripts/ && ./start-gatling.sh"

* To see the tests you must enter the public IP of the instance with the browser.

-------------------------------------------------- -------------------------------------------------- -------------------------------------------------- --
- Gatling Instance Stop

docker exec -it gatling bash -c "cd /home/scripts && ./stop.sh"

- Summarize Gatling Instance already created

docker exec -it gatling bash -c "cd /home/scripts && ./resume.sh"

- Terminate Gatling Instance

docker exec -it gatling bash -c "cd /home/scripts && ./terminate.sh" && docker-compose down

-------------------------------------------------- -------------------------------------------------- -------------------------------------------------- --

** In case of trying to enter the instance, you must enter with the user "ec2-user" and with the previously loaded key to deploy gatling