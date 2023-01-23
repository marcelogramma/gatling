#bin/bash
source .env
echo "Resuming Instance..."
cp aws-resume.yml.original aws-resume.yml
cp aws-info.yml.original aws-info.yml
sed -i "s/region: region-variable/region: $region/g" aws-resume.yml
sed -i "s/aws_access_key: aws_access_key-variable/aws_access_key: $aws_access_key/g" aws-resume.yml
sed -i "s/aws_secret_key: aws_secret_key-variable/aws_secret_key: $aws_secret_key/g" aws-resume.yml
sed -i "s/region: region-variable/region: $region/g" aws-info.yml
sed -i "s/aws_access_key: aws_access_key-variable/aws_access_key: $aws_access_key/g" aws-info.yml
sed -i "s/aws_secret_key: aws_secret_key-variable/aws_secret_key: $aws_secret_key/g" aws-info.yml


ansible-playbook -i localhost, aws-resume.yml --extra-vars 'ansible_python_interpreter=/usr/bin/python3 ansible_user=root ansible_password=mypassword'
ansible-playbook -i localhost, aws-info.yml --extra-vars 'ansible_python_interpreter=/usr/bin/python3 ansible_user=root ansible_password=mypassword host_key_checking=false'

IP_AWS_INSTANCE=$(grep -o '"public_ip": "[^"]*"' variable | awk -F '"' '{print $4}' | head -n 1)

echo -n "The Public IP of Gatling instance is: ";echo "$IP_AWS_INSTANCE"
echo -en "\nFor troubleshooring instance access the command is: "; echo -n "ssh -o StrictHostKeyChecking=no -i files/config/scripts/$private_key_name ec2-user@"; echo "$IP_AWS_INSTANCE"

rm -rf aws-resume.yml
