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

Para iniciar o Ca2, part1, foi necessário criar a pasta Parte1 na pasta Ca2 já existente no repositório. De seguida, foi efectuado o
download da aplicação disponível em https://bitbucket.org/luisnogueira/gradle_basic_demo/ para a pasta Parte1, que consiste 
num projeto para um servidor básico de um chat room. Finalmente, acedendo à CLI do Ubuntu foi feito o commit e o push para o 
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

A porção final do comando, <server port>, foi substituída por um porto válido para a execução do server - *59001*, que é o 
definido no ficheiro build da aplicação. De seguida, é necessário abrir uma nova linha de comando para se poder executar
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

Nesta tarefa basicamente chama-se o método main da classe ChatServerApp, para executar o servidor, e está-se a fornecer o argumento 59001,
que é o porto configurado. De seguida, regressou-se à CLI e verificou-se se a tarefa foi adicionada à lista de tarefas, após o build:

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
nova linha de comando e procedeu-se à execução da tarefa relativa ao cliente.

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

Para realizar esta tarefa,é necessário recurrer novamente ao IDE e aceder ao ficheiro build.gradle para se poder adicionar a nova 
tarefa. Para este efeito, foi acrescentado o seguinte bloco de código ao ficheiro:

````
task copySourceBackup(type: Copy) {
    from 'src'
    into 'backup'
}
````

Aqui o termo *from* especifica de onde vão ser copiados os ficheiros e o termo *into* para onde vão ser copiados. Se a pasta especificada
no *into* não existir, irá ser criada automaticamente. De sequida, voltou-se novamente à CLI e após o build, verificou-se que a tarefa 
*copySourceBackup* existe e que esta é executada com sucesso.

````
$ gradle build
$ gradle tasks --all
$ gradle copySourceBackup
````

Após a execução da tarefa, confirmou-se, de facto, que a pasta *backup* foi criada e continha os ficheiros provenientes do 
source.


### 1.8 Adicionar uma nova tarefa de tipo zip que construa um zipFile que contém os ficheiros do source 

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
irá estar o zipFile, que será criado na pasta *root* do projeto gradle basic demo. Se este último termo não for definido, o zipFile 
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

Para iniciar o Ca2, part2, foi necessário criar a pasta Parte2 na pasta Ca2 já existente no repositório. Esta parte do assignment
tem o objetivo de converter o build da versão Basic da aplicação Tutorial de maven para gradle.


### 1.10 Criação do branch tut-basic-gradle

