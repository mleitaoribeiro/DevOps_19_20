# Relatório Class Assignment 3 - Virtualização com Vagrant

## Class Assignment 3, parte 1 - Utilizar uma Virtual Box VM com Ubuntu para correr os projetos realizados nos assignments anteriores


## 1. Análise, Design e Implementação

### 1.1 Preparação do Assignment

Para a realização da Class Assignment 3 (Ca3), part1 foi utilizada a distribuição Linux Ubuntu 18.04 LTS na máquina host e para poder
cumprir todos os requisitos necessários para a sua implementação foi necessário:

  * acesso à CLI do Ubuntu;
  * uma ferramenta de virtualização (neste caso, foi utlizado a Virtual Box da Oracle);
  * um repositório remoto (neste caso, foi utilizado o Bitbucket);
  * o Spring Boot Tutorial Basic Project utilizada no Class Assignment 1 proveniente do Tutorial React.js and Spring Data REST 
  application;
  * o gradle_basic_demo utilizado no Class Assignment 2 e o respectivo jar, basic_demo-0.1.0.jar.


### 1.2 Criar uma máquina virtual seguindo os passos da aula teórica "Introduction to Virtualization"

Para iniciar o Ca3, part1, foi necessário criar a pasta Parte1 na pasta Ca3 já existente no repositório. De seguida, foi
utilizado o programa Virtual Box da Oracle para construir uma máquina virtual, usando o Ubuntu como distribuição Linux para
o sistema operativo. Para este efeito, foram seguidos os passos descritos na aula teórica "Introduction to Virtualization" e 
realizadas todas as configurações de rede necessárias para existir acesso à internet por parte da máquina virtual, bem como
acesso remoto por SSH à máquina host. A configuração foi concluída com sucesso, sem qualquer tipo de complicação. 

De seguida, reiniciou-se a máquina virtual, escolhendo a opção *headless start* para que esta corresse em backgroud e acedeu-se
à máquina através de uma ligação SSH na CLI do Ubuntu da máquina host, executando:

````
$ ssh martalribeiro@192.168.56.5
````

O termo *martalribeiro* representa o nome do utlizador da sessão onde se irá fazer o login e *192.168.56.5* representa o endereço
de IP no qual é feita a ligação entre a máquina virtual e a host.

### 1.3 Realizar um clone do repositório pessoal no interior da máquina virtual

Para realizar clone do repositório remoto (Bitbucket) na home directory da máquina virtual, foi necessário primeiro realizar
a instalação do sistema de controlo de versões Git e, de seguida proceder, ao clone. Para esse efeito, executaram-se os seguintes 
comandos, sendo que o último teve como objetivo verificar se a pasta *devops-19-20-a-1191779* foi de facto "clonada".

````
$ sudo apt install git

$ cd

$ git clone https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git

$ ls
````

De seguida, foi configurado o nome do utilizador e o endereço de email no sistema de controlo de versões Git para caso seja realizado
algum commit, a informação para identificar o autor do commit esteja definida.

````
$ git config --global user.name "Marta Ribeiro"
$ git config --global user.email 1191779@isep.ipp.pt
````

### 1.4 Instalar o software necessário para executar as aplicações contidas no repositorio

Para poderem ser executadas as aplicações sugeridas no guião do Ca3, part1, foi necessário instalar algum software essencial.
Para a instalação do Java Development Kit 8 e do Apache Maven 3.6.0, foram utilizados os seguintes comandos:

````
$ sudo apt install openjdk-8-jdk-headless

$ sudo apt install maven
````

Para instalação da ferramenta Gradle, versão 6.3, foram seguidas as intruções dadas no endereço https://gradle.org/, sendo
necessário recorrer ao Software Development Kit Manager, bem como proceder à instalação do software *curl*, *zip* e *unzip*.

````
$ sudo apt install curl

$ curl -s "https://get.sdkman.io" | bash

$ sudo apt install unzip

$ sudo apt install zip

source "/home/martalribeiro/.sdkman/bin/sdkman-init.sh"

$ sdk install gradle 6.3
````


### 1.5 Realizar o build e a execução do Spring Boot Tutorial Basic Project

A primeira aplicação a ser executada, presente no repositório remoto clonado, foi a versão Basic do Tutorial React.js and Spring
Data REST application. A partir da pasta *root* do projeto contido em Basic (~/devops-19-20-a-1191779/ca1/tut-basic), testou-se
alguns goals presentes no ficheiro pom.xml para verificar que o build era feito com sucesso. Executaram-se os goals *compile* e
*test*, através dos seguintes comandos:

```
$ mvn compile

$ mvn test
```

Em ambos os goals, o build foi executado com sucesso. De seguida, executou-se a web application baseada em spring boot, através do
comando:

````
mvn spring-boot:run
````

A aplicação foi executada com sucesso e foi inicializado o servidor Tomcat, que ficou então disponível para pedidos HTTP no
porto 8080 da máquina virtual, cuja ligação à maquina host está configurada com o endereço de IP *196.168.56.5*. Dado se tratar
de uma web application, os pedidos HTTP ao servidor têm de ser realizados a partir de um cliente HTTP, como por exemplo, um 
browser. Como a máquina virtual nao possui interface gráfica e por isso, não possui browsers, é necessário realizar os pedidos
através de um browser da máquina host para se poder aceder ao frontend da aplicação.

Para isso, foi utilizado o Google Chrome, colocando o seguinte endereço no browser:

