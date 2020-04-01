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
$ gradle build
$ gradle tasks --all
````

Depois executou-se a tarefa para verificar se o servidor é realmente executado e se ocorre algum erro. Caso ocorra, procede-se à
sua correção.

````
$ gradle runServer
````

Se o servidor for executado como esperado, será observada a mensagem: "The chat server is running...". De seguida, abriu-se uma
nova linha de comandos e procedeu-se à execução da tarefa relativa ao cliente.

````
$ gradle runClient
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
$ gradle build
$ gradle tasks --all
$ gradle test
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
$ gradle build
$ gradle tasks --all
$ gradle copySourceBackup
````

Após a execução da tarefa, confirmou-se, de facto, que a pasta *backup* criada inicialmente continha os ficheiros provenientes do 
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
$ gradle build
$ gradle tasks --all
$ gradle zipSource
````

Após a execução da tarefa, confirmou-se, de facto, que o zipFile foi construído no local esperado e que continha os ficheiros 
provenientes do source


### 1.9 Adicionar a tag ca2-part1

No final do Ca2, part1, marcou-se o master branch com a annotated tag Ca2-part1 e verificou-se que a tag tinha sido adicionada.
Para este efeito, executou-se:

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
Para iniciar o Ca2, part2, foi necessário criar a pasta Parte2 à pasta Ca2 já existente no repositório. Esta parte do assignment
tem o objetivo de converter a versão Basic da aplicação Tutorial para Gradle (em vez de Maven).


### 1.10 Criação do branch tut-basic-gradle

Para realizar a parte 2 do CA2 foi sugerido a criação um novo branch tut-basic-gradle e o respetivo checkout. Para isso, foram 
realizados os seguintes comandos:

````
$ git branch tut-basic-gradle
$ git checkout tut-basic-gradle
````

De seguida, verificou-se que de facto se estava no branch correto:

````
$ git branch
  email-field
  fix-invalid-email
  master
* tut-basic-gradle
````

### 1.11 Criação de um novo projeto spring com Gradle

Para criar um novo projeto spring com gradle de raiz mas de uma forma mais automatizada, recorreu-se ao url https://start.spring.io
e gerar assim o projeto com as seguintes dependências: Rest Repositories, Thymeleaf, JPA e H2. No final, irá ser gerado um zipFile 
com o novo projeto.

### 1.12 Configurar o novo projeto spring com a versão Basic da aplicação Tutorial

O zipFile extraído no passo anterior foi então colocado nas pasta ca2/Part2 e de seguida fez-se commit e push no branch tut-basic-gradle
para o repositorio local e remoto, respectivamente, a partir dos respectivos comandos Git. Os comandos de commit e push serão repetidos
ao longo deste assignment sempre que forem realizadas alterações no ficheiro build.gradle. Neste momento, foi possível consultar as tarefas
gradle disponíveis no projeto, através de:

````
$ gradle tasks
````

