# Relatório Class Assignment 4 - Containers com Docker

objectivo do assingment

O código fonte para esta tarefa está localizado na pasta [ca4](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca4/).


## 1. Análise, Design e Implementação



### 1.1 Criar Dockerfile para o container "db"

explicar o dockerfile


### 1.2 Criar Dockerfile para o container "web"

explicar docker file


### 1.3 Utilizar Docker-compose para produzir dois serviços: "db" e "wb"

explicar docker compose

na pasta root de Ca4

````
$ docker-compose build
````

````
$ docker-compose up
````


````
$ docker-compose stop

$ docker-compose start
````

In the host you can open the spring web application using the following url:

http://localhost:8080/basic-0.0.1-SNAPSHOT/
You can also open the H2 console using the following url:

http://localhost:8080/basic-0.0.1-SNAPSHOT/h2-console
For the connection string use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb


````
$ docker-compose down
````


### 1.4 Publicar as imagens "db" e "web" no Docker Hub


a seguir a ter uma conta no docker hub, criado um repositório e com ambos os containers a correr

````
$ docker commit ca4_web_1 web_spring_boot_application
````

fazer login no docker hub

````
$ docker login
````

primeiro tenho de fazer uma tag da minha imagem associada à minha conta docker hub

You need to include the namespace for Docker Hub to associate it with your account.
The namespace is the same as your Docker Hub account name.
You need to rename the image to YOUR_DOCKERHUB_NAME/docker-whale.

````
$ docker tag web_spring_boot_application martalribeiro/devops2019-2020:web_spring_boot_application
````

de seguida fazer o push

````
$ docker push martalribeiro/devops2019-2020:web_spring_boot_application
````

repetir o processo para o container "db"

````
$ docker commit ca4_db_1 db_spring_boot_application

$ docker tag db_spring_boot_application martalribeiro/devops2019-2020:db_spring_boot_application

$ docker push martalribeiro/devops2019-2020:db_spring_boot_application
````

Para poder o pull dos containers realizar os seguinte comandos:

````
$ docker pull martalribeiro/devops2019-2020:db_spring_boot_application

$ docker pull martalribeiro/devops2019-2020:web_spring_boot_application
````


### 1.5 Utilizar um volume no container "db" para copiar o ficheiro gerado para a base de dados


aceder ao container db

````
$ docker-compose exec db bash
````

aceder à pasta /usr/src/app

````
$ cd /usr/src/app

$ ls -la
drwxr-xr-x 1 root root    4096 May  5 23:10 .
drwxr-xr-x 1 root root    4096 May  5 23:10 ..
-rw-r--r-- 1 root root 2303679 Oct 14  2019 h2-1.4.200.jar
-rw-r--r-- 1 root root   28672 May  6 00:25 jpadb.mv.db
````

copiar "jpadb.mv.db" para a pasta /usr/src/data

````
cp jpadb.mv.db /usr/src/data
````

como foi criado o volume ./data:/usr/src/data, vai ser feita a copia para o pc host

confirmar que foi copiada

````
$ cd /usr/src/data

$ ls -la
drwxrwxrwx 1 root root     0 May  6 00:27 .
drwxr-xr-x 1 root root  4096 May  5 23:10 ..
-rwxr-xr-x 1 root root 28672 May  6 00:27 jpadb.mv.db
````

para sair do container "db"

````
$ exit
```` 

no pc host também




### 1.6 Adicionar a tag ca4

No final do Ca4, marcou-se o master branch com a annotated tag Ca4 e verificou-se que a tag tinha sido adicionada. Para este efeito, executou-se:

````
$ git tag -a ca4 -m "ca4"

$ git push origin ca4

$ git tag
Ant-ca2-part1
AntAltern
Ca1
Ca2-part2
HypervAltern
ca2-part1
ca3-part1
ca3-part2
ca4
v1.2.0
v1.3.0
v1.3.1
````


## 2. Análise da alternativa

Como ferramenta alternativa ao Docker, foram exloradas as Kubernetes.




## 3. Implementação da alternativa - Kubernetes
