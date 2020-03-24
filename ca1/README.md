# Relatório Class Assignment 1 - Controlo de versões com Git

O código fonte para esta tarefa está localizada na pasta [ca1/tut-basic](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca1/tut-basic/).

## 1. Análise, Design e Implementação

### 1.1 Preparação do Assignment

Para a realização da Class Assignment 1 (Ca1) foi sugerido utilizar o sistema de controlo de versões distribuída Git. 

Na realização desta tarefa foi usada a distribuição Linux Ubuntu 18.04 LTS on Windows e para poder cumprir todos os 
requisitos necessários para a implementação do Ca1 foi necessário:

  * acesso à Ubuntu Command Line;
  * ter instalado o Git command line package;
  * um IDE que suporte Git (neste caso, foi utilizado o IntelliJ);
  * acesso a um repositório remoto (neste caso, foi utilizado o Bitbucket);
  * acesso à pasta "Basic" utilizada no Class Assignment 0 proveniente do Tutorial React.js and Spring Data REST application.

### 1.2 Configurar o Git

O primeiro passo que deve ser feito após a instalação do Git é configurar o nome do utlizador e o seu endereço de email.
Este passo é muito importante porque todos os commit Git irão utilizar esta informação automaticamente aquando do commit.
Para este efeito foram utilizados os seguintes comandos:

```
$ git config --global user.name "Marta Ribeiro"
$ git config --global user.email 1191779@isep.ipp.pt
```

### 1.3 Inicializar um repositório de uma pasta existente

Para pudermos mais tarde publicar as versões estáveis das alterações que forem realizadas na aplicação, é necessário 
primeiro adicionarmos ao repositório escolhido os ficheiros sobre os quais queremos trabalhar. Para isso, através da
linha de comandos, acedeu-se à pasta que queriamos adicionar - working directory (nest caso a pasta do projecto devOps 
que contém a pasta "Basic" da aplicação) e de seguida realizaram-se os seguintes comandos:

```
$ git init
$ git add .
$ git commit -m "Initial commit"
$ git remote add origin https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git
$ git push -u origin --all 
```

Este conjuntos de comandos irá permitir inicializar a pasta para a qual queremos manter o controlo de versões - working 
directory, adicionar todos os ficheiros presentes à staging area e realizar o commit para o repositório Git. De seguida,
fez-se a ligação ao repositorio remoto para o qual queremos trabalhar e finalmente foi feito o push dos ficheiros para o mesmo.
A partir deste momento, o repositório remoto utlizado deverá conter toda a informação presente na pasta devOps.

### 1.4 Criar a tag inicial 

Para podermos ter o controlo das alterações que vamos introduzindo no projeto, devemos ir colocando tags no master branch
do projeto, no final de cada alteração relevante que é realizada. O Git suporta dois tipos de tag: lightweight and 
annotated. Uma lightweight tag é muito parecida a um branch que nunca muda, é apenas um apontador para um commit específico.
Por outro lado, uma annotated tag é guardado como um objeto completo na bse de dados do Git.

Seguindo os requisitos do Ca1, devemos marcar este branch como versão inicial através de uma annotated tag v1.2.0. Para 
isso, utilizamos os seguintes comandos:

```
$ git tag -a v1.2.0 -m "versão 1.2.0"
```

A opção -a indica ao Git que é um annotated tag e a opção -m especifica qual a mensagem associada, ficando esta armazenada
com o tag. Para transferir a tag para o repositório remoto utilizamos o comando:

```
$ git push origin v1.2.0
```

Para podermos consultar as tags e respectivas versões existentes só temos de executar:

````
$ git tag
  v1.2.0
````

### 1.5 Criar a nova funcionalidade email a partir da criação de um branch email-field

Para criar uma nova funcionalidade de email para a aplicação que estamos a trabalhar é necessário criarmos um novo branch
email-field para que possamos trabalhar na funcionalidade sem comprometer o branch principal (master) nem o trabalho realizado
até ao momento. Para isso foi realizado o seguinte comando:

````
$ git branch email-field
````

Quando estamos a trabalhar com branches é importante sabermos sempre em que branch estamos a trabalhar no momento. Para 
isso, o Git um contém um pointer especial chamado HEAD que aponta para o branch local no qual estamos a trabalhar isso.
Para podermos consultar esta informação apenas temos de executar o comando:

````
$ git branch
  email-field
* master
````

O asterisco (*) serve para indicar o HEAD branch. Concluimos então que quando criámos o novo branch email-field não fomos
automaticamente encaminhados para o novo branch criado. Para isso é necessário executar:

````
$ git checkout email-field

$ git branch
* email-field
  master
````

## 2. Analysis of an Alternative

*For this assignment is there an aterntiave tool for Git? Describe it and compare it ti Git*

Ao contrário das versões centralizadas, numa versão distribuída não existe uma cópia central no servidor para se armazenar todas as versões
dos ficheiros de um projeto. Em vez disso, é feita uma cópia completa do repositório local para que seja possível obter toda a
história do projeto sempre que necessário.

## 3. Implementation of the Alternative

*Present the implementation of this class assignment using the Git alternative*


## Referências
* https://git-scm.com/
* https://confluence.atlassian.com/get-started-with-bitbucket/types-of-version-control-856845192.html
* https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup
* https://ommune.com/kb/pushing-a-new-project-directory-to-bitbucket-repository/
* https://git-scm.com/book/en/v2/Git-Basics-Tagging




* https://bitbucket.org/tutorials/markdowndemo/src/master/

