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

Após descarregar o Vagrantfile do repositório indicado, procedeu-se à análise do ficheiro.

Numa primeira fase, é feita uma configuração geral para a criação das duas máquinas virtuais: define-se a box da qual se vão
criar as máquinas - "envimation/ubuntu-xenial" e, de seguida, define-se todo o software que é necessário instalar em ambas.

O segundo passo passa por configurar a máquina "db" que vai ser utilizada para executar o servidor H2 da base de dados, onde a
web application se vai conectar. Nesta secção é definida o endereço de IP da máquina bem como os portos por onde se vai realizar a
ligação à maquina host (8082) e outro para se poder aceder ao servidor H2 (9092). Finalmente, descarrega-se o servidor H2 atrvés do 
comando *wget* e executa-se o comando para correr o servidor, definindo que este comando deverá ser sempre executado quando a máquina 
é ligada. Para a conexão à base de dados, é necessário o URL: **jdbc:h2:tcp://192.168.33.11:9092/./jpadb**.

O terceiro passo passa por configurar a máquina "web" que vai ser utilizada para executar o tomcat e a web application no seu interior.
Nesta secção define-se também o endereço de IP da máquina bem como os portos por onde o tomcat vai ficar disponível a partir da maquina
host (8080). De seguida, é definido a memória RAM da máquina virtual (1024) e finalmente é feita a instalação de todo o software necessário
para poder executar a web application.

Finalmente, é feito o clone do repositório remoto onde a aplicação se encontra, acede-se à pasta da aplicação e faz-se o build do ficheiro
build.gradle aí contido. De seguida, faz-me uma cópia do ficheiro war gerado no build para a pasta */var/lib/tomcat8/webapps* do tomcat, que 
quando detectar este ficheiro, vai automaticamente expandi-lo e assim executar a *web application*.

Após esta análise, foram seguidos os passos descritos no README disponível no repositório indicado. Foi criada uma pasta local na 
máquina host e foi colocado aqui o VagrantFile. Executaram-se então os seguintes comandos na linha de comandos da PowerShell:

````
$ vagrant up
````

Este comando vai dar início à execução do Vagrantfile e à construção das duas máquinas virtuais. A máquina **db** é sempre a primeira
a ser construída porque a máquina **web** vai precisar de aceder à base de dados posteriormente. Após a execução do ficheiro ser finalizada,
é possível verficar que as maquinas foram criadas na virtual box e correndo o seguinte comando:

````
$ vagrant status
````

Vai ser possível ver o estado de ambas as máquinas virtuais:

````
Current machine states:

db                        running (virtualbox)
web                       running (virtualbox)
````

Se for necessário, a partir do Vagrantfile podemos ligar/desligar ambas as máquinas ou se especificarmos o nome à frente
dos comandos, apenas ligar/desligar a máquina pretendida:

````
$ vagrant halt

$ vagrant halt web
$ vagrant halt db

$ vagrant up web
$ vagrant up db

$ vagrant reload
````

Na ultima linha encontra-se o comando reload que é utilizado para reiniciar as máquinas virtuais caso pretendido.
Com ambas as máquinas a correr, pode-se aceder à web application no brownser a partir do seguinte endereço:

````
http://localhost:8080/basic-0.0.1-SNAPSHOT/
````

Nesta página conseguimos observar o resultado final da web application, que é o esperado.
Pode-se aceder também ao servidor H2 no brownser, a partir do seguinte endereço:

````
http://localhost:8080/basic-0.0.1-SNAPSHOT/h2-console
````

Para se realizar a conexão à base de dados, é necessário introduzir o URL definido no Vagrantfile na linha 37 - 
**jdbc:h2:tcp://192.168.33.11:9092/./jpadb**

A ligação é feita com sucesso e, desta forma, podemos introduzir dados na base de dados e depois verificar se, no lado da 
aplicação, o resultado é visível:

````
insert into employee values (3, 'wizard', 'Gandalf', null);
````

Ao voltar à pagina do browser da aplicação, é feita a atualização da página e é possivel ver os dados introduzidos.
Finalmente, após a exploração de toda a aplicação, é feita destruição de ambas as máquinas através do comando:

````
vagrant destroy
````

### 1.3 Copiar o Vagrantfile para o repositório pessoal Bitbucket

Para realizar esta tarefa, é criada a pasta *Parte2* dentro da pasta *ca3* já existente e faz-se cópia do Vagrantfile descarregado.
De seguida, executam-se os seguintes comandos:

