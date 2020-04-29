# Relatório Class Assignment 3 - Virtualização com Vagrant

## Class Assignment 3, parte 2 - Utilizar o Vagrant para configurar um ambiente virtual que permita a execução do Tutorial Spring Boot Application, Gradle Basic Version 


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
$ vagrant halt db
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
insert into employee values (3, 'wizard', 'Gandalf', null);
````

e ver se aparece na parte web - sucesso

desliguei ambas as maquinas



ver se tudo corre bem

ver novamente os links e verificar se houve sucesso



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
    cd devops-19-20-a-1191779/ca3/Parte2/basic
````

como se verificou por ligação ssh que o ficheiro gradlew não tinha permissões de execução para a root foi necessário adicionar o
seguinte comando:

````
    sudo chmod u+x gradlew
````

para ficar assim

````
-rwxr--r--   1 root root   5764 Apr 28 18:16 gradlew
````

de seguida manteve-se a linha do comando build e foi necessário mudar o nome do war que vai ser gerado pelo build do gradle para
tut_basic_gradle-0.0.1-SNAPSHOT.war, que vai ser expandido quando for copiado para a pasta tomcat8/webapps

```` 
    sudo ./gradlew clean build
    # To deploy the war file to tomcat8 do the following command:
    sudo cp build/libs/tut_basic_gradle-0.0.1-SNAPSHOT.war /var/lib/tomcat8/webapps
````

fiz commit das alteracoes

````
$ git add .
$ git commit -m "fix #29"
$ git push -u origin master
````

### 1.5 Atualizar a versão gradle basic da spring application para que use o servidor H2 na máquina virtual "db"

fiz as alterações necessárias para que aplicação corresse no interior da VM web


* suporte para a construção do war file:

nova classe ServletInitializer

````
package com.greglturnquist.payroll;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ReactAndSpringDataRestApplication.class);
    }
}
````

adicionar às dependencias no build.gradle

````
providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
````

adionar aos plugins no build.gradle

````
id 'war'
````

o war que vai ser construído vai ter o nome *tut_basic_gradle-0.0.1-SNAPSHOT*

* alterações ao ao ficheiro app.js para Application context path.

na linha 18 passa de 