Para realizar a parte 2 do CA2, foi sugerida a criação um novo branch tut-basic-gradle e o respetivo checkout. Para isso, foram 
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
e gerou-se assim o projeto com as seguintes dependências: Rest Repositories, Thymeleaf, JPA e H2. No final, foi gerado um zipFile 
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
Ca1 que corresponde à versão Basic modificada da aplicação Tutorial. Foram também copiados os ficheiros *webpack.config.js* e
package.json disponíveis na Ca1. O próximo passo consistiu em eliminar o conteúdo da pasta *src/main/resources/static/built/* dado 
que estes ficheiros devem ser gerados automaticamente atravás da ferramente webpack.config.js. 

Finalmente, procedeu-se à execução da aplicação através do comando:

````
$ gradle bootRun
````

É de salientar que o endereço onde a aplicação está disponível - http://localhost:8080, embora apresente o titulo ReactJS + 
Spring Data REST, possui uma página completamente em branco dado que o plugin responsável pelo frontend ainda não foi adicionado.

### 1.13 Adição do plugin org.siouan.frontend

Para que seja possível configurar o frontend do projeto, é necessário adicionar o plugin org.siouan.frontend. Para isso acrescentou-se
o seguinte bloco de código à secção destinada aos plugins:

````
id "org.siouan.frontend" version "1.4.1"
````

De seguida, foi adicionado ao ficheiro build.gradle, o seguinte bloco de cófigo para configurar o plugin adicionado anteriormente: 

````
frontend {
    nodeVersion = "12.13.1"
    assembleScript = "run webpack"
}
````

### 1.14 Atualização do package.json

Para a configuração da execução do webpack, foi também acrescentada uma segunda linha à secção scripts/objects do ficheiro package.json,
com a informação "webpack": "webpack". O bloco final da seccção scripts fica então com o seguinte formato:

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

Para realizar esta tarefa, é necessário recurrer novamente ao IDE e aceder ao ficheiro build.gradle para se poder adicionar a nova tarefa. 
Para este efeito, foi acrescentado o seguinte bloco de código ao ficheiro:

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

Após a execução da tarefa, confirmou-se, de facto, que a pasta *dist* foi criada e contém o jar gerado.


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
$ gradle deleteWebpackFiles
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

No final do Ca2, part1, após redação do relatório no README.md e da implementação da alternativa, marcou-se o master branch 
com a annotated tag Ca2-part2 e verificou-se que a tag tinha sido adicionada. Para este efeito, executou-se:

````
$ git tag -a ca2-part2 -m "ca2-part2"
        
$ git push origin ca2-part2
        
$ git tag
  Ant-ca2-part1
  AntAltern
  ca1
  ca2-part1
  ca2-part2
  v1.2.0
  v1.3.0
  v1.3.1
````


## 2. Análise de uma alternativa

Como ferramenta de build alternativo ao gradle, foi escolhida a ferramenta apache ant. 

Ao contrário do gradle, que apareceu mais recentemente, em 2012, e que se tem tornado uma ferramenta mais popular nos últimos 
anos, o apache ant é uma ferramente de build mais antiga, qua apareceu por volta de 2000, e que foi o sucessor da ferramenta
usada até então, o Make, como ferramenta de build em Java. Ant é o diminutivo de “Another Neat Tool” e é simples o suficiente para 
que qualquer pessoa possa utilizar sem pré-requisitos específicos. 

O maior benefício que o ant oferece é ser muito flexível pois não impõe nenhum tipo de convenção ou estrutura nos projetos. Por 
outro lado, ao contrário do gradle e do maven, este benefício obriga a que os utlizadores que usem ant tenham de configurar todo
o ficheiro de build do zero, o que por vezes os torna muito extensos e difíceis de manter. Assim, como o apache ant não suporta
o formato de gestão automática de dependências, todas as dependências têm de ser geridas manualmente pelo utlizador através da
manipulação de ficheiros jar ou war. 

Alguns anos após o surgimento do apache ant, surgiu um sub-projeto baseada neste projeto, em 2007, chamado apache ivy. O apache
ivy trata-se de um gestor de dependências fortemente orientado para projetos em Java e tem como objectivo incorporar no apache 
ant as mesma vantagens de gestão de dependencias presentes no apache maven, para que utilizadores de ant não tivessem
que ser obrigados a migrar para maven em projetos de maior dimensão e com muitas dependências. Para projetos que utilizem Spring
Boot, é possivel utilizar o apache ant+ivy através do módulo spring boot antlib de forma a auxiliar o ant na criação de executáveis
jar.

Em relação ao tipo de linguagem, ao contrário do gradle, que usa uma *domain specific language* (DSL) baseada em Groovy, que é 
muito simples e prática, o ant utiliza ficheiros que são escritos em XML. Isto pode ser também uma desvantagem pois o XML 
é uma linguagem com mais regras e que gera muito mais linhas de código, levando a ficheiros build de tamanho considerável em projetos
grandes. Ou seja, mais uma vez a manutenção destes ficheiros vai ser muito mais dificil de ser realizada, embora o XML seja muitas
vezes visto como uma linguagem mais poderosa e com um estrutua mais organizada.

Um dos benefícios que o Ant também possui é que as tarefas são organizadas em *targets* e permite, desta forma, construir tarefas
personalizadas. Este facto tornou-se uma grande vantagem aquando do surgimento do maven (2004), em que as tarefas personalizadas são
muito mais dificieis de construir, e que levou a que muitos utilizadores se mantivessem fiéis ao uso do ant para pequenos projetos, 
mesmo não sendo possível a gestão automática de dependências. 

Podemos então concluir que o apache ant, embora com algumas vantagens, é mais difícil de trabalhar do que a ferramenta gradle,
especialmente em projetos de maior dimensão. O gradle é uma ferramenta que foi construída a partir de conceitos já existentes em 
ant e em maven e, portanto, concilia a vantagem da gestão de dependências, através de plugins, com a possibilidade de definir 
tarefas personalizadas que podem ser executadas quando o utilizador pretender. Para além disso, utiliza uma linguagem simples que
permite a contrução de ficheiros de build mais simples e de muito mais fácil manutenção. Desta forma, o gradle é visto como uma 
ferramenta de build muito útil e vantajosa e, embora o apache maven ainda seja a ferramenta mais utilizada, esta nova ferramenta
tem vindo a tornar-se mais popular ao longo dos ultimos anos, sendo adotado pela Google como a ferramenta de build por omissão para
o sistema operativo Android.


## 3. Implementação de uma alternativa - Ant

### 3.1 Preparação do Assignment

A preparação do assignment para Ant foi similar à feita para o gradle, mas neste caso foi instalado o apache ant com o gestor de pacotes 
apt. Neste ponto, marcou-se o master branch com a annotated tag AntAltern.

Para a implementação da alternativa foi sugerido que fosse seguido o guião proposto para a parte 2 do Ca2. Foi então feita uma
tentativa de implementação na versão Basic da aplicação Tutorial React.js and Spring Data REST, que utiliza SpringBoot, com a ferramenta
de build apache ant. Dado nesta aplicação ser necessária a gestão de múltiplas dependências, foi utilizado o sub-projeto apache ivy e o 
módulo Spring Boot AntLib de forma a agilizar estas dependências e a não ser ser necessário configurar todos os ficheiros de forma manual.

Após várias tentativas sem sucesso e devido à dificuldade em gerir múltiplas dependências em projetos ant, mesmo com o suporte do apache
ivy e do spring boot antlib, optou-se pela implementação da alternativa seguindo o guião proposto para a parte 1 do Ca2, que apenas possui 
4 ou 5 dependências para gerir e que demonstra de igual forma a capacidade de conseguir implentar uma ferramenta alternativa ao uso do gradle.


### Class Assignment 2, parte 1

O código fonte para esta tarefa está localizado na pasta [ca2/Alternativa_ant/Parte1](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca2/Alternativa_ant/Parte1/).

### 3.2 Criação do novo projeto ant e adição da pasta source do gradle basic demo

Para iniciar o Ca2, part1 com ant, foi necessário criar um novo projeto ant vazio com: 
* uma pasta lib que contém o jar *log4j-1.2.17*, para gerir as dependências relativas ao apache log4j;
* colocar na mesma pasta lib o jar basic_demo-0.1.0, proeviniente da pasta build/libs do gradle basic demo, para gerir as dependencias
relativas ao servidor do chat room;
* um ficheiro build.xml, que vai permitir fazer o build da aplicação;
* um bloco de código com a tag <path/> no ficheiro build.xml, onde está definida a localização dos ficheiros jar utilizados nas tarefas ant;
* 4 tarefas básicas no ficheiro build.xml: *clean*, *init*, *compile* e *jar* e a tarefa *main* que é executada por default e 
corre as 4 anteriores automaticamente no mesmo comando; isto é possível devido a terem sido colocadas dependências entre tarefas.

Para realizar o build da aplicação basta realizar o seguinte comando na pasta *root* do projeto:

````
$ ant
````

Quando o comando *ant* é corrido sem qualquer tipo de opção à frente, é corrida sempre a tarefa definida como default no ficheiro
build.xml, que neste caso é a tarefa *main*.

De seguida, adicionou-se a pasta *source* do projeto gradle basic demo ao novo projeto ant vazio, o qual foi colocado na pasta 
Alternativa_ant/Parte1 criada na pasta Ca2 já existente no repositório. Finalmentem, acedendo à CLI do Ubuntu, foi feito o commit
e o push para o repositório local e remoto, respectivamente.

```
$ git add .
$ git commit -m "added folder Ant as an alternative"
$ git push -u origin master
```

### 3.3 Adaptação das intruções disponíveis no ficheiro README

As intruções neste ficheiro foram exexutadas de forma idêntica ao build com gradle, mas com as devidas adaptações. Sendo o ponto de 
partida a pasta *root* do projeto ant, foi feita a execução do servidor do chat room através do seguinte comando:

````
$ java -cp lib/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001
````

De seguida, abriu-se uma nova linha de comando para se poder executar o cliente do servidor:

````
$ java -cp lib/basic_demo-0.1.0.jar basic_demo.ChatClientApp localhost 59001
````


### 3.4 Adicionar uma nova tarefa para executar o servidor

Esta tarefa foi realizada de forma idêntica ao build com gradle, mas com as devidas adaptações para xml. Acedendo ao ficheiro build.xml,
foi acrescentado o seguinte bloco de código ao ficheiro:

````
<target name="runServer" depends="compile">
    <java classname="basic_demo.ChatServerApp" fork="true" classpathref="classpath">
        <arg line = "59001"/>
    </java>
</target>
````

Nesta tarefa, para executar o servidor, acede-se à classe ChatServerApp e o argumento *59001* é fornecido através da tag <arg line/>.
O termo "depends="compile" faz que sempre que a tarefa seja executado, a tarefa *compile* é executada anteriomente. Depois executou-se a tarefa
para verificar se o servidor é realmente executado e se ocorre algum erro.

````
$ ant runServer
````

Se o servidor for executado como esperado, será observada a mensagem: "The chat server is running...". No final, fez-se commit
e push das alterações, a partir dos respectivos comandos Git.


### 3.5 Adicionar uma nova tarefa para executar o cliente do chat room

Como neste projeto novo com ant não existe ainda uma tarefa responsável pela execução por parte do cliente, foi necessário criar
uma tarefa com este propósito, através do seguinte bloco de código:

````
<target name="runClient" depends="compile">
    <java classname="basic_demo.ChatClientApp" fork="true" classpathref="classpath">
        <arg line = "localhost 59001"/>
    </java>
</target> 
````

Para além do argumento *59001*, é fornecido também o argumento *localhost* através da tag <arg line/>. De seguida, estando o 
servidor a correr, abriu-se uma nova linha de comando e procedeu-se à execução da tarefa relativa ao cliente.

````
$ ant runClient
````

No final, correndo tudo conforme o previsto e não se verificando nenhum erro, fez-se commit e push das alterações, a partir
dos respectivos comandos Git.


### 3.6 Adicionar um teste unitário à aplicação e fazer update do ant script

Para realizar esta tarefa, foram adicionadas dois jars à pasta *lib*, *junit-4.12* e *hamcrest-core-1.3*, para ser possível a 
gestão das dependências do junit. De seguida, adicionou-se novamente uma tag <path/> para definir a localização dos jars do 
junit no ficheiro build.xml, bem como a localização dos ficheiros compilados após o build da aplicação:

````
<path id="junit.classpath">
    <fileset dir="lib/" includes="*.jar"/>
    <pathelement location="build/classes"/>
    <pathelement location="build/test"/>
</path>
````

Para execução dos testes, foi necessário adicionar uma tarefa com a tag <javac/> para compilar o código de teste e uma tarefa
com a tag <junit/> para executar os testes realizados.

````
<target name="test-compile" depends="compile">
    <mkdir dir="build/test"/>
    <javac srcdir="src/test/" destdir="build/test" includeantruntime="true">
        <classpath refid="junit.classpath"/>
    </javac>
</target>

<target name="junit" depends="test-compile">
    <junit printsummary="on" fork="true">
        <classpath>
            <path refid="junit.classpath"/>
            <pathelement location="build/test/"/>
        </classpath>
        <formatter type="brief" usefile="false" />
        <batchtest>
            <fileset dir="src/test/java/" includes="**/*Test.java" />
        </batchtest>
    </junit>
