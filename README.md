## API do jogos da Loteria da Caixa
API gratuita dos resultados dos jogos da [Loteria Caixa](https://loterias.caixa.gov.br/Paginas/default.aspx)

## Funcionalidades do Sistema

- Retornar resultados dos jogos da Lotérica Federal
- Atualizar os resultados do banco de dados.

## Tecnologias e Ferramentas Utilizadas

- Backend: **Ktor (Kotlin)**
- Banco de Dados:
    - **MongoDB** - Para armazenamento de dados sensíveis.

## Exemplos dos retornos
Atualmente os resultados estão armazenados em um banco de dados noSQL, e podem ser consumidos na url:

> https://loteriajogosapi.com/api
```array
[
  "maismilionaria",
  "megasena",
  "lotofacil",
  "quina",
  "lotomania",
  "timemania",
  "duplasena",
  "federal",
  "diadesorte",
  "supersete"
]
```
- Para retornar o resultado mais recente
>  https://loteriajogosapi.com/api/"loteria"/latest

> Você também pode substituir "latest" por "ultimo" ou "recente" 

- Um exemplo de uma consulta realizado para o resultado mais recente da megasena

> https://loteriajogosapi.com/api/megasena/recente

```json
{
  "tipoJogo": "megasena",
  "numero": 2672,
  "acumulado": false,
  "dataApuracao": "06/01/2024",
  "dataProximoConcurso": "09/01/2024",
  "dezenasSorteadasOrdemSorteio": [
    "40",
    "10",
    "13",
    "20",
    "56",
    "43"
  ],
  "exibirDetalhamentoPorCidade": true,
  "id": null,
  "indicadorConcursoEspecial": 1,
  "listaDezenas": [
    "10",
    "13",
    "20",
    "40",
    "43",
    "56"
  ],
  "trevosSorteados": null,
  "listaDezenasSegundoSorteio": null,
  "listaMunicipioUFGanhadores": [
    {
      "ganhadores": 1,
      "municipio": "RIO DE JANEIRO",
      "nomeFatansiaUL": "",
      "serie": "",
      "posicao": 1,
      "uf": "RJ"
    }
  ],
  "listaRateioPremio": [
    {
      "descricaoFaixa": "6 acertos",
      "faixa": 1,
      "numeroDeGanhadores": 1,
      "valorPremio": 6480137.0
    },
    {
      "descricaoFaixa": "5 acertos",
      "faixa": 2,
      "numeroDeGanhadores": 22,
      "valorPremio": 88421.09
    },
    {
      "descricaoFaixa": "4 acertos",
      "faixa": 3,
      "numeroDeGanhadores": 2413,
      "valorPremio": 1151.65
    }
  ],
  "listaResultadoEquipeEsportiva": null,
  "localSorteio": "ESPAÇO DA SORTE",
  "nomeMunicipioUFSorteio": "SÃO PAULO, SP",
  "nomeTimeCoracaoMesSorte": ""
}
```

> O campo "*trevosSorteados*" só terá algum valor quando a loteria pesquisada for +Miolionaria(maismilionaria).

> O campo "*nomeTimeCoracaoMesSorte*" só terá algum valor quando a loteria pesquisada for Timemania (timemania) ou Dia de Sorte (diadesorte).

- Para retornar um concurso específico
> https://loteriajogosapi.com/api/"loteria"/2500

## Para visualizar as operações da API

> https://documenter.getpostman.com/view/29175154/2s9YsKfXXW

![image](https://github.com/sanisamoj/apiLoteria/assets/69211869/f3889dee-3763-45eb-97f0-19d94f25adf6)