De seguida, procedeu-se à eliminação da pasta source do projeto vazio acabado de criar e copiou-se a pasta source disponível na 
Ca1 que corresponde à a versão Basic modificada da aplicação Tutorial. Foram também copiados os ficheiros webpack.config.js and 
package.json disponíveis na Ca1. O próximo passo consistiu em eliminar o conteúdo da pasta *src/main/resources/static/built/* dado 
que estes ficheiros devem ser gerados atravás da ferramente webpack.config.js. 

Finalmente, procedeu-se à execução da aplicação através do comando:

````
$ gradle bootRun
````

É de salientar que o endereço onde a aplicação está disponível - http://localhost:8080, embora apresente o titulo ReactJS + 
Spring Data REST, a página apresentada está completamente em branco dado que o plugin responsável pelo frontend ainda
não foi adicionado.

### 1.13 Adição do plugin org.siouan.frontend

Para que seja possível configurar o frontend do projeto, é necessário adicionar o plugin org.siouan.frontend. Para isso acrescentou-se
o seguinte bloco de código à secção destinada aos plugins:

````
id "org.siouan.frontend" version "1.4.1"
````

De seguida, foi adicionado ao ficheiro build.gradle o seguinte bloco de cófigo para configurar o plugin adicionado anteriormente: 

````
frontend {
    nodeVersion = "12.13.1"
    assembleScript = "run webpack"
}
````

### 1.14 Atualização do package.json

Para a configuração da execução do webpack, foi também acrescentada uma segunda linha à secção scripts/objects do ficheiro package.json,
com a informação "webpack": "webpack". O bloco final scripts fica então com o seguinte formato:

````
"scripts": {
    "watch": "webpack --watch -d",
    "webpack": "webpack"
},
````

### 1.15 Build e execução da aplicação

Após a execução do build, todas as configurações adicionadas relativas ao frontend vão ser executadas e o código do frontend
vai ser gerado. Desta forma, é possivel fazer a execução da aplicação, da mesma forma que foi feito com o maven no Ca1, e aceder
ao endereço http://localhost onde a aplicação está disponível. Para isso, executou-se:

````
$ gradle build
$ gradle bootRun
````

### 1.16 Adicionar uma nova tarefa de tipo cópia que copie o jar gerado para a pasta "dist" localizada na pasta *root* do projeto

Para realizar esta tarefa, criou-se uma pasta como o nome *dist* na pasta *root* do tut-basic-gradle, que vai ser o reservatório do
jar gerado. De seguida, é necessário recurrer ao IDE e aceder ao ficheiro build.gradle para se poder adicionar a nova tarefa. Para este
efeito, foi acrescentado o seguinte bloco de código ao ficheiro:

````
task copyGeneratedJar(type: Copy) {
	from 'build/libs'
	into 'dist'
}
````

De sequida, acedeu-se à CLI e após o build, verificou-se que a tarefa *copyGeneratedJar* existe e que esta é executada com sucesso.

````
$ gradle build
$ gradle tasks --all
$ gradle copyGeneratedJar
````

Após a execução da tarefa, confirmou-se, de facto, que a pasta *dist* criada inicialmente continha o jar gerado.


### 1.17 Adicionar uma nova tarefa que elimine automaticamente os ficheiros gerados pelo webpack antes da tarefa *clean* 

Para esta tarefa, recurreu-se novamente ao IDE e acedeu-se ao ficheiro build.gradle para se poder adicionar a nova tarefa. Para este
efeito, foi acrescentado o seguinte bloco de código ao ficheiro:

````
task deleteWebpackFiles(type: Delete) {
	delete 'src/main/resources/static/built/'
}

clean.dependsOn deleteWebpackFiles
````

De sequida, acedeu-se à CLI e após o build, verificou-se que a tarefa *deleteWebpackFiles* existe e que esta é executada com sucesso.

````
$ gradle build
$ gradle tasks --all
$ gradle copyGeneratedJar
````

Finalmente fez-se a confirmação que quando se executa a tarefa *clean*, a tarefa *deleteWebpackFiles* é automaticamente executada:

````
$ gradle clean
````

Após a execução da tarefa *clean*, a confirmação é feita de duas formas: 1) é gerada a mensagem que foram accionadas duas tarefas
e que ambas tiveram sucesso; 2) confirmar que, para além dos ficheiros contidos no build, também foram elminados os ficheiros contidos
na pasta src/main/resources/static/built/.


### 1.18 Exploração das funcionalidades desenvolvidas e merge do branch tut-basic-gradle ao master branch

Após todas as funcionalidades terem sido validadas e testadas e após commit e push para o branch, realizou-se o merge do branch
tut-basic-gradle. Para isso, executou-se:

````
$ git branch
  email-field
  fix-invalid-email
  master
* tut-basic-gradle

$ git checkout master
$ git merge tut-basic-gradle
$ git push -u origin master
````

### 1.20 Adicionar a tag ca2-part2

No final do Ca2, part1, após redação do relatório no README.md, marcou-se o master branch com a annotated tag Ca2-part2 e 
verificou-se que a tag tinha sido adicionada. Para este efeito, executou-se:

````
$ git tag -a ca2-part2 -m "ca2-part2"
        
$ git push origin ca2-part2
        
$ git tag
  ca1
  ca2-part1
  ca2-part2
  v1.2.0
  v1.3.0
  v1.3.1
````


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