</target>
````

De seguida, foi criada a pasta de testes e adicionou-se o teste sugerido no guião do Ca2, part1, com os respectivos imports
resultantes das dependências adicionadas.

````
package basic_demo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    
    @Test 
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
````

De seguida, correu-se o teste no IDE e verificou-se se estava a passar e não ocorriam erros de compilação. Finalmente, voltou-se
à CLI do Ubuntu para verificar que a tarefa *test* é executada com sucesso, sem falhas ou erros.

````
$ ant junit
````

Correndo tudo como previsto, fez-se commit e push das alterações, a partir dos respectivos comandos Git.


### 3.7 Adicionar uma nova tarefa de tipo cópia e que faça o backup dos ficheiros do source

Esta tarefa foi realizada de forma idêntica ao build com gradle. Para este efeito, foi acrescentado o seguinte bloco de código
com a tag <copy/> ao ficheiro build.xml:

````
<target name="copySourceBackup" depends="compile">
    <delete dir="backup/"/>
    <copy todir="backup/">
        <fileset dir="src/"/>
    </copy>
</target>
````

Aqui o termo *todir* especifica de onde vão ser copiados os ficheiros e o termo *dir* para onde vão ser copiados. Se a pasta especificada
no *todir* não existir, irá ser criada automaticamente. A tag <delete/> foi colocada antes da cópia ser realizada para que no início
de um backups, se existir, a pasta desatualizada seja eliminada. De sequida, voltou-se novamente à CLI e após o build, verificou-se que a tarefa 
*copySourceBackup* é executada com sucesso.

