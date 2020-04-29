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

**NOTA:** 

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

Como ferramenta de virtualização alternativa à Virtual Box, foi escolhido o Hyper-V.




## 3. Implementação de uma alternativa - Hyper-V

### 3.1 Preparação do Assignment

A preparação do assignment para Hyper-V foi similar à feita para o Virtual Box, mas neste caso a ferramenta de virtualização utlizada 
foi o Hyper-V da Microsoft. Neste ponto, marcou-se o master branch com a annotated tag HypervAltern.

Para instalar o Hyper-V não é necessário descarregar um ficheiro de instalação. Este está disponível para sistemas operativos Windows,
nomeadamente, em instalações a partir do Windows 8.1. Desta forma, apenas é necessário aceder a *Programs and Features*, clicar em *Turn
Windows features on or off* e selecionar a opção de "Hyper-V". Outra opção é instalar via PowerShell com o comando:

````
Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Hyper-V -All
````

### 3.2 Configuração do Hyper-V e adaptação do Vagrantfile utilizado no ponto 1.2

Para poder executar o ficheiro Vagrantfile, é necessário definir como *default provider* o Hyper-V dado que, sempre que se executa
o comanda *vagrant*, o hypervisor escolhido por omissão é o Virtual Box. Caso o Virtual Box não estiver instalado, o Vagrant vai tentar
realizar o dowload di Virtual Box para cumprir este requisito. Desta forma, sempre que é criado um novo ficheiro Vagrantfile, é necessário
definir o Hyper-V como default através da variável de ambiente "VAGRANT_DEFAULT_PROVIDER" e do seguinte comando:

````
[Environment]::SetEnvironmentVariable("VAGRANT_DEFAULT_PROVIDER", "hyperv", "User")
````

Outra forma de forçar a utilização do Hyper-V por parte do Vagrant é, sempre que o comando *vagrant up* é executado, adicionar a
seguinte opção:

````
vagrant up --provider hyperv
````

Outra questão importante é a configuração de rede das máquina virtuais que vão ser criadas. Como foi referido em cima, uma das
limitações de executar a configuração definida Vagrantfiles com Hyper-V é que o vagrant não é capaz de de realizar as configurações 
de rede necessárias no Hyper-V automaticamente (ao contrário do que aconteceu no Oracle VirtualBox. Por isso, outro passo necessário 
antes de se executar o ficheiro é configurar uma ligação de rede para, mais tarde, a máquina virtual poder aceder à internet. Para isso, 
é necessário criar um switch virtual externo, recorrendo ao Virtual Switch Manager do Hyper-V, como nome "External Switch".

Nesta fase, foi também definido no Vagrantfile que o hipervisor utilizado em ambas as máquinas virtuais foi o Hyper-V.

````
Vagrant.configure("2") do |config|
  config.vm.box = "envimation/ubuntu-xenial"
  config.vm.provider "hyperv"
````

De seguida, após inicialização da Power Shell em modo administrador, acedeu-se à pasta onde se encontra o Vagrantfile e este foi
executado com o seguinte comando:

````
vagrant up --provider hyperv
````

A maquina virtual "db" definida no Vagrantfile começou a ser construída mas surgiu a seguinte mensagem de erro:

````
The box you're attempting to add doesn't support the provider
you requested. Please find an alternate box or use an alternate
provider. Double-check your requested provider to verify you didn't
simply misspell it.
````

Desta forma, verificou-se que a box "envimation/ubuntu-xenial", que estava pre-definida no Vagrantfile para a construção de ambas
as máquinas virtuais, não suportava o Hyper-V.

````
vagrant box list envimation/ubuntu-xenial
    envimation/ubuntu-xenial (virtualbox, 1.0.3-1516241473)
````

Para contornar este problema, recorreu-se à documentação existente na página do Vagrant para encontrar uma box que suportasse o
Hyper-V. A box escolhida foi a "hashicorp/bionic64" que para além da virtual box também suporta Hyper-V. Foram realizadas as
seguintes alterações ao ficheiro Vagrantfile:

````
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/bionic64"
  config.vm.provider "hyperv"
...
config.vm.define "db" do |db|
    db.vm.box = "hashicorp/bionic64"
    db.vm.hostname = "db"
...
config.vm.define "web" do |web|
    web.vm.box = "hashicorp/bionic64"
    web.vm.hostname = "web"
````

Após esta configuração, voltou-se a executar novamente o ficheiro Vagrantfile. Apareceu a seguinta mensagem:

````
==> db: Verifying Hyper-V is enabled...
==> db: Verifying Hyper-V is accessible...
...
==> db: Successfully added box 'hashicorp/bionic64' (v1.0.282) for 'hyperv'!
...
    db: Please choose a switch to attach to your Hyper-V instance.
    db: If none of these are appropriate, please open the Hyper-V manager
    db: to create a new virtual switch.
    db:
    db: 1) Default Switch
    db: 2) External Switch
    db:
    db: What switch would you like to use?
````

Verfica-se então que a box "hashicorp/bionic64" foi adiconado com sucesso e que suporta o Hyper-V.

````
vagrant box list
    envimation/ubuntu-xenial (virtualbox, 1.0.3-1516241473)
    hashicorp/bionic64       (hyperv, 1.0.282)
