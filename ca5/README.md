# Relatório Class Assignment 5 - Pipelines CI/CD com Jenkins


## 1. Análise, Design e Implementação

### 1.1 Preparação do Assigment

Para a realização da Class Assignment 5 (Ca5) foi sugerido utilizar o Jenkins como ferramenta de *Continuous Integration/Continuous 
Integration* (CI/CD).

Na realização desta tarefa foi usada a distribuição Linux Ubuntu 18.04 LTS e para poder cumprir todos os requisitos necessários
para a sua implementação foi necessário:

* acesso à CLI do Ubuntu;
* ter instalado o Jenkins (neste caso, foi utilizado um ficheiro war para por o servidor a correr);
* um repositório remoto (neste caso, foi utilizado o Bitbucket);
* o Spring Boot Tutorial Basic Project em gradle utilizada no Class Assignment 3, parte 2 proveniente do Tutorial React.js and
Spring Data REST application.


### Class Assignment 5, parte 1

O código fonte para esta tarefa está localizado na pasta [ca5/Parte1](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca5/Parte1).

O objectivo do class assignment 5, parte 1, é utilizar o Jenkins para configurar um pipeline com o projeto Gradle Basic Demo realizado
no Ca2, parte 1.


### 1.2 Criar um job e configurar o pipeline para o Gradle Basic Demo

Após concluida a instalação do Jenkins, é necessário aceder ao Browser através do endereço "https://localhost:8080". Num primeiro passo
é realizada toda a configuração do Jenkins e é feita a decisão dos plugins a serem instalados. É recomendável selecionar o pacote de 
plugins standard para utilizadores com pouca experiência. De seguida, é necessário criar um *job*, selecionar a opção *pipeline* e realizar
a configuração do pipeline de forma a que se adequando ao projeto. 

Neste projeto foi dado o nome **devops19-20_gradle_demo** ao job e para configurar o projeto adequadamente foi necessário aceder
à secçção Pipeline. Antes de configurar esta secção, é primeiro necessário aceder à secção **Credentials** na página inicial do Jenkins e
adicionar uma nova credencial com o username e a password de acesso à conta do repositório remoto. É essencial definir um id para que este
possa ser mais tarde utilizado no script do Jenkinsfile.

Voltando à secção Pipeline, na configuração do *job*, foi escolhida a opção **Pipeline script from SCM** e de seguida escolher o sistema
de controlo de versões a utilizar. Neste caso, foi escolhido o Git e adicionado o repositório pessoal do Bitbucket com a credencial previamente
criada. Foi também escolhido o branch ao que se pertence aceder, neste caso o *master branch* e de seguida definida a path onde se encontra o 
Jenkinsfile a utilizar no build. Nesta pipeline, o path escolhido foi **ca5/Parte1/Jenkinsfile**. Finalmente, salvam-se todas as configurações e
o job fica assim preparado para realizar o build do projeto.


### 1.3 Criar o Jenkinsfile

Para podermos correr a pipeline, precisamos de um Jenkinsfile que é composto por várias stages. Para cumprir todos os requisitos necessários
da Ca5, parte 1, foram definidos 4 stages: Checkout, Assemble, Test e Archive.


#### 1.3.1 Checkout

Esta stage tem como objectivo fazer o checkout do conteúdo que se encontra no repositório pessoal. Para isso, é utilizada a credencial 
**mlrBitbucket* criada anteriomente com os dados de acesso ao repositório remoto. 

````
stage('Checkout') {
    steps {
        echo 'Checking out...'
        git credentialsId: 'mlrBitbucket', url:
            'https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git'
    }
}
````

#### 1.3.2 Assemble

Esta stage tem como objectivo compilar e produzir o ficheiro de arquivo com a aplicação, que neste caso se trata de um ficheiro war.
Numa primeira fase, é estabelecido o local onde se encontra o ficheiro a compilar e, de seguida, após conceder a permissão de execução
a este ficheiro, é feito o comando que permite gerar o arquivo. A task build do gradle não foi utilizada neste stage para que os testes 
não fossem também executados. Por isso, utilizou-se a task assemble do gradle.

