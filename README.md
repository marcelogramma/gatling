Este es el manifiesto para levantar el servicio de Gatling.

Para poner en ejecucion el proyecto se debe instalar docker y docker-compose

El proyecto inicialmente se conecta con AWS atraves de una imagen de ansible y procede a hacer el deploy de una instancia con gatling.

Dicha instancia ejecutara el servicio de gatling y una vez que termina de hacer las pruebas, permite mostrarlos atraves de HTTP por su IP publica.

--------------------------------------------------------------------------------------------------------------------------------------------------------

Antes de empezar:

A) Se deben completar las credenciales de AWS, configuraciones de instancias y de Gatling en el archivo ".env" dentro de carpeta "files/config/"

*Opcional, se puede cambiar la llave publica y privada en "/files/config/scripts", a su vez hay que modificar el valor de la variable public_key_name y private_key_name. [Esta llave se usara para hacer el deploy de la instancia en AWS]

--------------------------------------------------------------------------------------------------------------------------------------------------------

Una vez subida la llave publica y modificados los valores de las variables en el archivo .env, se puede proceder a realizar el build de la imagen:

- Buildear imagen:

docker build . -t gatlingimg

Una vez buildeada la imagen debemos:

- Crear Instancia para gatling en AWS (tiempo aproximado de deploy 2 minutos y medio)

docker-compose up -d && docker exec -it gatling bash -c "chmod +x /home/scripts/*.sh && cd /home/scripts/ && ./create.sh"

Para realizar las correspondientes pruebas de carga:

- Ejecutar Pruebas de gatling (tambien actualiza los parametros de las variables de pruebas)

docker exec -it gatling bash -c "cd /home/scripts/ && ./start-gatling.sh"

* Para ver las pruebas debes ingresar con el navegador a la IP publica de la instancia. 

--------------------------------------------------------------------------------------------------------------------------------------------------------
- Stop de Instancia de Gatling

docker exec -it gatling bash -c "cd /home/scripts && ./stop.sh"

- Resumir Instancia de Gatling ya creada

docker exec -it gatling bash -c "cd /home/scripts && ./resume.sh"

- Terminar Instancia de Gatling

docker exec -it gatling bash -c "cd /home/scripts && ./terminate.sh" && docker-compose down

--------------------------------------------------------------------------------------------------------------------------------------------------------

** En caso de tratar de ingresar a la instancia, se debe ingresar con el usuario "ec2-user" y con la key previamente cargada para hacer el deploy de gatling