````
$ ant copySourceBackup
````

Após a execução da tarefa, confirmou-se, de facto, que a pasta *backup* foi criada e continha os ficheiros provenientes do 
source.


### 3.8 Adicionar uma nova tarefa de tipo zip que construa um zipFile que contém os ficheiros do source 

Esta tarefa foi realizada de forma idêntica ao build com gradle. Para este efeito, foi acrescentado o seguinte bloco de código
com a tag <zip/> ao ficheiro build.xml:

````
<target name="zipSource" depends="compile">
    <zip destfile="source.zip" basedir="src/" update="true"/>
</target>
````

Aqui o termo *destfile* vai ser o nome do ficheiro zip no final e o termo *basedir* é o nome da pasta do qual se vai realizar 
o arquivo. O termo "update="true"" significa que se o arquivo zip já existir aquando da execução da tarefa, este vai ser atualizado
caso a pasta de origem tiver sofrido alterações. De seguida executou-se:

````
$ ant zipSource
````

Após a execução da tarefa, confirmou-se, de facto, que o zipFile foi construído no local esperado e que continha os ficheiros 
provenientes do source


### 3.9 Adicionar a tag Ant-ca2-part1

No final do Ca2, part1 com ant, marcou-se o master branch com a annotated tag Ant-ca2-part1 e verificou-se que a tag tinha sido
adicionada. Para este efeito, executou-se:

