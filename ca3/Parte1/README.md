# Relatório Class Assignment 3 - Virtualização com Vagrant

## Class Assignment 3, parte 1 - Utilizar a Virtual Box VM com Ubuntu para correr os projetos realizados nos assignments anteriores


## 1. Análise, Design e Implementação

### 1.1 Preparação do Assignment

Para a realização da Class Assignment 3 (Ca3), parte 1 foi sugerido utilizar o virtual box para construir uma máquina virtual 
usando o Ubuntu como distribuição Linux para o sistema operativo.

Na realização desta tarefa, na máquina Host foi utilizada a distribuição Linux Ubuntu 18.04 LTS e para poder cumprir todos os 
requisitos necessários para a sua implementação foi necessário:

  * acesso à CLI do Ubuntu;
  * uma ferramenta de virtualização (neste caso, foi utlizado o Virtual Box);
  * um repositório remoto (neste caso, foi utilizado o Bitbucket);
  * o Spring Boot Tutorial Basic Project utilizada no Class Assignment 1 proveniente do Tutorial React.js and Spring Data REST 
  application;
  * o gradle_basic_demo utilizado no Class Assignment 2 e o respectivo jar, basic_demo-0.1.0.jar.


### 1.2 Criar uma máquina virtual seguindo os passos da aula teórica "Introdução à Virtualização"



pus a correr a maquina virtual em modo headless no virtual box

acedi à maquina virtual por ssh na minha Ubunt CLI

````
$ ssh martalribeiro@192.168.56.5
````


### 1.3 Realizar um clone do repositório pessoal no interior da máquina virtual

fiz clone no home directory e verifiquei se estava lá

````
$ git clone https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git

$ ls
````

configurei os meus dados de commit

````
$ git config --global user.name "Marta Ribeiro"
$ git config --global user.email 1191779@isep.ipp.pt
````

### 1.4 Instalar o software necessário para correr as aplicações contidas no repositorio

instalei o git, jdk, maven

````
$ sudo apt install git

$ sudo apt install openjdk-8-jdk-headless

$ sudo apt install maven
````

gradle, sdk, zip, unzip - segui as instruções do gradle.org

````
$ curl -s "https://get.sdkman.io" | bash

$ sudo apt install unzip

$ sudo apt install zip

source "/home/martalribeiro/.sdkman/bin/sdkman-init.sh"

$ sdk install gradle 6.3
````


### 1.5 Realizar o build e a execução do Spring Boot Tutorial Basic Project

testei a execução de alguns goals definidos neste projeto em maven
```
$ mvn compile

$ mvn test
```

em ambos o build foi realizado com sucesso.

depois corri a aplicação através do comando

````
mvn spring-boot:run
````


como é uma web app tenho de correr o frontend no browser do host porque a maq virtual nao tem interface gráfica nem browser

a app está então a correr no porto 8080 da VM cuja placa de rede que faz ligação à maquina host está configurado com o 
Ip address 196.168.56.5
Por isso, para aceder à app no browser da máquina host tenho de colocar o seguinte endereço no browser.

````
http://192.168.56.5:8080/
````

Neste caso, a app execuatada está disponível no endereco *192.168.56.5:8080*.




### 1.6 Realizar o build e a execução do Gradle Basic Demo

tentei primeiro realizar o build do projeto e executar todas as tasks desenvolvidas no ca2 part1:

```
$ gradle build
```

servidor - com sucesso

````
$ gradle runServer
````

cliente - tentei abrir nova linha de comandos e fazer nova ligação ssh mas não deixou porque já tinha a outra sessão ssh aberta neste porto

````
martalribeiro@DESKTOP-ABTOVG3:/mnt/c/Users/Marta$ ssh martalribeiro@192.168.59.5
ssh: connect to host 192.168.59.5 port 22: Resource temporarily unavailable
````

tive de ir à VM e abrir outro terminal sem ser o tty1, abri o tty3, fiz login novamente e tentei correr o cliente, deu erro porque
não existe uma interface gráfica. executei

````
$ gradle runClient
````

apareceu o erro

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

porque estamos num Ubuntu Server e não no Ubuntu com interface gráfica (Ubuntu Desktop)

como é uma app em que os clientes estão definidos através de um interface gráfica, tenho de correr a tarefa cliente no host enquanto
o server corre na VM, executei no host através do execução da classe "ChatClientApp" presente no jar da aplicação.

````
java -cp basic_demo-0.1.0.jar basic_demo.ChatClientApp 192.168.56.5 59001
````

o cliente correu com sucesso.

porque servidor e cliente acedem À aplicação por máquinas distintas. Numa situação real, enquanto o servidor do chat é executado 
normalmente sempre na mesma máquina, o cliente deve poder ser executado em qualquer outra máquina desde que possua o endereço
de ip do servidor e o porto de entrada. 


tarefa test - com sucesso

tarefa copySourceBackup - com sucesso

tarefa zipSource - com sucesso



## Referências

* https://www.virtualbox.org/
* https://gradle.org/install/
* https://sdkman.io/


