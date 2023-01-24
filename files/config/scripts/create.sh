#bin/bash
source .env

cp aws-create.yml.original aws-create.yml
cp aws-info.yml.original aws-info.yml
sed -i "s|key_material: key-variable|key_material: \"{{ lookup('file', '$public_key_name')}}\"|g" aws-create.yml
sed -i "s/region: region-variable/region: $region/g" aws-create.yml
sed -i "s/instance_type: instance-type-variable/instance_type: $instance_type/g" aws-create.yml
sed -i "s/image_id: image-variable/image_id: $image/g" aws-create.yml
sed -i "s/aws_access_key: aws_access_key-variable/aws_access_key: $aws_access_key/g" aws-create.yml
sed -i "s#aws_secret_key-variable#$aws_secret_key#g" aws-create.yml
#sed -i "s/aws_secret_key: aws_secret_key-variable/aws_secret_key: $aws_secret_key/g" aws-create.yml
sed -i "s/subnet_id_variable/$vpc_subnet_id/g" aws-create.yml
sed -i "s/vpc_id_variable/$vpc_id/g" aws-create.yml
sed -i "s/region: region-variable/region: $region/g" aws-info.yml
sed -i "s/aws_access_key: aws_access_key-variable/aws_access_key: $aws_access_key/g" aws-info.yml
sed -i "s#aws_secret_key-variable#$aws_secret_key#g" aws-info.yml


ansible-playbook -i localhost, aws-create.yml --extra-vars 'ansible_python_interpreter=/usr/bin/python3 ansible_user=root ansible_password=mypassword host_key_checking=false'
ansible-playbook -i localhost, aws-info.yml --extra-vars 'ansible_python_interpreter=/usr/bin/python3 ansible_user=root ansible_password=mypassword host_key_checking=false'


IP_AWS_INSTANCE=$(grep -o '"public_ip": "[^"]*"' variable | awk -F '"' '{print $4}' | head -n 1)

echo -en "\nThe Public IP of Gatling instance is: ";echo "$IP_AWS_INSTANCE"
echo -en "\nFor troubleshooring instance access the command is: "; echo -n "ssh -o StrictHostKeyChecking=no -i files/config/scripts/$private_key_name ec2-user@"; echo "$IP_AWS_INSTANCE"
echo -en "\For view test results, visit http://$IP_AWS_INSTANCE \n"

rm -rf aws-create.yml
rm -rf aws-info.yml
rm -rf variable