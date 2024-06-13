package com.sanisamoj.models.pages

import kotlinx.html.*

fun HTML.HomePage() {
    head {
        title("Loteria Federal API")
        style {
            +"body { font-family: Arial, sans-serif; margin: 40px; background-color: #f4f4f4; }"
            +"h1 { color: #333; }"
            +"p { color: #666; }"
            +"a { color: #1e88e5; text-decoration: none; }"
            +"a:hover { text-decoration: underline; }"
            +"pre { background: #eee; padding: 10px; border-radius: 5px; }"
        }
    }
    body {
        h1 { +"Loteria Federal API" }
        p {
            +"Atualmente, os resultados estão armazenados em um banco de dados NoSQL, e podem ser consumidos na URL: "
            a(href = "https://loteriajogosapi.com/api") { +"https://loteriajogosapi.com/api" }
        }
        p {
            +"Lista de loterias disponíveis:"
            pre {
                +"""[
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
]"""
            }
        }
        p {
            +"Para retornar o resultado mais recente, use uma das seguintes URLs:"
            ul {
                li { +"https://loteriajogosapi.com/api/loteria/latest" }
                li { +"https://loteriajogosapi.com/api/loteria/ultimo" }
                li { +"https://loteriajogosapi.com/api/loteria/recente" }
            }
        }
        p {
            +"Exemplo de uma consulta realizada para o resultado mais recente da Mega-Sena:"
            pre {
                +"""https://loteriajogosapi.com/api/megasena/recente

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
}"""
            }
        }
        p {
            +"Para retornar um concurso específico, use a seguinte URL:"
            pre { +"https://loteriajogosapi.com/api/loteria/2500" }
        }
        p {
            +"Para visualizar as operações da API, acesse: "
            a(href = "https://documenter.getpostman.com/view/29175154/2s9YsKfXXW") { +"Documentação Postman" }
        }
    }
}