````
$ git tag -a Ant-ca2-part1 -m "Ant-ca2-part1"
        
$ git push origin Ant-ca2-part1
        
$ git tag
  Ant-ca2-part1
  AntAltern
  Ca1
  ca2-part1
  v1.2.0
  v1.3.0
  v1.3.1
````



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
* https://www.baeldung.com/ant-maven-gradle
* https://medium.com/@kapil.sharma91812/few-points-on-java-build-tools-ant-vs-maven-vs-gradle-e149a43325b8
* https://medium.com/@Colin_But/ant-vs-maven-vs-gradle-801fde21af80
* https://ant.apache.org/ivy/features.html
* https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#build-tool-plugins-antlib
* https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/build-tool-plugins-antlib.html#spring-boot-ant-exejar
* https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/using-boot-build-systems.html#using-boot-ant
* http://ant.apache.org/
* https://dzone.com/tutorials/java/ant/ant-example/ant-example.html
* https://ant.apache.org/manual/tutorial-HelloWorldWithAnt.html
* https://ant.apache.org/manual/listeners.html
* https://stackoverflow.com/questions/3730880/use-ant-for-running-program-with-command-line-arguments
* https://github.com/junit-team/junit4/wiki/Getting-started-%E2%80%93-Ant
* https://ant.apache.org/manual/tutorial-HelloWorldWithAnt.html#resources
* https://ant.apache.org/manual/Tasks/copy.html
* https://www.javatpoint.com/apache-ant-copy-task
* https://www.javatpoint.com/apache-ant-zip-task