````
http://192.168.56.5:8080/
````

### 1.6 Realizar o build e a execução do Gradle Basic Demo

De seguida, foi executado a aplicação de chat room Gradle Basic Demo. A partir da pasta *root* do projeto, contido em gradle_basic_demo
(~/devops-19-20-a-1191779/ca2/Parte1/gradle_basic_demo), testaram-se todas as tasks personalizadas realizadas no guião da Ca2, part1,
presentes no ficheiro build.gradle. Primeiro, foi executado o build do projeto, através do seguinte comando:

```
$ gradle build
```

A primeira task a ser testada foi a responsável pela execução do servidor do chat room. Foi possível observar a mensagem "The chat 
server is running...", que indica que o servidor está a correr, através do comando:

````
$ gradle runServer
````

De seguida, testou-se a task *runClient*, responsável pela execução do cliente no chat room. Para isso, abriu-se uma nova linha de
comando na CLI do Ubunu e tentou realizar-se nova ligação SSH à máquina virtual:

````
martalribeiro@DESKTOP-ABTOVG3:/mnt/c/Users/Marta$ ssh martalribeiro@192.168.59.5
ssh: connect to host 192.168.59.5 port 22: Resource temporarily unavailable
````

No entanto, não foi obtido sucesso pois não é possível abrir uma nova sessão pelo mesmo porto 22.

Desta forma, foi necessário recorrer à virtual box, foi aberta a janela da máquina virtual já a correr e, após login, tentou-se
executar a task do cliente, na pasta *root* do projeto, através do comando:

````
$ gradle runClient
````

No entanto, foi apresentada a seguinte mensagem de erro:

````
> Task :runClient FAILED
Exception in thread "main" java.awt.HeadlessException:
No X11 DISPLAY variable was set, but this program performed an operation which requires it.
        at java.awt.GraphicsEnvironment.checkHeadless(GraphicsEnvironment.java:204)
        at java.awt.Window.<init>(Window.java:536)
        at java.awt.Frame.<init>(Frame.java:420)
        at javax.swing.JFrame.<init>(JFrame.java:233)
        at basic_demo.ChatClient.<init>(ChatClient.java:35)
        at basic_demo.ChatClientApp.main(ChatClientApp.java:18)

FAILURE: Build failed with an exception.
````

Este erro ocorre devido a estarmos na presença da versão Ubuntu Server e não da versão Ubuntu com interface gráfica (Ubuntu Desktop).
Como o gradle basic demo é uma aplicação em que os clientes estão definidos através de uma pequena interface gráfica, para a podermos
executar, é necessário que a task *runClient* esteja a ser executada na máquina host, enquanto a task *runServer* corre na máquina virtual.

Para este efeito, colocou-se a máquina virtual a correr na opção *headless*, manteve-se a execução da *runServer* na ligação remota por
SSH à máquina virtual e executou-se a *runClient* a partir de uma nova linha de comando da máquina host. Existem duas formas para
executar a task do cliente: 

1. Aceder à pasta gradle_basic_demo na máquina host e alterar o ficheiro buil.gradle para que a task *runClient* seja alterada e configurada
para que os seus argumentos sejam o endereço de IP *192.168.59.5* e o porto *59001* configurado no servidor;
2. Aceder ao jar da aplicação que está presente na pasta build/libs/basic_demo-0.1.0.jar, após o build do projeto, e executar na CLI da
máquina host a classe "ChatClientApp" presente no jar da aplicação, e dando, como argumento, o endereço de IP *192.168.56.5* da máquina 
virtual e o porto *59001* configurado no servidor.

Optou-se por realiazar a segunda opção, através do seguinte comando:

````
java -cp basic_demo-0.1.0.jar basic_demo.ChatClientApp 192.168.56.5 59001
````

Desta forma, a task *runClient* é executada com sucesso, ficando então disponível a janela do chat room.

Outra justificação importante para testar que é possével correr o servidor e o cliente em máquinas distintas, prende-se ao facto de
numa situação real, o servidor e o cliente nunca serem executados na mesma máquina. Fora do ambiente de teste e quando a aplicação
está acessível ao público, normalmente o servidor de um chat room é executado sempre na mesma máquina, enquanto o cliente deve poder
ser executado em qualquer outra máquina desde que possua o endereço de IP do servidor e o porto de entrada adequado. 

De seguida, procedeu-se à execução das restantes tasks do ficheiro build.gradle. A task *test* correu com sucesso, bem como a task
*copySourceBackup*, em que se verificou a criação da pasta backup na pasta *root* do projeto, contendo os ficheiros provenientes da 
pasta source. A task *zipSource* foi também executada sem qualquer complicação, dando origem à pasta zipSource, também na pasta *root*
do projecto, contendo o arquivo source.zip com o conteúdo da pasta source.


### 1.7 Adicionar a tag ca3-part1

No final do Ca3, part1, marcou-se o master branch com a annotated tag Ca3-part1 e verificou-se que a tag tinha sido adicionada.
Para este efeito, executou-se:

````
$ git tag -a ca3-part1 -m "ca3-part1"
        
$ git push origin ca3-part1
        
$ git tag
Ant-ca2-part1
AntAltern
Ca1
Ca2-part2
ca2-part1
ca3-part1
v1.2.0
v1.3.0
v1.3.1
````


## Referências

* https://www.virtualbox.org/
* https://gradle.org/install/
* https://sdkman.io/


