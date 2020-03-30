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

O código fonte para esta tarefa está localizado na pasta [ca2/Part1/gradle_basic_demo](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca2/Parte1/gradle_basic_demo/).

### 1.3 Adicionar o gradle basic demo ao repositório

Para iniciar o Ca2, part1, foi necessário criar a pasta Parte1 à pasta Ca2 já existente no repositório. De seguida, foi efectuado o
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

Chegado a este ponto, é necessário recurrer ao IDE e aceder ao ficheiro build.gradle para se poder configurar a execução do servidor.
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

Nesta tarefa basicamente está se a chamar o método main da classe ChatServerApp, para executar o servidor, e a fornecer o argumento 59001,
que é o porto configurado. De seguida, regressou-se à CLI e verificou-se se a task foi adicionada à lista de tasks, após o build:

````
gradle build
gradle tasks --all
````

Depois executou-se a tarefa para verificar se o servidor é realmente executado e se ocorre algum erro. Caso ocorra, procede-se à
sua correção.

````
gradle runServer
````

Se o servidor for executado como esperado, será observada a mensagem: "The chat server is running...". De seguida, abriu-se uma
nova linha de comandos e procedeu-se à execução da tarefa relativa ao cliente.

````
gradle runClient
````

Novamente, deve ser verificado se tudo corre como o planeado, tentando abrir mais linhas de comandos, simulando mais clientes e
trocando mensagens entre eles para verificar que não ocorrem erros. No final, correndo tudo conforme o previsto, fez-se commit e push das
alterações, a partir dos respectivos comandos Git.


### 1.6 Adicionar um teste unitário à aplicação e fazer update do gradle script

Para se realizar testes unitários numa aplicação cuja ferramenta de build é o gradle, é necessário recorrer novamente ao IDE e
atualizar o ficheiro build.gradle de forma a que seja possível excutar os testes. Para este efeito, foram adicionadas duas dependências 
ao ficheiro e uma tarefa de teste.

````
dependencies {
    ...
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.3.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.3.1'
}

test {
    useJUnitPlatform()
}
````

De seguida, foi criada a pasta de testes e adicionou-se o teste sugerido no guião do Ca2, part1, com os respectivos imports
resultantes das dependências adicionadas.

````
package basic_demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    
    @Test 
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
````

De seguida, correu-se o teste e verificou-se se estava a passar e não ocorriam erros de compilação. Finalmente, voltou-se à CLI e 
verificou-se se o build corre como previsto. Verificou-se ainda que a tarefa *test* existe e que esta é executada com sucesso.

````
gradle build
gradle tasks --all
gradle test
````

Correndo tudo sem a ocorrência de erros, fez-se commit e push das alterações, a partir dos respectivos comandos Git.


### 1.7 Adicionar uma nova tarefa de tipo cópia e que faça o backup dos ficheiros do source

Para realizar esta tarefa, criou-se uma pasta como o nome *backup* na pasta *root* do projeto gradle basic demo, que vai ser
o reservatório dos ficheiros dos quais vamos realizar a cópia. De seguida, é necessário recurrer novamente ao IDE e aceder ao ficheiro
build.gradle para se poder adicionar a nova tarefa. Para este efeito, foi acrescentado o seguinte bloco de código ao ficheiro:

````
task copySourceBackup(type: Copy) {
    from 'src'
    into 'backup'
}
````
Aqui o termo *from* especifica de onde vão ser copiados os ficheiros e o termo *into* para onde vão ser copiados. De sequida,
voltou-se novamente à CLI e após o build, verificou-se que a tarefa *copySourceBackup* existe e que esta é executada com sucesso.

````
gradle build
gradle tasks --all
gradle copySourceBackup
````

Após a execução da tarefa, confirmou-se, de facto, que a pasta backup criada inicialmente continha os ficheiros provenientes do 
source.


### 1.8 Adicionar uma nova tarefa de tipo zip e que construa um zipFile que contém os ficheiros do source 

Para esta tarefa recorre-se novamente ao IDE e acede-se ao ficheiro build.gradle para se poder adicionar a nova tarefa. Para este
efeito, foi acrescentado o seguinte bloco de código ao ficheiro:

````
task zipSource(type: Zip) {
    archiveFileName = "source.zip"
    destinationDirectory = file('zipSource')

    from 'src'
}
````

Aqui o termo *archiveFileName* vai ser o nome do ficheiro zip no final e o termo *destinationDirectory* é o nome da pasta onde
irá estar o zipFile no interior da pasta *root* do projeto gradle basic demo. Se este último termo não for definido, o zipFile 
vai ser construído dentro da pasta build/distributions/ e não na root do projeto.

De seguida executaram-se os mesmos comandos na CLI que já tinham sido realizados para a tarefa em 1.7.

````
gradle build
gradle tasks --all
gradle zipSource
````

Após a execução da tarefa, confirmou-se, de facto, que o zipFile foi construído no local esperado e que continha os ficheiros 
provenientes do source


### 1.9 Adicionar a tag ca2-part1

No final do Ca2, part1, marcou-se o master branch com a annotated tag Ca2-part1 e verificou-se que a tag tinha sido adicionada.
Para este efeito, executando-se:

````
$ git tag -a ca2-part1 -m "ca2-part1"
        
$ git push origin ca2-part1
        
$ git tag
  ca1
  ca2-part1
  v1.2.0
  v1.3.0
  v1.3.1
````


### Class Assignment 2, parte 2

O código fonte para esta tarefa está localizado na pasta [ca2/Part2/tut_basic_gradle](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca2/Parte2/tut_basic_gradle/).

###





## 2. Análise de uma alternativa



## 3. Implementação de uma alternativa



## Referencias

* https://gradle.org/
* https://guides.gradle.org/writing-gradle-tasks/
* https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.Test.html
* https://docs.gradle.org/current/userguide/java_testing.html#using_junit5
* https://www.baeldung.com/junit-5-gradle
* https://docs.gradle.org/current/userguide/working_with_files.html
* https://docs.gradle.org/current/dsl/org.gradle.api.tasks.Copy.html
* https://confluence.atlassian.com/get-started-with-bitbucket/git-and-mercurial-commands-860009656.html
* https://start.spring.io
* https://github.com/Siouan/frontend-gradle-plugin
* https://stackoverflow.com/questions/41532108/run-clean-task-before-every-build-automatically-in-gradle
* https://docs.gradle.org/current/userguide/tutorial_using_tasks.html