````
    client({method: 'GET', path: '/api/employees'}).done(response => {
````

para

````
    client({method: 'GET', path: '/tut_basic_gradle-0.0.1-SNAPSHOT/api/employees'}).done(response => {
````

* alterações ao ficheiro index HTML

corrigir o path para o ficheiro de CSS

passa de 

````
<link rel="stylesheet" href="/main.css" />
````

para

````
<link rel="stylesheet" href="main.css" />
````

* alterar o ficheiro aplications.properties

````
server.servlet.context-path=/basic-0.0.1-SNAPSHOT
spring.data.rest.base-path=/api
#spring.datasource.url=jdbc:h2:mem:jpadb
# In the following settings the h2 file is created in /home/vagrant folder
spring.datasource.url=jdbc:h2:tcp://192.168.33.11:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
# So that spring will no drop de database on every execution.
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
````

onde se colocou todas as configurações necessárias para o servidor H2 e o context path para o war


### 1.6 Executar o novo vagrantfile após as alterações às aplicação gradle basic da spring application

executar o vagrant file

realizar o comando na power shell

````
$ vagrant up
````


carregar nos links do readme e ver a app a correr

````
http://localhost:8080/tut_basic_gradle-0.0.1-SNAPSHOT/
````

e a base de dados a correr
como há persisitencia sempre que arrancamos a vm ele cria nova linha para o frodo baggins porque essa parte da app não foi
alterada porque era usada quando se executa a app em memoria

sempre que fizer reload da vms ele adiciona uma linha igual

````
$ vagrant reload
````

correr o link da base de dados


````
http://localhost:8080/tut_basic_gradle-0.0.1-SNAPSHOT/h2-console
````

para aceder conectar à base de dados é necessário utilizar um url especifico, que está definido no vagrantfile na linha 37 que é 
*jdbc:h2:tcp://192.168.33.11:9092/./jpadb*

clicar em teste connection - Test successful

e depois ligar à base de dados

inserir dados na base de dados e ver no browser a alterar

````
insert into employee values (3, 'wizard', 'gandalf@lotr.com', 'Gandalf', null, null);
````

e ver se aparece na parte web - sucesso

fiz ligação ssh e verfiquei que o war tinha sido copiado

````
$ cd /var/lib/tomcat8/webapps

$ls
drwxrwxr-x 4 tomcat8 tomcat8     4096 Apr 28 18:18 .
drwxr-xr-x 4 root    root        4096 Apr 28 18:15 ..
drwxr-xr-x 3 root    root        4096 Apr 28 18:15 ROOT
drwxr-xr-x 5 tomcat8 tomcat8     4096 Apr 28 18:18 tut_basic_gradle-0.0.1-SNAPSHOT
-rw-r--r-- 1 root    root    42355482 Apr 28 18:18 tut_basic_gradle-0.0.1-SNAPSHOT.war
````

fui verficar que o tomcat era ums dos processos ativos

````
$ ps aux

tomcat8    565  3.6 51.4 2745404 522428 ?      Sl   18:23   0:32 /usr/lib/jvm/java-8-openjdk-amd64/bin/java -Djava.util.root
````

para vermos o estado do processo tomcat8

````
$ systemctl status tomcat8

● tomcat8.service - LSB: Start Tomcat.
   Loaded: loaded (/etc/init.d/tomcat8; bad; vendor preset: enabled)
   Active: active (running) since Tue 2020-04-28 18:23:39 UTC; 32min ago
     Docs: man:systemd-sysv-generator(8)
  Process: 537 ExecStart=/etc/init.d/tomcat8 start (code=exited, status=0/SUCCESS)
   CGroup: /system.slice/tomcat8.service
           └─565 /usr/lib/jvm/java-8-openjdk-amd64/bin/java -Djava.util.logging.config.file=/var/lib/tomcat8/conf/logging.properties -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -Djava.awt.headless=true -Xmx12
8m -XX:+UseConcMarkSweepGC -Djava.endorsed.dirs=/usr/share/tomcat8/endorsed -classpath /usr/share/tomcat8/bin/bootstrap.jar:/usr/share/tomcat8/bin/tomcat-juli.jar -Dcatalina.base=/var/lib/tomcat8 -Dcatalina.home=/usr/share/tomcat8
 -Djava.io.tmpdir=/tmp/tomcat8-tomcat8-tmp org.apache.catalina.startup.Bootstrap start
````

de seguida

desliguei ambas as maquinas





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
HypervAltern
ca2-part1
ca3-part1
ca3-part2
v1.2.0
v1.3.0
v1.3.1
````


## 2. Análise de uma alternativa




## 3. Implementação de uma alternativa - Hyper-V

### 3.1 Preparação do Assignment

A preparação do assignment para Hyper-V foi similar à feita para o Virtual Box, mas neste caso a ferramenta de virtualização utlizada 
foi o Hyper-V da Microsoft. Neste ponto, marcou-se o master branch com a annotated tag HypervAltern.

### 3.2 Adaptar o Vagrantfile utilizado no ponto 1.2 para o Hyper-V




### 3.3 Copiar o Vagrantfile para o repositório pessoal Bitbucket



### 3.4 Atualizar a configuração do Vagrantfile de forma a executar a versão gradle da spring application utilizada no Ca2, parte2



### 3.5 Atualizar a versão gradle basic da spring application para que use o servidor H2 na máquina virtual "db"



### 3.6 Executar o novo vagrantfile após as alterações às aplicação gradle basic da spring application



````
==> db: Verifying Hyper-V is enabled...
==> db: Verifying Hyper-V is accessible...
...
==> db: Successfully added box 'hashicorp/bionic64' (v1.0.282) for 'hyperv'!
````


````
==> db: Please choose a switch to attach to your Hyper-V instance.
    db: If none of these are appropriate, please open the Hyper-V manager
    db: to create a new virtual switch.
    db:
    db: 1) Default Switch
    db: 2) Virtual Switch
````


### 3.7 Adicionar a tag HyperV-ca3-part2





## Referências

* https://www.vagrantup.com/downloads.html


