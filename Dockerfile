FROM centos:7

#Variable para que ansible no chequee hosts fingerprint en los known_hosts del contenedor
ENV ANSIBLE_HOST_KEY_CHECKING=False

#Crear carpeta para scripts, Instalar Cliente y server SSH para ejecutar ssh como demonio para usar Ansible
RUN mkdir -p /home/scripts;\
    mkdir -p /home/app;\
    yum update;\
    yum install epel-release -y;\
    yum install openssh-clients -y;\
    yum install openssh-server -y;\
    mkdir /var/run/sshd;\
    echo 'root:mypassword' | chpasswd;\
    sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config;\
    sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd;\
    ssh-keygen -A;\
    yum install screen -y
    

# Instalar Ansible, WGET y Python3 con dependecias varias para deploy de instancias en AWS

RUN yum install ansible -y;\
    yum install curl wget -y;\
    yum install gcc openssl-devel bzip2-devel libffi-devel zlib-devel -y;\
    wget https://www.python.org/ftp/python/3.9.6/Python-3.9.6.tgz;\
    tar -xvf Python-3.9.6.tgz;\
    cd Python-3.9.6;\
    ./configure --enable-optimizations;\
    make;\
    make altinstall;\
    yum install python3-pip -y;\
    pip3 install boto boto3 botocore; \
    ansible-galaxy collection install community.aws --force-with-deps;\
    cd /;\
    rm -rf Python-3.9.6.tgz;\
    rm -rf Python-3.9.6;\
    yum clean all

#Dejar corriendo demonio SSH
CMD ["/usr/sbin/sshd", "-D"]