````
$ git add .
$ git commit -m "fix #29"
$ git push -u origin master
````

### 1.4 Atualizar a configuração do Vagrantfile de forma a executar a versão gradle da spring application utilizada no Ca2, parte2

Para executar a versão gradle da aplicação, é necessário realizar algumas alterações ao ficheiro Vagrantfile fornecido inicialmente.
Nas linhas 70 e 71, deve-se alterar o comando *git clone* para que seja possível realizar o clone do repositorio remoto pessoal para a
maquina virtual **web**. O comando *cd* dever ser também alterado para que se possa aceder à pasta onde se encontra a aplicação:

````
    git clone https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git
    cd devops-19-20-a-1191779/ca3/Parte2/basic
````

Ao executar o Vagrantfile, verificou-se por ligação **ssh** à máquina **web** que o ficheiro gradlew não tinha permissões de execução para a root.
  
````
vagrant ssh web
```` 
 
````
-rw-r--r--   1 root root   5764 Apr 28 18:16 gradlew
```` 
 
Desta forma, foi necessário adicionar o seguinte comando ao Vagrantfile:

````
    sudo chmod u+x gradlew
````

Para obter o seguinte resultado:

````
-rwxr--r--   1 root root   5764 Apr 28 18:16 gradlew
````

De seguida, manteve-se a linha do comando build do gradle, mas foi necessário mudar a linha que executa a cópia do ficheiro war
para a pasta *webapps* do tomcat. Como o nome do ficheiro war que vai ser gerado pelo build do gradle é diferente do existente,
teve de alterar o nome do ficherio para *tut_basic_gradle-0.0.1-SNAPSHOT.war*:

```` 
    sudo ./gradlew clean build
    # To deploy the war file to tomcat8 do the following command:
    sudo cp build/libs/tut_basic_gradle-0.0.1-SNAPSHOT.war /var/lib/tomcat8/webapps
````

No final de todas as alterações,fez-se commit e push a partir dos respectivos comandos Git


**NOTA:** Sempre que o ficheiro Vagrantfile foi alterado, a máquina virtual previamente criada foi destruída.


### 1.5 Atualizar a versão gradle basic da spring application para que use o servidor H2 na máquina virtual "db"

Para que a versão basic da application seja capaz de utilizar o servidor H2 que está a correr na máquina virtual *db*, é necessário
fazer as alterações necessárias ao projeto para que aplicação corra no interior da máquina virtual **web**. Para isso:

1.Dar suporte para a construção do war file a partir de: 

-uma nova classe ServletInitializer:

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

-adição da informação do servidor na secção das dependencias no ficheiro **build.gradle**:
    
    
    ````
    providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
    ````
    
    Adição de informação de suporte ao war nos plugins do ficheiro **build.gradle**:
    
    
    ````
    id 'war'
    ````

    O war construído irá ter o nome *tut_basic_gradle-0.0.1-SNAPSHOT*.

