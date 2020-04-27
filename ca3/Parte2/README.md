# Relatório Class Assignment 3 - Virtualização com Vagrant

## Class Assignment 3, parte 2 - Utilizar o Vagrant para configurar um ambiente virtual que permita a execução do Tutorial Spring
Boot Application, Gradle Basic Version 


## 1. Análise, Design e Implementação

### 1.1 Preparação do Assignment

Para a realização da Class Assignment 3 (Ca3), parte 2 foi utilizado o sistema operativo Windows na máquina host e para poder
cumprir todos os requisitos necessários para a sua implementação foi necessário:

  * acesso à Power Shell do Windows;
  * uma ferramenta de virtualização (neste caso, foi utlizado a Virtual Box da Oracle);
  * ter instalado o Vagrant (neste caso, foi utilizado o gestor de pacotes apt);
  * um repositório remoto (neste caso, foi utilizado o Bitbucket);
  * o Spring Boot Tutorial Basic Project em gradle utilizada no Class Assignment 2, parte 2 proveniente do Tutorial React.js and Spring
  Data REST application;


### 1.2 Descarregar e explorar o Vagrantfile disponível em https://bitbucket.org/atb/vagrant-multi-spring-tut-demo/

descarregar, ler e fazer o README!!

ver como são criadas duas maquinas virtuais no ficheiro

web: this VM is used to run tomcat and the spring boot basic application
db: this VM is used to execute the H2 server database

ver explicação do professor para explicar o vagrantfile

realizar o comando na power shell

````
$ vagrant up
````

base de dados é sempre a primeira porque a parte web precisa de aceder à base de dados
depois ver as maquinas criadas na virtual box e correr o comando 

````
$ vagrant status
````
ver o seguinte 

````
Current machine states:

db                        running (virtualbox)
web                       running (virtualbox)
````

podemos por vagrant status db ou web para ver so o status de um a maquina e podemos ligar/desligar só uma também especificando
a maquina que queremos

````
$ vagrant halt web
$ vagrant halt web
````

carregar nos links do readme e ver a app a correr

````
http://localhost:8080/basic-0.0.1-SNAPSHOT/
````

e a base de dados a correr
como há persisitencia sempre que arrancamos a vm ele cria nova linha para o frodo baggins porque essa parte da app não foi
alterada porque era usada quando se executa a app em memoria

sempre que fizer reload da vms ele adiciona uma linha igual

````
$ vagrant reload
````



````
http://localhost:8080/basic-0.0.1-SNAPSHOT/h2-console
````

para aceder conectar à base de dados é necessário utilizar um url especifico, que está definido no vagrantfile na linha 37 que é 
*jdbc:h2:tcp://192.168.33.11:9092/./jpadb*

clicar em teste connection - Test successful

e depois ligar à base de dados

inserir dados na base de dados e ver no browser a alterar

````
insert into employee values (3, 'wizard', 'Gandalf', 'Gandalf');
````

e ver se aparece na parte web - sucesso

desliguei ambas as maquinas




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




### 1.3 Copiar o Vagrantfile para o repositório pessoal Bitbucket


copiar o ficheiro para Ca3, parte 2 e passar para o repositorio local e remoto.



### 1.4 Atualizar a configuração do Vagrantfile de forma a executar a versão gradle da spring application utilizada no Ca2, parte2


nas linhas 70 e 71, alterar estas linhas para fazer clone do meu repositorio para a maquina virtual web

````
    git clone https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git
    cd devops-19-20-a-1191779/ca3/Parte2/tut_basic_gradle
````

fiz commit das alteracoes

````
$ git add .
$ git commit -m "fix #2"
$ git push -u origin master
````

### 1.5 Atualizar a versão gradle basic da spring application para que use o servidor H2 na máquina virtual "db"





### 1.7 Adicionar a tag ca3-part2

No final do Ca3, part2, marcou-se o master branch com a annotated tag Ca3-part3 e verificou-se que a tag tinha sido adicionada.
Para este efeito, executou-se:

````
$ git tag -a ca3-part2 -m "ca3-part2"
        
$ git push origin ca3-part2
        
$ git tag
Ant-ca2-part1
AntAltern
Ca1
Ca2-part2
ca2-part1
ca3-part1
ca3-part2
v1.2.0
v1.3.0
v1.3.1
````


## Referências

* https://www.vagrantup.com/downloads.html


