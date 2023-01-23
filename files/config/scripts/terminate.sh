#bin/bash
source .env
echo "Deleting instance..."
cp aws-terminate.yml.original aws-terminate.yml
sed -i "s/region: region-variable/region: $region/g" aws-terminate.yml
sed -i "s/aws_access_key: aws_access_key-variable/aws_access_key: $aws_access_key/g" aws-terminate.yml
sed -i "s/aws_secret_key: aws_secret_key-variable/aws_secret_key: $aws_secret_key/g" aws-terminate.yml

ansible-playbook -i localhost, aws-terminate.yml --extra-vars 'ansible_python_interpreter=/usr/bin/python3 ansible_user=root ansible_password=mypassword'

rm -rf aws-terminate.yml