````
stage('Assemble') {
    steps {
        dir('ca2/Parte1/gradle_basic_demo') {
            echo 'Building...'
            sh 'chmod +x gradlew'
            sh './gradlew assemble'
        }
    }
}
````

#### 1.3.3 Test

Esta stage tem como objectivo executar os testes unitários existentes no projecto e publicar no Jenkins os resultados destes
testes. Para isso, foi primeiro definida a pasta do projeto e, de seguida, após após conceder a permissão de execução do ficheiro
build do gradle, é executada a task test do gradle para que os testes sejam executados também. Finalmente, é criada a pasta onde os
testes vão ser armazenados e é utilizado o comando **junit** para a execução e armazenamentos do resultado dos testes no Jenkins.

````
stage('Test') {
    steps {
        dir('ca2/Parte1/gradle_basic_demo') {
            echo 'Building...'
            sh 'chmod +x gradlew'
            sh './gradlew test'
            sh 'touch build/test-results/test/*.xml'
            junit 'build/test-results/test/*.xml'
        }
    }
}
````

#### 1.3.4 Archive

Esta stage tem como objectivo arquivar no Jenkins os ficheiros gerados durante o Assemble, ou seja, o ficheiro war. Para isso, primeiro
estabeleceu-se a pasta onde se encontra o projeto e, de seguida, a partir do comando **archiveArtifacts**, definiu-se onde se encontra o
ficheiro war a guardar.

````
stage('Archive') {
    steps {
        dir('ca2/Parte1/gradle_basic_demo') {
            sh 'pwd'
            echo 'Archiving...'
            archiveArtifacts 'build/distributions/'
        }
    }
}
````

Após conclusão do Jenkinsfile, este foi colocado na pasta **ca5/Parte1** e foi feito o commit e push para o repositorio remoto.

### 1.4 Executar o build do Jenkinsfile

Após push do ficheiro Jenkinsfile no repositório remoto, é necessário voltar à página do job no Jenkins carregada anteriomente e 
selecionar a funcionalidade **Build Now** na barra de tarefas do lado esquerdo da página. Desta forma, o build foi iniciado e
acedeu-se à secção **Console Output** para poder verificar se ocorrem erros. 

Neste assignment, o build falhou uma primeira vez devido a um erro de sintaxe no ficheiro Jenkinsfile e uma segunda vez devido a
um erro no link de acesso ao repositório remoto. À terceira tentativa, o build correu com sucesso. Na secção **Build Artifacts**,
foi possivel verificar o armazenamento do arquivo gerado no Assemble e, na secção Test Result, é possivel verificar o resultado
dos testes unitários.

### 1.5 Adicionar a tag ca5, parte 1

No final do Ca5, parte 1, marcou-se o master branch com a annotated tag ca5-part1 e verificou-se que a tag tinha sido adicionada.
Para este efeito, executou-se:

````
$ git tag -a ca5-part1 -m "ca5-part1"

$ git push origin ca5-part1

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
ca5-part1
v1.2.0
v1.3.0
v1.3.1
````

### Class Assignment 5, parte 2

O código fonte para esta tarefa está localizado na pasta [ca5/Parte2](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca5/Parte2).

O objectivo do class assignment 5, parte 2, é utilizar o Jenkins para configurar um pipeline com o projeto Tutorial Spring Boot
Application, Gardle Basic Version, realizado no Ca3, parte 2.


### 1.6 Criar um job e configurar o pipeline para o Tutorial Spring Boot, Gardle Basic Version

A criação do job e a configuração do pipeline para o projeto Gradle Basic Version foi similar à feita para o projeto Gradle Basic Demo,
feito no ponto 1.2.

