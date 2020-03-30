# Relatório Class Assignment 2 - Gradle como ferramenta de build


## 1. Análise, Design e Implementação

### 1.1 Preparação do Assignment

Para a realização da Class Assignment 2 (Ca2) foi sugerido utilizar o Gradle como ferramenta de build. 

Na realização desta tarefa foi usada a distribuição Linux Ubuntu 18.04 LTS e para poder cumprir todos os requisitos necessários 
para a sua implementação foi necessário:

  * acesso à CLI do Ubuntu;
  * ter instalado o Gradle 6.6.3 (neste caso, foi utilizado o Software Development Kit Manager);
  * um IDE que suporte Gradle (neste caso, foi utilizado o IntelliJ);
  * um repositório remoto (neste caso, foi utilizado o Bitbucket);
  * a pasta "Basic" utilizada no Class Assignment 1 proveniente do Tutorial React.js and Spring Data REST application.

### 1.2 Gitignore com gradle

Tal como o maven, para que ficheiros não desejados sejam ignorados pelo Git e não sejam guardados no repositório, tem de existir um 
ficheiro .gitignore, criado através da página https://www.gitignore.io/ com as palavras-chave Java, Gradle e IntelliJ.


### Class Assignment 2, parte 1

O código fonte para esta tarefa está localizado na pasta [ca2/ca2.1/gradle_basic_demo](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca2/Parte%201/gradle_basic_demo/).

### 1.3 Adicionar o gradle basic demo ao repositório

Para iniciar o Ca2, foi necessário criar a pasta Parte1 à pasta Ca2 já existente no repositório. De seguida, foi efectuado o
download da aplicação disponível em https://bitbucket.org/luisnogueira/gradle_basic_demo/ para a pasta Parte1, que consiste 
num projeto para um servidor básico de um chat room. Finalmentem acedendo à CLI do Ubuntu foi feito o commit e o push para o 
repositório local e remoto, respectivamente.

```
$ git add .
$ git commit -m "added gradle basic demo to Ca2/Part1 folder"
$ git push -u origin master
```

### 1.4 Execução das intruções disponíveis no ficheiro README

Sendo o ponto de partida a pasta *root* do projeto gradle basic demo, foi feito o build do ficheiro .jar da aplicação através de:

````
$ ./gradlew build
````

De seguida, para executar o servidor do chat room, executou-se o seguinte comando

````
$ java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp <server port>
````

A porção final do comando, <server port>, foi substituída por um porto válido para a execução do server - *59001*, que é o que 
está definido no ficheiro build da aplicação. De seguida, é necessário abrir uma nova linha de comando para se poder executar
o cliente do servidor:

````
$ ./gradlew runClient
````

Esta tarefa assume também o que está configurado no ficheiro build da aplicação: o endereço de IP do servidor do chat é 
*localhost* e o seu porto é *59001*.


### 1.5 Adicionar uma nova tarefa para executar o servidor

Chegado a este ponto, é necessário recurrer ao IDE e aceder ao ficheiro build.gradle para podermos configurar a execução do servidor.
Para este efeito, foi acrescentado o seguinte bloco de código ao ficheiro:

````
task runServer(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a server that supports a chat on localhost:59001 "

    classpath = sourceSets.main.runtimeClasspath

    main = 'basic_demo.ChatServerApp'

    args '59001'
}
````

- correr o server na CLI

- gradle build

- verificar se a minha task foi adicionada 
gradle tasks --all

- correr o server pela task
gradle runServer

- verficar se há erros e corrigir

- correr o client

- verficar que correu tudo bem

- fazer add ., commit e push


### Class Assignment 2, parte 2




## 2. Análise de uma alternativa



## 3. Implementação de uma alternativa


