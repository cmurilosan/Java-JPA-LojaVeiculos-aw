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
inicialização da aplicação. Configuramos para `drop-and-create`, ou seja, para recriar as tabelas sempre. Logicamente, isso nos ajuda a ter o banco
sempre limpinho para os nossos testes, mas em produção essa opção não deve ser usada.
- javax.persistence.sql-load-script-source : Executa um arquivo SQL para uma carga de dados na inicialização da factory do JPA, ou seja,
a cada vez que criarmos uma instância de ainda veremos aqui no livro.
- EntityManagerFactory como hibernate.dialect : dialeto a ser usado na construção de comandos `SQL hibernate.show_sql :` informa se os comandos SQL devem ser exibidos na console (importante para debug, mas deve ser desabilitado em ambiente de produção)
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

#### E - Gerando as tabelas do banco de dados
- Como ainda não temos a tabela representada pela classe dados, precisamos criá-la.
- O **JPA** pode fazer isso pra gente, graças à propriedade `javax.persistence.schema-generation.database.action` com valor `drop-and-create` , que incluímos no arquivo `persistence.xml`.

#### F - Definindo detalhes físicos de tabelas
- Vamos analisar as alterações que fizemos individualmente.
  - Especificamos o nome da tabela como `tab_veiculo` . Se não fizermos isso, o nome da tabela será considerado o mesmo nome da classe.

```
@Table(name = "tab_veiculo")
public class Veiculo {
```
  - Definimos o tamanho da coluna com 60 e com restrição `not null`.

```
@Column(length = 60, nullable = false)
private String fabricante;
```
  - Especificamos o nome da coluna como `ano_fabricacao` e com restrição `not null`. Se o nome da coluna não for especificado, por padrão, ela receberá o mesmo nome do atributo mapeado.

```
@Column(name = "ano_fabricacao", nullable = false)
private Integer anoFabricacao;
```

  - Atribuímos a precisão de 10 com escala de 2 casas na coluna de número decimal, especificando ainda que ela pode receber valores nulos.

```
@Column(precision = 10, scale = 2, nullable = true)
private BigDecimal valor;
```

#### G - Criando EntityManager
- Criamos um bloco estático para inicializar a fábrica de `Entity Manager`. Isso ocorrerá apenas uma vez, no carregamento da classe. Agora, sempre que precisarmos de uma `EntityManager` , podemos chamar:

```
EntityManager manager = JpaUtil.getEntityManager();
```
- Persistindo objetos
  - O código abaixo obtém um `EntityManager` , que é responsável por gerenciar o ciclo de vida das entidades.

```
EntityManager manager = JpaUtil.getEntityManager();
```
  - Agora iniciamos uma nova transação.

```
EntityTransaction tx = manager.getTransaction();
tx.begin();
```
  - Instanciamos um novo veículo e atribuímos alguns valores, chamando os métodos `setters`.

```
Veiculo veiculo = new Veiculo();
veiculo.setFabricante("Honda");
veiculo.setModelo("Civic");
veiculo.setAnoFabricacao(2020);
veiculo.setAnoModelo(2020);
veiculo.setValor(new BigDecimal(90500));
```
  - Executamos o método `persist` , passando a instância do veículo como parâmetro. Isso fará com que o JPA insira o objeto no banco de dados.
  - Não informamos o código do veículo, porque ele será obtido automaticamente através do `auto-increment` do MySQL.

```
manager.persist(veiculo);
```
  - Agora fazemos `commit` da transação, para efetivar a inserção do veículo no banco de dados.

```
tx.commit();
```
  - Finalmente, fechamos o `EntityManager` e o `EntityManagerFactory` .
  
```
manager.close();
JpaUtil.close();
```

#### H - Buscando objetos pelo identificado
- O método busca o objeto imediatamente no banco de dados, enquanto só executa o SQL quando o objeto for usado pela primeira vez, ou find getReference seja, quando invocamos um método getter da instância, desde que não seja o `getCodigo`.
- Note que o SQL foi executado apenas quando um getter foi invocado, e não na chamada de getReference .

#### I - Listando objetos
- A única novidade no código-fonte que usamos são as seguintes linhas:

```
Query query = manager.createQuery("select v from Veiculo v");
List<Veiculo> veiculos = query.getResultList();
```
- Veja que criamos uma **query** com a **JPQL** e atribuímos na variável **query** . Depois executamos o método `getResultList` e obtivemos uma lista de veículos.
- As IDEs, como o *IntelliJ IDEA* ou *Eclipse*, podem mostrar um alerta de **Type safety** em `query.getResultList()` . Por enquanto você pode ignorar isso, porque em breve você vai aprender sobre **TypedQuery** e isso será resolvido.

#### J - Atualizando objetos
- O código acima executa o comando `select` no banco de dados para buscar o veículo de **código 1** , imprime o valor atual do veículo, atribui um novo valor **(soma 500,00 reais)** e imprime um novo valor.
- Veja que não precisamos chamar nenhum método para a atualização no banco de dados. A alteração foi identificada automaticamente e refletida no banco de dados, através do comando `SQL update`.

#### K - Excluindo objetos
- A exclusão de objetos é feita chamando o método `remove` de `EntityManager`, passando como parâmetro o objeto da entidade.


## 2 - Gerenciando estados

#### A - Estados e ciclo de vida
- Objetos transientes
  - Objetos transientes `(transient)` são instanciados usando o operador `new` .
  - Isso significa que eles ainda não estão associados com um registro na tabela do banco de dados e qualquer alteração em seus dados não afeta o estado no banco de dados.
- Objetos gerenciados
  -Objetos gerenciados `(managed)` são instâncias de entidades que possuem um identificador e representam um registro da tabela do banco de dados.
  - As instâncias gerenciadas podem ser objetos que foram persistidos através da chamada de um método do `EntityManager` , como por exemplo o `persist` .
  - Eles também podem ter se tornado gerenciados através de métodos de consulta do `EntityManager` , que buscam registros da base de dados e instanciam objetos diretamente no estado `managed`.
  - Objetos gerenciados estão sempre associados a um contexto de persistência, portanto, quaisquer alterações nesses objetos são sincronizadas com o banco de dados.
- Objetos removidos
  - Uma instância de uma entidade pode ser excluída através do método `remove` do `EntityManager`. 
  - Um objeto entra no estado removed quando ele é marcado para ser eliminado, mas é fisicamente excluído durante a sincronização com o banco de dados.
- Objetos desanexados
  - Um objeto sempre inicia no estado `transiente` e depois pode se tornar gerenciado. Quando o `EntityManager` é fechado, continua existindo uma instância do objeto, mas já no estado `detached`.
  - Esse estado existe para quando os objetos estão desconectados, não tendo mais sincronia com o banco de dados.