Neste caso, o nome dado ao job foi **devops19-20_tutorial_spring_app_basic_gradle** e na configuração do job, na secção Pipeline, foi definida
uma path diferente para o Jenkinsfile - **ca5/Parte2/Jenkinsfile**.

### 1.7 Criar o Jenkinsfile

Para podermos correr a pipeline, precisamos de novamente de um Jenkinsfile que é composto 6 stages: Checkout, Assemble, Test, Javadoc,
Archive e Docker Image.


#### 1.7.1 Checkout

Esta stage tem como objectivo fazer o checkout do conteúdo que se encontra no repositório pessoal. Para isso, é utilizada a credencial 
**mlrBitbucket* criada anteriomente com os dados de acesso ao repositório remoto. 

````
stage('Checkout') {
    steps {
        echo 'Checking out...'
        git credentialsId: 'mlrBitbucket', url:
            'https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git'
    }
}
````

#### 1.7.2 Assemble

Esta stage tem como objectivo compilar e produzir o ficheiro de arquivo com a aplicação, que neste caso se trata de um ficheiro war.
Numa primeira fase, é estabelecido o local onde se encontra o ficheiro a compilar e, de seguida, após conceder a permissão de execução
a este ficheiro, é feito o comando que permite gerar o arquivo. A task build do gradle não foi utilizada neste stage para que os testes 
não fossem também executados. Por isso, utilizou-se a task assemble do gradle.

````
stage('Assemble') {
    steps {
        dir('ca2/Parte1/gradle_basic_demo') {
            echo 'Building...'
            sh 'chmod +x gradlew'
            sh './gradlew assemble'
        }
    }
}
````

#### 1.7.3 Test

Esta stage tem como objectivo executar os testes unitários existentes no projecto e publicar no Jenkins os resultados destes
testes. Para isso, foi primeiro definida a pasta do projeto e, de seguida, após após conceder a permissão de execução do ficheiro
build do gradle, é executada a task test do gradle para que os testes sejam executados também. Finalmente, é criada a pasta onde os
testes vão ser armazenados e é utilizado o comando **junit** para a execução e armazenamentos do resultado dos testes no Jenkins.

````
stage('Test') {
    steps {
        dir('ca2/Parte1/gradle_basic_demo') {
            echo 'Building...'
            sh 'chmod +x gradlew'
            sh './gradlew test'
            sh 'touch build/test-results/test/*.xml'
            junit 'build/test-results/test/*.xml'
        }
    }
}
````

#### 1.7.4 Archive

Esta stage tem como objectivo arquivar no Jenkins os ficheiros gerados durante o Assemble, ou seja, o ficheiro war. Para isso, primeiro
estabeleceu-se a pasta onde se encontra o projeto e, de seguida, a partir do comando **archiveArtifacts**, definiu-se onde se encontra o
ficheiro war a guardar.

````
stage('Archive') {
    steps {
        dir('ca2/Parte1/gradle_basic_demo') {
            sh 'pwd'
            echo 'Archiving...'
            archiveArtifacts 'build/distributions/'
        }
    }
}
````

Após conclusão do Jenkinsfile, este foi colocado na pasta **ca5/Parte1** e foi feito o commit e push para o repositorio remoto.


### 1.8 Executar o build do Jenkinsfile

ativar o plugin

por o docker a correr no pc

docker pull martalribeiro/devops2019-2020:spring_app_tut


### 1.9 Adicionar a tag ca5, parte 2

No final do Ca5, parte 2, marcou-se o master branch com a annotated tag ca5-part2 e verificou-se que a tag tinha sido adicionada.
Para este efeito, executou-se:

````
$ git tag -a ca5-part2 -m "ca5-part2"

$ git push origin ca5-part2

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
ca5-part1
ca5-part2
v1.2.0
v1.3.0
v1.3.1
````



## 2. Análise da alternativa

Como ferramenta alternativa ao Jenkins, foi escolhido o Buddy.

