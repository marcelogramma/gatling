#bin/bash
source .env

cp aws-info.yml.original aws-info.yml
sed -i "s/region: region-variable/region: $region/g" aws-info.yml
sed -i "s/aws_access_key: aws_access_key-variable/aws_access_key: $aws_access_key/g" aws-info.yml
sed -i "s#aws_secret_key-variable#$aws_secret_key#g" aws-info.yml

ansible-playbook -i localhost, aws-info.yml --extra-vars 'ansible_python_interpreter=/usr/bin/python3 ansible_user=root ansible_password=mypassword host_key_checking=false'

IP_AWS_INSTANCE=$(grep -o '"public_ip": "[^"]*"' variable | awk -F '"' '{print $4}' | head -n 1)

echo -n "The Public IP of Gatling instance is: ";echo "$IP_AWS_INSTANCE"
echo -en "\nFor troubleshooring instance access the command is: "; echo -n "ssh -o StrictHostKeyChecking=no -i files/config/scripts/$private_key_name ec2-user@"; echo "$IP_AWS_INSTANCE"

rm -rf aws-info.yml
rm -rf variable

echo -en "\nRefreshing simulation files\n"

scp -r -o StrictHostKeyChecking=no -i $private_key_name ../app/* ec2-user@$IP_AWS_INSTANCE:/home/ec2-user/
scp -o StrictHostKeyChecking=no -i $private_key_name .env ec2-user@$IP_AWS_INSTANCE:/home/ec2-user/
scp -o StrictHostKeyChecking=no -i $private_key_name gatlingconfig.sh ec2-user@$IP_AWS_INSTANCE:/home/ec2-user/

echo -en "\nStarting Gatling service...\n"

ssh -o StrictHostKeyChecking=no -i $private_key_name ec2-user@$IP_AWS_INSTANCE "chmod +x /home/ec2-user/*.sh && cd /home/ec2-user/ && ./gatlingconfig.sh"

echo -en "\nLoad test finished, please visit http://$IP_AWS_INSTANCE to show results\n"
echo -en "\nLoad test finished, please visit http://$IP_AWS_INSTANCE to show results\n"