````

Como o Hyper-V vai ignorar as configurações de rede existentes no Vagrantfile devido às suas limitações, durante a instalação 
das máquinas virtuais vai questionar o utilizador que placa de rede definida se quer escolher. Neste passo, deveria ser selecionado
a opçao 2) External Switch, mas como o objectivo é fazer a automatização deste processo, procedeu-se às seguintes alterações no 
ficheiro Vagrantfile:

````
    db.vm.network "private_network", ip: "192.168.33.11", bridge: "External Switch"
...
    web.vm.network "private_network", ip: "192.168.33.10", bridge: "External Switch"
````

Após esta configuração, voltou-se a executar novamente o ficheiro Vagrantfile. Apareceu a seguinta mensagem:

````
Vagrant requires administrator access for pruning SMB shares and
may request access to complete removal of stale shares.
==> db: Preparing SMB shared folders...
    db: You will be asked for the username and password to use for the SMB
    db: folders shortly. Please use the proper username/password of your
    db: account.
    db:
    db: Username:
````

Durante a criação de uma máquina virtual com uma box hashicorp/precise64, o Vagrant tenta utilizar SMB synced folders que são 
ficheiros que realizam a sincronização entre a máquina host e a máquina virtual de forma a melhorar a performance em sisitemas 
operativos Windows relativamente a outros hipervisors. Como esta configuração não é pretendida, acrecentou-se o seguinte ao 
Vagrantfile:

````
Vagrant.configure("2") do |config|
  config.vm.box = "hashicorp/bionic64"
  config.vm.provider "hyperv"
  # disabling SMB shared folders (not needed)
  config.vm.synced_folder ".", "/vagrant", disabled: true
````

Nesta fase foram também implementadas duas configurações adicionais, de forma a melhorar a experiência com o Vagrant. Quando o comando 
*vagrat up* é executado, uma grande porção deste tempo é utilizado a clonar a *hard drive* virtual. Com o Hyper-V existem mecanismos
que ajudam a acelerar este processo através da utilização de *linked cloning*. Outra funcionalidade adicional interssante é a ativação
de *virtualization extensions* que vão permitir a nested virtualization, em Hyper-V. Para isso, foram adicionadas as seguintes configurações:  

````
db.vm.provider "hyperv" do |v|
  v.memory = 1024
  v.enable_virtualization_extensions = true
  v.linked_clone = true
end
...
web.vm.provider "hyperv" do |v|
  v.memory = 1024
  v.enable_virtualization_extensions = true
  v.linked_clone= true
end
````

**NOTA:** Sempre que o ficheiro Vagrantfile foi alterado, a máquina virtual previamente criada foi destruída através do seguinte
comando:

````
vagrant destroy
````

##### Os pontos 3.3, 3.4 e 3.5 foram realizada de forma idêntica à realizada em nos pontos 1.3, 1.4 e 1.5.


### 3.6 Executar o novo vagrantfile após as alterações às aplicação gradle basic da spring application

Após todas a alterações mencionadas nos pontos 3.2 a 3.5, executou-se o ficheiro Vagrant file a partir da linha de comandos
da Power Shell.

````
$ vagrant up
````


carregar nos links do readme e ver a app a correr

os links para acesso dependem do ip definido pelo hiperv na altura da criação

````
http://192.168.1.93:8080/tut_basic_gradle-0.0.1-SNAPSHOT/
````

correr o link da base de dados


````
http://192.168.1.93:8080/tut_basic_gradle-0.0.1-SNAPSHOT/h2-console
````

não conseguiu aceder à base de dados, não dão os links


**Spring Application**

https://drive.google.com/file/d/1qQQUrG-wDZI809N6SDsVcYE2rwOPuxcO/view

**Base de Dados H2**

https://drive.google.com/file/d/1wR8XMOzJjMHGp1_Y7U9xg5N-HaiEDz3D/view


Isto deve-se as limitações do Hyper-v!!!



de seguida

desliguei e destrui ambas as maquinas

````
vagrant halt

vagrant destroy
````



## Referências

* https://www.vagrantup.com/downloads.html
* https://www.vagrantup.com/docs/hyperv/
* https://www.vagrantup.com/docs/hyperv/usage.html
* https://www.vagrantup.com/docs/hyperv/configuration.html
* https://www.vagrantup.com/docs/hyperv/limitations.html
* https://www.vagrantup.com/docs/synced-folders/smb.html
* https://app.vagrantup.com/hashicorp/boxes/bionic64
* https://techcommunity.microsoft.com/t5/virtualization/vagrant-and-hyper-v-tips-and-tricks/ba-p/382373
* https://github.com/hashicorp/vagrant/blob/master/website/source/intro/getting-started/boxes.html.md
* https://stackoverflow.com/questions/57796239/vagrant-not-adhering-to-static-ip-definition-in-vagrant-file
* https://www.vagrantup.com/docs/boxes/info.html
* https://superuser.com/questions/1354658/hyperv-static-ip-with-vagrant
* https://help.moonrivers.com/support/solutions/articles/1000226850-creating-vm-clones-in-microsoft-hyper-v




