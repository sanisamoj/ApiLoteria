package com.sanisamoj.external

import com.sanisamoj.models.interfaces.LoteriaApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object LoteriaApi {
    private val BASE_URL = "https://servicebus2.caixa.gov.br/portaldeloterias/api/"

    // Cria um TrustManager que não verifica nada (INSEGURA)
    private val insecureTrustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
    }

    // Crie um SSLContext com o TrustManager inseguro
    private val insecureSslContext = SSLContext.getInstance("SSL").apply {
        init(null, arrayOf<TrustManager>(insecureTrustManager), java.security.SecureRandom())
    }

    // Crie um OkHttpClient que aceite todas as conexões (INSEGURAS)
    private val insecureOkHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(insecureSslContext.socketFactory, insecureTrustManager)
        .hostnameVerifier { _, _ -> true } // Ignora a verificação do nome do host
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(insecureOkHttpClient) // Adicionando o cliente inseguro ao Retrofit
        .build()

    val retrofitService: LoteriaApiService by lazy {
        retrofit.create(LoteriaApiService::class.java)
    }
}