O Jenkins trata-se de um servidor de integração contínua e devido a ser open source, está disponivel gratuitamente. É uma ferramenta
desenvolvida em Java e disponibiliza mais de 300 plugins de suporte para construir e testar virtualmente qualquer projeto. É extremamente
flexivel, pois permite o desenvolvimente de plugins pela comunidade open source, e é considerada a líder quando se fala de *Continuous
Integration/Continuous Delivery* (CI/CD). Embora possua uma UI para gerar o pipeline, esta não é muito fiável e, desta forma, os pipelines
são habitualmente gerados através de Jenkins DSL em Jenkinsfiles.

O Buddy é uma ferramenta não open source que gera pipelines através de um conjunto de integrações internas, Assim, não estando dependente 
de plugins, garante qualidade e minimiza os erros durante a construção de um pipeline. É uma ferramente que permite a contrução, teste e 
deploy de um projeto de forma muito rápida pois possui uma UI eficiente que auxilia a geração de pipelines. Pode também ser diretamente 
conectada a palataformas Git (GitHub, Bitbucket) para facilitar o processo de CI/CD e é bastante útil para o desenvolvimento de software
com a ferramenta Docker, que é uma das principais integrações existentes.

Enquanto o Buddy é classificado na categoria de *Continuous Deployment*, o Jenkins pode ser primariamente classificado na categoria
*Continuous Integration*.

Na tabela seguinte, podem ser verificadas as diferentes mais importantes entre Jenkins e Buddy:


Categoria                         | Jenkins                           | Buddy
--------------------------------- | ----------------------------------|---------------------------------------------
Instalação                        | Instalação e configuração fácil   | Não necessita instalação, utiliza o browser
Manutenção                        | Manutenção regular                | Não necessita manutenção
Integrações                       | Plugins open source               | Integrações pré-feitas e testadas
Pipelines                         | **Scripts** e UI                  | **UI** e scripts                         

**NOTA:** Na ultima linha da tabela, os elementos a negrito são as opções mais fiaveis e mais utilizadas.


## 3. Implementação da alternativa - Buddy

### 3.1 Preparação do Assigment

Na realização desta tarefa foi utilizado o sistema operativo Windows e para poder cumprir todos os requisitos necessários para a
sua implementação foi necessário:

* acesso à ferramenta Buddy através de um browser (neste caso, foi utilizado o Google Chrome);
* um repositório remoto (neste caso, foi utilizado o Bitbucket);
* o Spring Boot Tutorial Basic Project em gradle utilizada no Class Assignment 3, parte 2 proveniente do Tutorial React.js and
Spring Data REST application, guardado no interior do repositório remoto.


### Class Assignment 5, parte 2

O código fonte para esta tarefa está localizado na pasta [ca5/Alternativa_Buddy](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca5/Alternativa_Buddy).

O objectivo é repetir o class assignment 5, parte 2, usando a alternativa ao Jenkins, o Buddy para configurar um pipeline com o projeto
Tutorial Spring Boot Application, Gardle Basic Version, realizado no Ca3, parte 2.


### 3.2 Criar uma conta no Buddy e um job



### 3.3 Criar e executar o build do job



docker pull martalribeiro/devops2019-2020:spring_app_tut_buddy


## Referências:
* https://www.jenkins.io/
* https://www.jenkins.io/download/
* https://www.jenkins.io/doc/book/installing/
* https://www.jenkins.io/doc/book/pipeline/syntax/
* https://plugins.jenkins.io/junit/
* https://plugins.jenkins.io/htmlpublisher/
* https://www.jenkins.io/doc/book/pipeline/docker/
* https://hackernoon.com/buddy-vs-jenkins-mt4v32u2
* https://stackshare.io/stackups/buddy-vs-jenkins
* https://www.g2.com/compare/buddy-vs-jenkins
* https://www.capterra.com/p/157673/Buddy/reviews/
* https://buddy.works/docs
