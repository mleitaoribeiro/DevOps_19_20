# Relatório Class Assignment 1 - Controlo de versões com Git

O código fonte para esta tarefa está localizado na pasta [ca1/tut-basic](https://bitbucket.org/martalribeiro/devops-19-20-a-1191779/src/master/ca1/tut-basic/).

## 1. Análise, Design e Implementação

### 1.1 Preparação do Assignment

Para a realização da Class Assignment 1 (Ca1) foi sugerido utilizar o sistema de controlo de versões distribuída Git. 

Na realização desta tarefa foi usada a distribuição Linux Ubuntu 18.04 LTS e para poder cumprir todos os requisitos 
necessários para a implementação do Ca1 foi necessário:

  * acesso à CLI do Ubuntu;
  * ter instalado o Git (neste caso, foi utilizado o gestor de pacotes apt);
  * um IDE que suporte Git (neste caso, foi utilizado o IntelliJ);
  * um repositório remoto (neste caso, foi utilizado o Bitbucket);
  * a pasta "Basic" utilizada no Class Assignment 0 proveniente do Tutorial React.js and Spring Data REST application.

### 1.2 Configurar o Git

O primeiro passo que deve ser feito após a instalação do Git é configurar o nome do utilizador e o seu endereço de email.
Este passo é fundamental porque esta informação é utilizada para identificar o autor do commit. Para este efeito foram 
utilizados os seguintes comandos:

```
$ git config --global user.name "Marta Ribeiro"
$ git config --global user.email 1191779@isep.ipp.pt
```

### 1.3 Inicializar um repositório de uma pasta existente

Antes de adicionar ficheiros ao repositório Git para permitir o controlo das suas versões e alterações, é necessário 
inicializar o repositório. Também é necessário configurar, se existente, o endereço do repositório remoto (neste caso, 
o endereço do repositório BitBucket). Para isso, na working directory, realizaram-se os seguintes comandos:

```
$ git init
$ git remote add origin https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git
```

Uma vez que o repositório foi inicializado numa pasta com ficheiros já existentes, esses ficheiros foram de seguida 
adicionados num commit inicial e adicionados ao repositório remoto.

```
$ git add .
$ git commit -m "Initial commit"
$ git push -u origin master
```


Este conjuntos de comandos irá permitir inicializar a pasta para a qual queremos manter o controlo de versões - working 
directory, adicionar todos os ficheiros presentes à staging area e realizar o commit para o repositório Git, usando a 
opção -m para poder de seguida introduzir a mensagem do commit. De seguida, fez-se a ligação ao repositorio remoto para
o qual queremos trabalhar e finalmente foi feito o push dos ficheiros para o mesmo, usando a opção -u para que o Git faça
o seguimento da ligação ente o repositorio local e remoto. A partir deste momento, o repositório remoto utlizado deverá 
conter toda a informação presente na pasta devOps.

Se por outro lado fosse pretendido clonar um repositório já existente e trabalhar a partir deste para o assignment, 
deveria ser executado o comando clone seguido do url do repositorio remoto:

```
$ git clone https://martalribeiro@bitbucket.org/martalribeiro/devops-19-20-a-1191779.git
```

Para que ficheiros não desejados fossem ignorados pelo Git e não fossem guardados no repositório, foi criado um ficheiro 
.gitignore através da página https://www.gitignore.io/ com as palavras chave Java, Maven, IntelliJ. Para que este ficheiro
fosse também guardado no repositório Git e no remoto, foram realizados os seguintes comandos: 

```
$ git add .gitignore
$ git commit -m "Added .gitignore file"
$ git push -u origin master
```

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

Após este passo devemos recorrer ao IDE para realizarmos todas as alterações que irão dar suporte à funcionalidade email.
De seguida deverá ser feito o commit e o push para o repositório remoto de estas todas as alterações no novo branch em 
que estamos a trabalhar. Para isso executou-se:

```
$ git add .
$ git commit -m "Added email-field attribute"
$ git push -u origin email-field
```

### 1.6 Validação e debugging da nova funcionalidade

Para cumprir este requisito foram adicionadas validações aos métodos responsáveis pela manipulação de todos os atributos da 
classe de forma a impossibilitar a introdução de valores nulos e vazios nesses campos. De seguida foram também adicionados 
testes unitários para testar a criação de Employees. Para isso, foi necessário primeiro actualizar as dependências do Maven 
no ficheiro pom.xml para que fosse utilizada a versão mais recente do Junit. Depois desta alteração, foi criada uma nova 
pasta de testes no source e criada a classe EmployeeTest. Nesta classe foram adicionados vários métodos de acordo 
com a metodologia do JUnit (protocolo Arrange / Act / Assert e fazendo uso do método equals). Desta forma foi possível 
realizar o debug dos métodos da classe Employee ao nível do servidor e corrigir possíveis erros.

Tal como foi mencionado para a criação do atributo email, no final de cada grupo de testes foi realizado o commit de todas
estas alterações através da utilização dos comandos supramencionados.

Para o debug a nível do cliente foi posta a correr a aplicação na Ubuntu command line com o seguinte comando:
 
 ```
 $ mvn spring-boot:run
 ```
 
Esta ficou disponível no endereço *localhost:8080*, o qual está acessível através do browser Google Chrome. Através da realização de
comandos *curl* a nível da Ubuntu command line e da utilização das ferramentas *Components* e *Profiler* da extensão 
"React Developer Tools", foi possível realizar o debug.

### 1.7 Merge do branch email-field ao master branch

Após todas as alterações terem sido validadas e testadas, teve de ser realizado o merge do branch criado com a nova 
funcionalidade ao branch master. Para isso executou-se:

````
$ git checkout master
$ git merge email-field
$ git push -u origin master
````

A partir deste momento, o master branch fica atualizado com a funcionalidade do branch email-field e o working directory
passa a ser novamente o master branch.

### 1.8 Eliminar um branch inativo

Após o merge do branch email-field com o master branch este fica inativo. Caso este não vá ser mais utilizado, é boa 
prática eliminar os branches inactivos. Para este efeito executa-se:

````
$ git branch -d email-field
````

Para efeitos de avaliação, os branches inativos foram mantidos.

### 1.9 Criar um novo tag após adição da nova funcionalide

Após serem finalizadas todas as alterações ao master branch e se tiver atingido a versão estável da aplicação, esta deve 
ser marcada com um novo tag através de uma annotated tag v1.3.0. Para isso, utilizamos os seguintes comandos:

```
$ git tag -a v1.3.0 -m "versão 1.3.0"
$ git push origin v1.3.0
```

### 1.10 Criar um branch para a correção de bugs a partir da criação de um novo branch fix-invalid-email

O objectivo da criação do branch fix-invalid-email é a correção de um bug: o servidor só deverá aceitar a criação de Employees
com um email válido, ou seja, tem de possuir o simbolo *@*. Para atingir este requisito devem ser repetidos os passos 1.5 a
1.9 deste relatório, de forma a suportar esta funcionalidade, mas o nome do branch passa a ser fix-invalid-email e a versão 
final estável é marcada com a annotated tag v1.3.1.

### 1.11 Final do Ca1

No final do Class Assignment 1 marcou-se o master branch com a annotated tag Ca1, executando-se:

```
$ git tag -a Ca1 -m "Ca1"
$ git push origin Ca1
```

## 2. Análise da alternativa


Ao contrário das versões centralizadas, numa versão distribuída não existe uma cópia central no servidor para se armazenar 
todas as versões dos ficheiros de um projeto. Em vez disso, é feita uma cópia completa do repositório local para que seja 
possível obter toda a história do projeto sempre que necessário.


## 3. Implementação da alternativa - Mercurial

### 3.1 Preparação do Assignment

Para a realização da Ca1 foi utilizado o sistema de controlo de versões distribuída Mercurial como alternativa ao Git. 
A preparação do assignment foi similar à feita para o Git, mas neste caso foi instalado o Mercurial com o gestor de pacotes
apt e o repositório remoto utlizado foi o HelixTeamHub.

### 1.2 Configurar o Mercurial

Para configurar o nome do utlizador e o seu endereço de email, tal como efectuado para o Git, foram utilizados os seguintes 
comandos no user directory:

```
$ touch .hgrc
$ nano .hgrc
```

E editado o ficheiro com a seguinte informação:

````
[ui]
# Name data to appear in commits
username = Marta Ribeiro <1191779@isep.ipp.pt>
````

Em adição à configuração dos dados do utilizador que vai executar os commits, quando trabalhamos em Mercurial deve-se 
também proceder à configuração do repositório remoto, neste caso o HelixTeamHub, para facilitar os pushes:

````
$ hg config --local
````

E editar o ficheiro com apenas a seguinte informação:

````
[paths]
default = https://1191779isepipppt@helixteamhub.cloud/isep/projects/devops-19-20-a-1191779/repositories/mercurial/devops-19-20-A-1191779-mercurial
````

### 1.3 Inicializar um repositório de uma pasta existente

A inicialização de um repositório em Mercurial é identica à do Git. Para isso, na working directory, realizaram-se os 
seguintes comandos:

```
$ hg init

$ hg add
$ hg commit -m "Initial commit"
$ hg push
```

Se por outro lado fosse pretendido clonar um repositório já existente:

```
$ hg clone https://1191779isepipppt@helixteamhub.cloud/isep/projects/devops-19-20-a-1191779/repositories/mercurial/devops-19-20-A-1191779-mercurial
```

Foi igualmente criado um ficheiro .hgignore tal como com o Git e de seguida:

```
$ hg add .gitignore
$ hg commit -m "Added .hgignore file"
$ hg push
```

### 1.4 Criar a tag inicial 

Para marcar o default branch como versão inicial através da tag v1.2.0, utilizou-se o seguinte comando:

```
$ hg tag -r 1 v1.2.0
$ hg push
```

Ao contrário do Git, o Mercurial utiliza o conceito de changeset, que corresponde a uma modificação local que é marcada com
um número à medida que vão sendo feitas alterações. Neste comando, a opção *-r 1* indica que vamos a marcar a changeset 1 com a
tag v1.2.0.

Para podermos consultar as tags e respectivas versões existentes só temos de executar:

````
$ hg tags
v1.2.0                             1:2b9bb47a1b98
````

### 1.5 Criar a nova funcionalidade email a partir da criação de um branch email-field

Esta tarefa foi realizada em Mercurial de forma idêntica à realizada em Git. No entanto, em Mercurial o branch principal 
é denominado default branch e existem dois conceitos associados à criação de novas funcionalidades: bookmarks e branches.
O bookmark equivale aos branches usados no Git porque é apenas uma referência para o commit que é automaticamente atualizado
sempre que este é feito. Já o branch é armazenado como um constituinte permanente de um commit, tendo de ser feito push deste
para associar o commit a esse branch.

Neste assignment foram utilizadas os bookmarks e os branches por uma questão de explorar todas as opções. No presente relatório
vai apenas ser exlicado o conceito de branch e os passos foram identicos aos seguidos para o Git.

Assim, para criar um novo branch email-field, foram realizados os seguintes comandos:

````
$ hg branch email-field
$ hg commit -m "email-field"
$ hg push --new-branch
````

Ao criar este branch, este passa a ser automaticamente o working directory, mas apenas fica visivel para consulta e marcado 
com o apontador HEAD (letras a verde), após o push do novo branch. Para podermos consultar todos os branches temos de 
executar o comando:

````
$ hg branches
default                       15:ad794a694d24 (inactive)
email-field (a verde)         14:64bba1c6e4b4

````

Após serem realizadas todas as alterações pretendidas no IDE para o branch email-field, executou-se:

```
$ hg add
$ hg commit -m "Added email-field attribute"
$ hg push
```

### 1.6 Validação e debugging da nova funcionalidade

As validações e o debugging da aplicação foram realizados de forma idêntica à realizada com o Git. No final de cada grupo 
de testes foi realizado o commit de todas estas alterações, através da utilização dos comandos supramencionados.

### 1.7 Merge do branch email-field ao default branch

Após todas as alterações terem sido validadas e testadas, teve de ser realizado o merge do branch criado com a nova 
funcionalidade ao branch defualt. Para isso executou-se:

````
$ hg up default
$ hg merge email-field
$ hg ci -m merge
$ hg push
````

Após esta execução, primeiro faz-se a passagem para o default branch, a seguir o merge com o branch email-field, depois
o commit do merge feito e finalmente o push. A partir deste momento, o default branch fica atualizado com a funcionalidade 
do branch email-field e o working directory passa a ser novamente o default branch.

### 1.8 Eliminar um branch inativo

Após o merge do branch email-field com o default branch, este fica inativo. Caso este não vá ser mais necessário, é boa 
prática eliminar os branches inactivos. Para este efeito, estando o Head no branch email-field, executa-se um commit que 
transmite a eliminação do branch e, de seguida, o mesmo é eliminado. Só depois é realizado o merge.

````
$ hg up email-field
$ hg ci -m "Close branch email-field" --close-branch 
$ hg up default
$ hg merge email-field
$ hg ci -m merge
$ hg push
````

Para efeitos de avaliação, os branches inativos devem ser mantidos. Neste caso, o branch email-field foi eliminado mas
o branch que vai ser criado no passo 1.10 é mantido. De qualquer forma, foram criados bookmarks para marcar os commits 
pertencentes a cada branch.

### 1.9 Criar um novo tag após adição da nova funcionalide

Para marcar o default branch com a versão estável desta funcionalidade, utilizou-se a tag v1.3.0 a partir do seguinte comando:

```
$ hg tag -r 16 v1.3.0
$ hg push
```

Para consultar todas as tags:

````
$ hg tags
v1.3.0                            16:bfe73f859e35
v1.2.0                             1:2b9bb47a1b98
````

### 1.10 Criar um branch para a correção de bugs a partir da criação de um novo branch fix-invalid-email

A criação do branch fix-invalid-email foi realizada exatamente da mesma forma e com o mesmo propósito da realizada com o
Git. No entanto, no final da realização da tarefa, este branch não foi eliminado, sendo o merge executado com os seguintes
comandos:

````
$ hg up default
$ hg merge fix-invalid-email
$ hg ci -m merge
$ hg push
````

A versão final estável foi marcada com a tag v1.3.1.

````
$ hg tags
v1.3.1                            20:1baea59b5c6d
v1.3.0                            16:bfe73f859e35
v1.2.0                             1:2b9bb47a1b98
````

### 1.11 Final do Ca1

No final do Class Assignment 1 marcou-se o default branch com tag Ca1, executando-se:

```
$ hg tag -r 22 Ca1
$ hg push
```


## Referências
* https://git-scm.com/
* https://confluence.atlassian.com/get-started-with-bitbucket/types-of-version-control-856845192.html
* https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup
* https://ommune.com/kb/pushing-a-new-project-directory-to-bitbucket-repository/
* https://git-scm.com/book/en/v2/Git-Basics-Getting-a-Git-Repository
* https://git-scm.com/book/en/v2/Git-Basics-Tagging
* por o link da comparação
* https://confluence.atlassian.com/get-started-with-bitbucket/git-and-mercurial-commands-860009656.html
* https://confluence.atlassian.com/get-started-with-bitbucket/install-and-set-up-mercurial-860009660.html
* https://www.mercurial-scm.org/guide
* https://www.mercurial-scm.org/wiki/TutorialClone
* https://swcarpentry.github.io/hg-novice/09-remote-repos/
* https://www.mercurial-scm.org/guide#tagging
* https://www.mercurial-scm.org/wiki/ChangeSet
* https://docs.rhodecode.com/RhodeCode-Enterprise/tutorials/branching-vs-bookmarking.html
* https://www.mercurial-scm.org/wiki/Branch
* http://www.secretgeek.net/branching
* https://stackoverflow.com/questions/2237222/how-to-correctly-close-a-feature-branch-in-mercurial
* https://bitbucket.org/tutorials/markdowndemo/src/master/

