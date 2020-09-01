# Estudo de JPA - AW

## 01 - Primeiros passos com JPA

#### A - Preparando o projeto com as dependências
- A dependência **hibernate-core** adiciona o Hibernate no nosso projeto, que é a
  implementação JPA que nós vamos usar.
- Nós vamos usar o sistema de gerenciamento de banco de dados **MySQL
  Community**, por isso adicionamos a dependência do **Driver JDBC** do MySQL no
  pom.xml também.
#### Criando o Domain Model (Classe Veiculo)
- A **classe Veiculo** possui os seguintes atributos:
  - **codigo** : identificador único do veículo
  - **fabricante** : nome do fabricante do veículo
  - **modelo** : descrição do modelo do veículo
  - **anoFabricacao** : número do ano de fabricação do veículo
  - **anoModelo** : número do ano do modelo do veículo
  - **valor** : valor que está sendo pedido para venda do veículo
- O atributo identificador (chamado de codigo ) é referente à **chave primária**

#### B - Implementando o equals() e hashCode()
- Para que os objetos das entidades sejam **diferenciados uns de outros**.
- No banco de dados, as chaves primárias **diferenciam registros distintos**. Quando
  mapeamos uma entidade para uma tabela, devemos criar os métodos **equals()
  e hashCode()** , levando em consideração a forma em que os registros são
  diferenciados no banco de dados.

#### C - Mapeamento básico
- A anotação **@Entity** diz que a classe é uma entidade JPA, que representa uma
tabela do banco de dados.
- A anotação **@Id** é usada para declarar o identificador da entidade, ou seja,
  representa a chave primária na tabela do banco de dados.
- A anotação **@GeneratedValue** especifica que um valor será gerado
 automaticamente para este atributo.
 - A estratégia **IDENTITY** especifica que o valor será auto-incrementado pela própria
   coluna do banco de dados.
- A anotação **@Column** especifica que a propriedade da classe representa uma coluna
na tabela do banco de dados. Propositalmente, anotamos apenas uma propriedade com **@Column** , mas isso não
quer dizer que apenas a propriedade anotada está mapeada. Ao omitir a
anotação nas outras propriedades, o JPA faz o mapeamento automaticamente, o
que significa que todas as propriedades da classe estão mapeadas para colunas.

#### D - O arquivo persistence.xml
- javax.persistence.jdbc.url : descrição da URL de conexão com o banco de dados
- javax.persistence.jdbc.driver : nome completo da classe do driver JDBC
- javax.persistence.jdbc.user : nome do usuário do banco de dados
- javax.persistence.jdbc.password : senha do usuário do banco de dados
- javax.persistence.schema-generation.database.action : Gera automaticamente o schema (as tabelas e relacionamentos) na 
inicialização da aplicação. Configuramos para drop-and-create, ou seja, para recriar as tabelas sempre. Logicamente, isso nos ajuda a ter o banco
sempre limpinho para os nossos testes, mas em produção essa opção não deve ser usada.
- javax.persistence.sql-load-script-source : Executa um arquivo SQL para uma carga de dados na inicialização da factory do JPA, ou seja,
a cada vez que criarmos uma instância de ainda veremos aqui no livro.
- EntityManagerFactory como hibernate.dialect : dialeto a ser usado na construção de comandos SQL hibernate.show_sql : informa se os comandos SQL devem ser exibidos na
console (importante para debug, mas deve ser desabilitado em ambiente de produção)
- hibernate.format_sql : indica se os comandos SQL exibidos na console devem ser formatados (facilita a compreensão, mas pode gerar textos longos na saída)
- Criação dos dados iniciais para o BD

```
insert into Veiculo (codigo, fabricante, modelo, anoFabricacao, anoModelo, valor)
values (1, 'Fiat', 'Toro', 2020, 2020, 107000);
insert into Veiculo (codigo, fabricante, modelo, anoFabricacao, anoModelo, valor)
values (2, 'Ford', 'Fiesta', 2019, 2019, 42000);
insert into Veiculo (codigo, fabricante, modelo, anoFabricacao, anoModelo, valor)
values (3, 'VW', 'Gol', 2019, 2020, 35000);
```