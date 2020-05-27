# Relatório Class Assignment 5 - Pipelines CI/CD com Jenkins


## 1. Análise, Design e Implementação

### 1.1 Preparação do Assigment



### Class Assignment 5, parte 1

O código fonte para esta tarefa está localizado na pasta [ca5/Parte1](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca5/Parte1).

O objectivo do class assignment 5, parte 1, é utilizar o Jenkins para configurar um pipeline com o projeto Gradle Basic Demo realizado
no Ca2, parte 1.


### 1.2 Criar um job e configurar o pipeline para o Gradle Basic Demo



### 1.3 Criar o Jenkinsfile



### 1.4 Executar o build do Jenkinsfile



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



### 1.7 Criar o Jenkinsfile



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
--------------------------------- | ----------------------------------|--------------------------------------------
Instalação                        | Instalação e configuração fácil   | Não necessita instalação, utiliza o browser
Manutenção                        | Manutenção regular                | Não necessita manutenção
Integrações                       | Plugins open source               | Integrações pré-feitas e testadas
Pipelines                         | **Scripts** e UI                  | **UI** e scripts                         

**NOTA:** Na ultima linha da tabela, os elementos a negrito são as opções mais fiaveis e mais utilizadas.


## 3. Implementação da alternativa

### 3.1 Preparação do Assigment



### Class Assignment 5, parte 2

O código fonte para esta tarefa está localizado na pasta [ca5/Parte2/Alternativa_Buddy](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca5/Parte2/Alternativa_Buddy).

O objectivo do class assignment 5, parte 2, é utilizar o Jenkins para configurar um pipeline com o projeto Tutorial Spring Boot
Application, Gardle Basic Version, realizado no Ca3, parte 2.


### 3.2 Criar um job



### 1.8 Criar e executar o build do job



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