-alterações ao ficheiro app.js para definar a context path da aplicação:

    Na linha 18 passa de:
    
    ````
        client({method: 'GET', path: '/api/employees'}).done(response => {
    ````
    
    Para:
    
    ````
        client({method: 'GET', path: '/tut_basic_gradle-0.0.1-SNAPSHOT/api/employees'}).done(response => {
    ````

2.Alterações ao ficheiro index HTML:

    Corrição do path para o ficheiro de CSS que passa de:
    
    ````
    <link rel="stylesheet" href="/main.css" />
    ````
    
    Para:
    
    ````
    <link rel="stylesheet" href="main.css" />
    ````

3.Alterar o ficheiro aplications.properties para a configuração da base de dados H2:

    ````
    server.servlet.context-path=/tut_basic_gradle-0.0.1-SNAPSHOT
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

    Aqui foram colocadas todas as configurações necessárias para o servidor H2 e a context path para o ficheiro war.


### 1.6 Executar o novo vagrantfile após as alterações às aplicação gradle basic da spring application

Após todas as alterações necessaários ao ficheiro Vagrantfile e ao projeto gradle basic, executou-se o Vagrantfile a partir da
linha de comandos da PowerShell:

````
vagrant up
````

Após conclusão do comando e com ambas as máquinas virtuais construídas e a correr, recorreu-se ao browser na máquina host para se aceder
ao seguinte endereço:

````
http://localhost:8080/tut_basic_gradle-0.0.1-SNAPSHOT/
````

A página foi carregada com sucesso, permitindo assim concluir que a execução do Vagrantfile com todas as alterações feitas ao
ficheiro e ao projeto gradle foi realizado com secesso.
De seguida acedeu-se também ao servidor H2 no brownser, a partir do seguinte endereço:

````
http://localhost:8080/tut_basic_gradle-0.0.1-SNAPSHOT/h2-console
````

A página foi carregada com sucesso e é possivel observar a janela de login na base de dados. Foi introduzido o URL correcto -
**jdbc:h2:tcp://192.168.33.11:9092/./jpadb** e depois introduziram-se os seguintes dados na base de dados:

````
insert into employee values (3, 'wizard', 'gandalf@lotr.com', 'Gandalf', null, null);
````

Ao voltar à pagina do browser da aplicação, é feita a atualização da página e é possivel ver os dados introduzidos, após 
*reload* da máquina virtual **web**. Finalmente, é feita destruição de ambas as máquinas através do comando:

````
vagrant destroy
````

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

A Virtual Box é uma ferramenta de virtualização open-source pertencente à Oracle e é classificada um *hypervisor* de tipo 2, 
ou seja, instala as máquinas virtuais sobre o sistema operativo da máquina host, tal como qualquer outro software instalado. 
Assim, a máquina virtual é executada como um processo na máquina host, partilhando o hardware do sistema, mas a gestão da máquina
virtual é feita através da maquina host em vez de os comandos serem diretamente executados. Como consequência, existe um ligeiro
atraso entre as ações realizadas.

O Hyper-V é um *bare-metal hypervisor* pertencente à Microsoft e é classificado como hypervisor de tipo 1 que corre directamente no
hardare da máquina host. Desta forma, não é necessária a instalação adicional de um *package* externo e é possivel a gestão directa do
sistema operativo da máquina virtual. O Hyper-V especificamente permite a virtualização de hardware, ou seja, a máquina virtual está a
ser executada em hardware virtual, e permite a criação de discos duros virtuais, switches virtuais, etc, que no fim podem ser adicionados
à máquina virutal.

Em termos comparativos, a Virtual Box permite criar uma máquina virtual mais facilmente pois disponibiliza um assistente que guia o utilizador 
na criação da máquina. Por outro lado, tem a desvantagem de exigir a instalação de software e também é um hypervisor com um nível de performance
inferior ao Hyper-V. O Hyper-V já é um hypervisor que exige um pouco mais de conhecimento para a criação adequada de uma máquina virtual
sem problemas de configuração. No entanto, possui a vantagem de atingir elevados níveis de performance e não necessitar da instalação de
software.

Em termos da utilização do Vagrant, o Hyper-V tem uma desvantagem grande relativamente ao Virtual Box porque o Vagrant neste hypervisor
não consegue criar nem configurar novas ligações de rede. Quando uma máquina virtual é lançada com o Hyper-V, o Vagrant de imediato
questiona a que switch virtual se deseja ligar a máquina virtual e vai gerar automaticamente um endereço de IP. Como resultado desta 
configuração inicial, todas as configurações de rede definidas no VagrantFile vão ser ignoradas e o Vagrant nunca irá conseguir estabelecer
um endereço IP de forma estática ou configurar automaticamente um NAT.



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
de rede necessárias no Hyper-V automaticamente (ao contrário do que aconteceu no Oracle VirtualBox). Por isso, outro passo necessário 
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

Após conclusão do comando e com ambas as máquinas virtuais construídas e a correr, recorreu-se ao browser na máquina host para se aceder
ao seguinte endereço:

````
http://192.168.1.93:8080/tut_basic_gradle-0.0.1-SNAPSHOT/
````

De seguida, acedeu-se também ao servidor H2 no brownser, a partir do seguinte endereço:

````
http://192.168.1.93:8080/tut_basic_gradle-0.0.1-SNAPSHOT/h2-console
````

O endereço de IP utilizado para estes endereços depende do endereço de IP que é definido pelo Hyper-V na altura da criação da máquina
virtual **web** e é automaticamente gerado pelo hypervisor quando se realiza a configuração inicial com o External Switch. Em ambas as
situações a página não foi carregada com sucesso, tendo-se observado as seguintes mensagens:

**Spring Application**

````
HTTP Status 404 – Not Found
Type Status Report

Message /tut_basic_gradle-0.0.1-SNAPSHOT/

Description The origin server did not find a current representation for the target resource or is not willing to disclose
that one exists.

Apache Tomcat/8.5.39 (Ubuntu)
````

**Base de Dados H2**

````
HTTP Status 404 – Not Found
Type Status Report

Message /tut_basic_gradle-0.0.1-SNAPSHOT/h2-console

Description The origin server did not find a current representation for the target resource or is not willing to disclose that one exists.

Apache Tomcat/8.5.39 (Ubuntu)
````

Este resultado pode ser explicado à luz das limitações que foram referidas anteriomente relativamente à utilização do Vagrant com 
o Hyper-V. Mesmo estando definidas no Vagrantfile as configurações de rede necessárias para que exista a ligação do servidor H2 ao 
servidor da aplicação web, como é utilizado o Hyper-V, o Vagrant vai ignorar todas estas configurações e vai assumir o endereço de IP
configurado automaticamente na criação das máquinas virtuais. De cada vez que é feita a criação da máquina virtual neste hypervirsor, o
endereço de IP gerado vai ser sempre diferente e não é possível definir um endereço de IP estático para configurar os dois servidores.

Desta forma, quando acedemos ao brownser a partir do endereço de IP gerado, é possível aceder à máquina **web** onde está a correr o
tomcat mas no entanto como não há comunicação com o servidor da base de dados, não é possivel aceder aos dados disponibilizados pela 
aplicação web e é verificado a mensagem de erro **404 - Not Found**.

Uma das possíveis soluções para este problema é realizar a configuração manual dos endereços de IP por ligação *ssh* às máquinas virtuais,
após estas serem construídas, e configurar também manualmente no Hyper-V o hardware das placas de rede a ser virtualizado. No entanto, o 
objectivo do ca3, parte 2 é precisamente automatizar estas configurações no Vagrantfile para que não seja necessário realizar as configurações
manuais. Por enquanto, o Vagrant ainda não possui esta funcionalidade no Hyper-V, mas na documentação oficial é referido que será uma funcionalidade
a existir numa versão futura.

Desta forma, existem outras soluções possíveis para contornar este problema e tornar o processo mais automatizado. Um das 
soluções encontradas seria criar um switch NAT para o Hyper-V e realizar algumas configurações complexas no Vagrantfile que
irão definir um IP estático que está no *range* do switch NAT criado. Assim, após o *vagrant up* e quando for ativada uma das configurações
do Vagrantfile que vai obrigar ao reload da maquina virtual, a placa de rede definida para a máquina passa a ser o switch NAT e a maquina
irá adquirir o endereço de IP estático definido anteriomente. Embora mais automatizada, continua a ser uma solução que requere alguma 
configuração manual, não sendo portanto ainda a ideal.

Nenhuma das soluções referidas anteriormente foram implementadas devido à complexidade exigida pela tarefa.

Finalmente, é feita destruição de ambas as máquinas através do comando:

````
vagrant destroy
````


## Referências

* https://www.vagrantup.com/downloads.html
* https://www.virtualbox.org/
* https://www.makeuseof.com/tag/virtualbox-vs-vmware-vs-hyper-v/
* https://www.vagrantup.com/docs/virtualbox/
* https://www.vagrantup.com/docs/virtualbox/common-issues.html
* https://www.vagrantup.com/docs/hyperv/
* https://www.vagrantup.com/docs/hyperv/limitations.html
* https://docs.microsoft.com/en-us/virtualization/hyper-v-on-windows/about/
* https://www.trustradius.com/compare-products/hyper-v-vs-oracle-vm-virtualbox
* https://www.vagrantup.com/docs/hyperv/usage.html
* https://www.vagrantup.com/docs/hyperv/configuration.html
* https://www.vagrantup.com/docs/synced-folders/smb.html
* https://app.vagrantup.com/hashicorp/boxes/bionic64
* https://techcommunity.microsoft.com/t5/virtualization/vagrant-and-hyper-v-tips-and-tricks/ba-p/382373
* https://github.com/hashicorp/vagrant/blob/master/website/source/intro/getting-started/boxes.html.md
* https://stackoverflow.com/questions/57796239/vagrant-not-adhering-to-static-ip-definition-in-vagrant-file
* https://www.vagrantup.com/docs/boxes/info.html
* https://superuser.com/questions/1354658/hyperv-static-ip-with-vagrant
* https://help.moonrivers.com/support/solutions/articles/1000226850-creating-vm-clones-in-microsoft-hyper-v




