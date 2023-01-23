#bin/bash
source .env

sed -i "s#wwwurl#$url#g" /home/ec2-user/simulations/Basic.scala
sed -i "s/requestcount/$requestcount/g" /home/ec2-user/simulations/Basic.scala
sed -i "s/scenarioName/$scenarioName/g" /home/ec2-user/simulations/Basic.scala
sed -i "s/protocolvariable/$protocolvariable/g" /home/ec2-user/simulations/Basic.scala
sed -i "s#getVar#$getvar#g" /home/ec2-user/simulations/Basic.scala


sudo timedatectl set-timezone $timezone
sudo systemctl reload httpd
docker-compose up -d
docker exec -t gatling bash -c 'bin/gatling.sh -s Basic'