# Lácio

> Última flor do Lácio, inculta e bela,  
> És, a um tempo, esplendor e sepultura:  
> Ouro nativo, que na ganga impura  
> A bruta mina entre os cascalhos vela...   
> (...)    
> Em que da voz materna ouvi: "meu filho!"  
> E em que Camões chorou, no exílio amargo,  
> O gênio sem ventura e o amor sem brilho!  
> (Olavo Bilac) 

# Arquitetura

### api
- **Java 1.8**
- **Spring Boot**
- **Maven**: controle de dependências
- **Jsoup**: parse e navegação de HTML
- **Groovy**: parse e navegação de JSON
- **Spock**: testes


### client
- **ReactJS**: renderização View e contrle de estado
- **create-react-app**: bootstrap da aplicação
- **axios**: requests XHR
- **npm**: controle de dependências
- **cytoscape**: renderização do Grafo de palavras

### deployment
**Heroku**: https://lacio.herokuapp.com

## Justificativas
Java, Spring boot e Maven foram escolhas seguras, pois são plataformas/ferramentas que já domino.  
Java 1.8+ já possui abordagem funcional (lambdas), com a qual tenho bastante simpatia, e isso
justificou não utilizar NodeJS, embora menor.   

Jsoup eu já utilizei em muitas soluções e é uma das bibliotecas mais bem documentadas e elegantes que conheço.  
Outra escolha segura.

Optei por não usar um banco de dados para deixar a solução mais simples e dado que alta disponibilidade não
era um requisito. Também não era requisito alto desempenho, então o ônus de ter maior latência para acessar um fonte externa não foi problema.   
De toda forma, usamos um cache em memória (Spring cache)  para garantir um mínimo de desempenho.  
Aqui, usamos duas fontes:
- dicionariocriativo.com.br: acesso, parse e navegação via JSoup
- Google Dictionary API: acesso via JSoup, navegação e parse via Groovy.


Groovy também é uma linguagem que tenho certo domínio e é excelente para fazer parse de JSON. Nenhuma biblioteca Java que conheço 
traz tamanha praticidade. Como a nacessidade de utilizá-la veio no meio do projeto, não a utilizei desde o início. Então quase
todo o código é escrito em Java e uma única classe em Groovy: a que faz o parse do JSON.

Spock é um framework Groovy para testes extremamente elegante. Fiz um teste básico apenas para mostrar que valorizo a prática.
O tempo não foi suficiente para testes mais completos.

Do lado do client, as escolhas foram mais óbvias.  
React e sua abordagem declarativa sempre me agradou. Uso bastante em projetos pessoais. Não cogitei usar outra biblioteca/framework,
mas pensei em usar somente Javascript nativo (BabelJS pelo menos), já que a view não era complexa.  
Abortei a ideia, pois imaginei que um dos focos do desafio era apresentar ferramentas que conheço. Mas vale frisar que se fosse 
um protótipo qualquer, teria partido pra solução mais simples e com menos bibliotecas.   
A esolha por create-react-app em vez de React puro foi porque já conheço a ferramenta de build e me pouparia
algum esforço configurando BabelJS + Webpack.  Mas também friso que é uma bazuca pra matar uma formiga.


cytoscape foi uma sugestão do enunciado. Cheguei a testar as outras, mas essa parecia atender bem.  
Como não tinha conhecimento em renderuzação de grafos, não gastei muito tempo pesquisando.  
No entanto, perdi algum tempo com a documentação falha da ferramenta. Além de algum esforço
para integrá-lo razoavelmente bem com o React (até existem bibliotecas que já fazem isso, mas 
encontrei bugs e não quis perder tempo investigando).
     


# Solução
Usamos duas fontes de dados: 
1) https://dicionariocriativo.com.br/
2) Google Dictionary

[1] foi usado para identificar palavras relacionadas a outra dentro de um domínio semântico.  
[2] foi usada para identificar o significado de uma palavra dentro de um domínio semântico.


## Desenvolvimento
A solução exibe um grafo com relações entre palavras, domínios conceituais e seus significados.   
Focamos somente em **substantivos**, pois a extrapolação para verbos, adjetivos e advérbios é trivial.   

Inicialmente, tínhamos como meta mostrar informações gramaticais no grafo: classe gramatical,
sinônimos, antônimos etc. Mas priorizamos mostrar significados e o tempo não foi suficiente para fazer tudo.  
No entanto, a api ficou pronta: `[GET] /api/resumo/{palavra}`.      
Os seguintes casos de uso foram implementados:

