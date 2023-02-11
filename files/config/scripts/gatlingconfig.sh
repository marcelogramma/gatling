#bin/bash
source .env

sed -i "s#wwwurl#$url#g" /home/ec2-user/simulations/Basic.scala
sed -i "s/requestcount/$requestcount/g" /home/ec2-user/simulations/Basic.scala
sed -i "s/scenarioName/$scenarioName/g" /home/ec2-user/simulations/Basic.scala
sed -i "s/protocolvariable/$protocolvariable/g" /home/ec2-user/simulations/Basic.scala
sed -i "s#getVar#$getvar#g" /home/ec2-user/simulations/Basic.scala
sed -i "s/timevariable/$timevariable/g" /home/ec2-user/simulations/Basic.scala
sed -i "s/tiempologin/$tiempologin/g" /home/ec2-user/simulations/Basic.scala
sed -i "s/maxiempologin/$maxiempologin/g" /home/ec2-user/simulations/Basic.scala
sed -i "s#secretusernamelogin#$secretusernamelogin#g" /home/ec2-user/simulations/Basic.scala
sed -i "s#usernamelogin#$usernamelogin#g" /home/ec2-user/simulations/Basic.scala
sed -i "s#secretpasswordlogin#$secretpasswordlogin#g" /home/ec2-user/simulations/Basic.scala
sed -i "s#passwordlogin#$passwordlogin#g" /home/ec2-user/simulations/Basic.scala
sed -i "s#httpcodelogin#$httpcodelogin#g" /home/ec2-user/simulations/Basic.scala
sed -i "s#msjerrorlogin#$msjerrorlogin#g" /home/ec2-user/simulations/Basic.scala

sudo timedatectl set-timezone $timezone
sudo systemctl reload httpd
docker-compose up -d
#docker exec -t gatling bash -c 'bin/gatling.sh -s Basic'
docker exec -i gatling bash -c 'bin/gatling.sh'