package com.sanisamoj.models.generics

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class ResultsCaixa(
        @BsonId
        @Contextual
        @Transient
        val id_mongo: ObjectId = ObjectId(),
        val tipoJogo: String,
        val numero: Int,
        val acumulado: Boolean,
        val dataApuracao: String?,
        val dataProximoConcurso: String?,
        val dezenasSorteadasOrdemSorteio: List<String>,
        val exibirDetalhamentoPorCidade: Boolean,
        val id: String?,
        val indicadorConcursoEspecial: Int,
        val listaDezenas: List<String>,
        val trevosSorteados: List<String>?,
        val listaDezenasSegundoSorteio: List<String>?,
        val listaMunicipioUFGanhadores: List<WinnersLocations>?,
        val listaRateioPremio: List<PrizeApportionmentList>?,
        val listaResultadoEquipeEsportiva: List<String>?,
        val localSorteio: String,
        val nomeMunicipioUFSorteio: String,
        val nomeTimeCoracaoMesSorte: String?,
        val numeroConcursoAnterior: Int,
        val numeroConcursoFinal_0_5: Int,
        val numeroConcursoProximo: Int,
        val numeroJogo: Int?,
        val observacao: String?,
        val tipoPublicacao: Int,
        val ultimoConcurso: Boolean,
        val valorArrecadado: Float?,
        val valorAcumuladoConcurso_0_5: Float?,
        val valorAcumuladoConcursoEspecial: Float?,
        val valorAcumuladoProximoConcurso: Float?,
        val valorEstimadoProximoConcurso: Float?,
        val valorSaldoReservaGarantidora: Float?,
        val valorTotalPremioFaixaUm: Float?,
)