1) Buscar uma palavra na caixa de busca: adiciona uma palavra ao grafo
2) Clicar em um nó do tipo palavra: carrega mais domínios conceituais
3) Clicar em um nó do tipo domínio conceitual: carrega mais palavras
4) Clicar numa aresta: mostra os significados da palavra dentro do domínio conceitual (outro nó da aresta): 
os significados são ranqueados com um score (número em azul)

## Estratégias para definir significados
Ao clicar numa aresta, são exibidos os significados da palavra dentro do domínio conceitual.  
Cada significado é ordenado por ordem descrescente de relevância a partir de um score.

Algumas abordagens foram testadas para a implementação desse algortimo.  
A seguir, algumas tentativas.
Tomemos como exemplo a palavra **terra** dentro do domínio conceitual **astronomia**.

##### Pesquisar no google os termos "terra astronomia" ou variantes do tipo "o que significa terra astronomia"
A ideia aqui é usar o fato do Google ser excelente para relacionara palavras dentro de um contexto.
No entanto, a pesquisa volta muito ruído. Não voltam só definições, voltam tambem notícias aleatórias.

##### Pesquisar na Wikipedia, na página de desambiguação
A ideia era acessar https://pt.wikipedia.org/wiki/Terra_(desambigua%C3%A7%C3%A3o) e inferir pela lista qual dos itens
tem mais associação ao domínio **astronomia**.  
A estratégia não se mostrou acurada. Muitos termos não possuem páginas de desambiguação e não é tão simples inferir qual item da
página corresponde ao domínio desejado. No exemplo, não existe um termo Terra - astronomia por ex. 
Existe Terra — o terceiro planeta do sistema solar, mas fazer o match nesse item exigiria um código mais complexo.

##### Utilizar o Google Dictionary
Essa foi a abordagem adotada.   
A ideia é usar a api (que volta JSON)  do Google para recuperar significados de uma palavra.   
Essa é a api que o Google usa quando pesquisamos por "**define** terra" por ex.
No entanto, ela não contextualiza também. Não é possível buscar por "**define** terra astronomia".  
Então precisamos de um algortimo para atribuir um score a cada significado de acordo com o domínio conceitual. Segue abaixo.

### Algoritmo
Claro, não é uma tarefa simples relacionar palavras dentro de um contexto. Afinal, vocês 
são uma empresa com várias pessoas que fazem isso =).  
Depois de tentar soluções triviais (usar o Google como fonte por ex), desisti e implementei algo simples.  

Parti da seguinte premissa: o texto do significado provavelmente contém palavras que estão relacionadas à palavra desejada
dentro de um domínio conceitual.

Por ex: um dos significados para terra:

> **Astronomia** planeta do sistema solar, o terceiro quanto à proximidade do **Sol**, habitado pelo homem ☞ inicial maiúsc.

Pelo dicionariocriativo, dentro de Astronomia, temos relacionados a terra:
sol, astronomia, que estão na descrição acima.

A ideia então é comparar TODOS os domínios conceituais de terra (astronomia, região, fíisica etc), analisar
quais palavras dão match e atribuir um score. E então exibir os significados de maior score primeiro.   
Essa abordagem foi promissora para alguns termos, mas trouxe muitos falsos positivos.  
Precisaria de mais refinamento: podar alguns termos por ex (preposições, conjunções etc).

Uma abordagem mais complexa seria também analisar sinônimos. Por ex, globo é uma palavra relacionada a terra em astronomia.
E planeta é sinônimo de globo (segundo https://dicionariocriativo.com.br/sinonimos-e-antonimos/globo). Então poderíamos contabilizá-la também.
No entanto, sinônimos também precisam de um contexto. Teríamos que inferir que globo é sinônimo de planeta dentro do conceito astronomia.

Tentamos ainda mais uma técnica de inferência: similaridade.  
planeta não aperece como palavras relacionadas a terra, mas planetografia aparece. A ideia então seria usar um algoritmo que dissesse 
que planetografia tem chances de se referir a planeta e que poderíamos contabilizar também. Os algortimos mais simples não trouxeram bons resultados.
Testamos Algoritmo de Fuzzy e de Levenshtein: também pareciam promissores, mas precisaríamos de mais refinamento.

No fim, optamos por algo simples. Comparar apenas os subjects dos significados.   
Ao invocar a api do Google para terra, ela retorna dentre sesus significados:  

> ASTRONOMIA  
> planeta do sistema solar, o terceiro quanto à proximidade do Sol, habitado pelo homem ☞ inicial maiúsc.

"ASTRONOMIA" é o que ela denomina como "subject", que é muito próximo do que dicionariocriativo classifica como domínio conceitual.  
Em vez de comprara o texto todo, comparamos somente so subjects. Quando encontramos um match, é muito provável
que aquele seja o significado desejado. O problema é que é limitado. Muitos significados vêm sem subjects.   



 
 

   


 



 



  



 


