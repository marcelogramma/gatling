#bin/bash
source .env
echo "Stopping instance..."
cp aws-stop.yml.original aws-stop.yml
sed -i "s/region: region-variable/region: $region/g" aws-stop.yml
sed -i "s/aws_access_key: aws_access_key-variable/aws_access_key: $aws_access_key/g" aws-stop.yml
sed -i "s#aws_secret_key-variable#$aws_secret_key#g" aws-stop.yml

ansible-playbook -i localhost, aws-stop.yml --extra-vars 'ansible_python_interpreter=/usr/bin/python3 ansible_user=root ansible_password=mypassword'

rm -rf aws-stop.yml
