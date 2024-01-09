package com.sanisamoj.external

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class ApiRequest {

    //Instancializa o cliente
    val client : OkHttpClient = createInsecureOkHttpClient()

    //Realiza um get em uma api externa
    suspend inline fun<reified T> get(apiUrl : String, clientHttp: OkHttpClient = client) : T? {
        //Constrói a url
        val request : Request = Request.Builder().url(apiUrl).build()

        try {
            //Retorna a resposta da requisição | USE - Melhora os métodos closeble
            clientHttp.newCall(request).execute().use { response ->

                // Verifica se a requisição foi bem-sucedida (código de resposta 200)
                if (!response.isSuccessful) {
                    // Trate o erro aqui, se necessário
                    return null
                }

                // Obtém o corpo da resposta como uma String
                val stringResult = response.body?.string()

                // Libere os recursos associados à resposta
                response.body?.close()

                //Inicializa o JsonObject
                val gson = Gson()

                //Converte o Json em um objeto kotlin
                val result : T = gson.fromJson(stringResult, T::class.java)

                // Retorna a String resultante
                return result
            }
        } catch (error : Throwable) {
            return null
        }
    }
}

//Cria uma instância do OkHttpClient com a desativação da proteção ssl
fun createInsecureOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .sslSocketFactory(
            UnSafeOkHttpClient.unsafeSSLSocketFactory,
            UnSafeOkHttpClient.unsafeTrustManager
        )
        .hostnameVerifier { _, _ -> true }
        .readTimeout(180, TimeUnit.SECONDS) // Defina o tempo limite de leitura aqui (exemplo: 30 segundos)
        .writeTimeout(180, TimeUnit.SECONDS) // Defina o tempo limite de escrita aqui, se necessário
        .connectTimeout(180, TimeUnit.SECONDS)
        .build()
}

//Desabilita a proteção ssl (Não recomendado)
object UnSafeOkHttpClient {
    val unsafeTrustManager: javax.net.ssl.X509TrustManager by lazy {
        object : javax.net.ssl.X509TrustManager {
            override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        }
    }

    val unsafeSSLSocketFactory: javax.net.ssl.SSLSocketFactory by lazy {
        try {
            val sslContext = javax.net.ssl.SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf(unsafeTrustManager), java.security.SecureRandom())
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException("Failed to create an insecure SSLSocketFactory", e)
        }
    }
}