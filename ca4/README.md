# Relatório Class Assignment 4 - Containers com Docker

O código fonte para esta tarefa está localizado na pasta [ca4](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca4/).

O objectivo do class assignment 4 é utilizar o Docker para configurar um ambiente de "containerização" para se poder executar a 
versão gradle do Spring Boot Tutorial Basic Project.

## 1. Análise, Design e Implementação

### 1.1 Preparação do assignment

Para a realização da Class Assignment 4 (Ca4) foi utilizado o sistema operativo Windows e para poder cumprir todos os requisitos 
necessários para a sua implementação foi necessário:

* acesso à Power Shell do Windows;
* uma ferramenta de virtualização (neste caso, foi utlizado o Hyper-V da Microsoft);
* ter instalado o Docker Desktop (neste caso, recorreu-se ao executável fornecido em https://www.docker.com/products/docker-desktop);
* um repositório remoto (neste caso, foi utilizado o Bitbucket);
* o Spring Boot Tutorial Basic Project em gradle utilizada no Class Assignment 2, parte 2 proveniente do Tutorial React.js 
and Spring Data REST application;

Para recriar o ambiente de containerização, é necessário ter as imagens necessárias para a aplicação correr. Para isso, é possível a
utilização de imagens já existentes no Docker Hub ou criar um imagem a partir da configuração de um Dockerfile. Neste assignment,
foram criadas duas imagens de raiz para os dois containers necessários.

### 1.2 Criar Dockerfile para o container "db"

O primeiro passo passou por configurar o Dockerfile do container "db" que vai ser utilizada para executar o servidor H2 da base de dados, 
onde a web application se vai conectar. Inicialmente, recorreu-se à imagem *ubuntu* já existente para instalar o sistema operativo do 
container. De seguida é instalado todo o software necessário e descarrega-se o servidor H2 atrvés do comando wget. Finalmente, são definidos
dois portos de acesso ao servidor e executa-se o comando para este correr.

### 1.3 Criar Dockerfile para o container "web"

No passo seguinte configurou-se o Dockerfile para o container "web" que vai ser utilizado para executar o tomcat e a web application no seu 
interior. Recorreu-se à imagem *tomcat* para inicializar o container com o servidor e, de seguida, foi instalado todo o software necessário à
execução da aplicação web. Foram também definidos os comandos para realizar o git clone do repositório remoto onde a aplicação se encontra e
acedeu-se à pasta da aplicação para se fazer o build do ficheiro build.gradle aí contido, dando-lhe as permissões necessárias. Finalmente, é
definido o comando que vai realizar a cópia do ficheiro war gerado no build para a pasta /var/lib/tomcat8/webapps do tomcat, que quando detectar
este ficheiro, vai automaticamente expandi-lo e assim executar a web application. No final do ficheiro foi colocado o porto de entrada onde o
tomcat vai ficar disponível.

### 1.4 Utilizar Docker-compose para produzir dois serviços: "db" e "web"

Após a configuração de cada um dos Dockerfiles referidos, procedeu-se à configuração do documento docker-compose. Este vai ser o documento que
vai dar origem ao ambiente de conteinerização e vai criar dois serviços: "db" e "web". Em cada um está definida a imagem a ser utlizada e o endereço
de IP e os portos a serem configurados. No serviço "web" é especificado também que este vai ser dependendente do "db", ou seja, este último
tem de arrancar sempre em primeiro lugar. No serviço "db" foi também configurado um volume que faz a ligação entre a pasta "./data" presente na
máquina host e a pasta "/usr/src/data" presente máquina cvirtualizada no container. Finalmente, para além dos serviços, é configurada a rede 
que vai estar disponível no ambiente de conteinerização. 

Para iniciar a contrução do ambiente de conteinerização, é necessário na pasta root do documento Docker-compose, executar o seguinte a partir
da linha de comandos da PowerShell:

````
$ docker-compose build
````

Após o build ter sido realizado com sucesso, é possível visualizar na dashboard da aplicação do Docker Desktop o ambiente e os dois containers
construídos. De seguida, para inicializar os containers criados, executa-se o seguinte comando:

````
$ docker-compose up
````

A partir do momento em que ambos os containers estão a correr neste ambiente, o servidor H2 e a aplicação web gradle spring
boot passam a ser executados e, desta forma, é possível verificar no brownser da máquina host qual o resultado obtido.

Para isso, foi utilizado o browser Google Chroome e os seguintes endereços:

**Gradle Spring Application:**
````
http://localhost:8080/basic-0.0.1-SNAPSHOT/
````

**Base de Dados H2**
````
http://localhost:8080/basic-0.0.1-SNAPSHOT/h2-console
````

Ambas as páginas foram carregadas com sucesso, permitindo assim concluir que a execução do ficheiro Docker-compose com ao projeto gradle
spring application e o servidor H2 foi realizado com secesso. 

De seguida, a partir da página onde a base de dados ficou disponívol, inseriu-se o seguinte URL- jdbc:h2:tcp://192.168.33.11:9092/./jpadb
e foram introduzidos-se os alguns dados na base de dados. Atualizando a página onde está a ser carregada a web application é possivel
verificar que os dados que foram introduzidos ficam imediatamente disponíveis para visualização.

A aplicação do Docker Desktop permite também pausar e voltar a iniciar os containers, sem que estes sejam destruídos. Para isso, 
executam-se os seguintes comandos:

````
$ docker-compose stop

$ docker-compose start
````

Finalmente, para efetuar a destruição do ambiente de containerização, executa-se o seguinte comando:

````
$ docker-compose down
````


### 1.5 Publicar as imagens "db" e "web" no Docker Hub

Para ser possível publicar as imagens criadas no Docker Hub de forma a que fiquem disponíveis para qualquer outro utilizador,
é necessário efetuar o registo de uma conta e criar um repositorio público. De seguida, temos de verificar que o container 
com a imagem que pretedemos guardar está a correr. Para isso, executa-se o seguinte comando:

````
$ docker ps

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              (...)     NAMES
19f907f76eb4        ca4_web             "catalina.sh run"        22 hours ago        Up 11 seconds       (...)     ca4_web_1
0c1671290d23        ca4_db              "/bin/sh -c 'java -c…"   22 hours ago        Up 11 seconds       (...)     ca4_db_1
````

Para publicar a imagem criada no container "web", é primeiro necessário realizar o commit da imagem pretendida:

````
$ docker commit ca4_web_1 web_spring_boot_application
````

De seguida, é nécessário fazer o login na conta do Docker Hub:

````
$ docker login
````

Finalmente, é necessário realizar uma tag da imagem da qual se fez commit e associa-la à conta criada no Docker Hub:

````
$ docker tag web_spring_boot_application martalribeiro/devops2019-2020:web_spring_boot_application
````

E depois realizar o push da imagem para o repositório pessoal:

````
$ docker push martalribeiro/devops2019-2020:web_spring_boot_application
````

Após publicada a imagem do container "web", repete-se todo o processo para o container "db":

````
$ docker commit ca4_db_1 db_spring_boot_application

$ docker tag db_spring_boot_application martalribeiro/devops2019-2020:db_spring_boot_application

$ docker push martalribeiro/devops2019-2020:db_spring_boot_application
````

Para outros utilizadores poderem ter acesso às imagens, apenas é necessário realizar o pull a partir dos seguinte comandos:

````
$ docker pull martalribeiro/devops2019-2020:db_spring_boot_application

$ docker pull martalribeiro/devops2019-2020:web_spring_boot_application
````


### 1.6 Utilizar um volume no container "db" para copiar o ficheiro gerado para a base de dados

Para podermos fazer uma cópia do ficheiro gerado para a base de dados, é necessário aceder ao volume configurado no container "db".
Após inicializar o container, executa-se o seguinte comando para podermos aceder ao interior do container:

````
$ docker-compose exec db bash
````

O primeiro passo passa por aceder à pasta "/usr/src/app" contida no container, através do comando:

````
$ cd /usr/src/app

$ ls -la
drwxr-xr-x 1 root root    4096 May  5 23:10 .
drwxr-xr-x 1 root root    4096 May  5 23:10 ..
-rw-r--r-- 1 root root 2303679 Oct 14  2019 h2-1.4.200.jar
-rw-r--r-- 1 root root   28672 May  6 00:25 jpadb.mv.db
````

De seguida, executa-se o comando de cópia e copia-se o ficheiro "jpadb.mv.db" para a pasta */usr/src/data:*

````
cp jpadb.mv.db /usr/src/data
````

Como foi criado o volume "./data:/usr/src/data", o ficheiro copiado para esta pasta vai ser automaticamente copiado para a pasta
"./data" da máquina host. Para confirmar que a cópia foi feita, executa-se o seguinte comando para verificar o interior da pasta 
"/usr/src/data":

````
$ cd /usr/src/data

$ ls -la
drwxrwxrwx 1 root root     0 May  6 00:27 .
drwxr-xr-x 1 root root  4096 May  5 23:10 ..
-rwxr-xr-x 1 root root 28672 May  6 00:27 jpadb.mv.db
````

E, de seguida, confirma-se na máquina host se a pasta "./data" contém o ficheiro também.

Para sair do interior do container "db", executa-se o seguinte comando.

````
$ exit
```` 

### 1.7 Adicionar a tag ca4

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


## 2. Análise de uma alternativa

Como ferramenta alternativa ao Docker, foram exloradas as Kubernetes.




## Referências:

* https://www.docker.com/products/docker-desktop