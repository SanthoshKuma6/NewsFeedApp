package com.task.newsfeedapp.base.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.math.BigDecimal
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.lang.reflect.Type


object Networking {
    const val HEADER_AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "

    fun create(baseUrl: String, cacheDir: File, cacheSize: Long): INetworkService {
        val gson = GsonBuilder()
            .registerTypeAdapter(BigDecimal::class.java, BigDecimalTypeAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                getUnsafeOkHttpClient(cacheDir)
            )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(INetworkService::class.java)

    }
    fun getUnsafeOkHttpClient(cacheDir: File): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String,
                        ) {
                            // method is empty
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String,
                        ) {
                            // method is empty
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.cache(Cache(cacheDir, 10 * 1024 * 1024))
            builder.followRedirects(true)
            builder.followSslRedirects(false)
            builder.addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ ->
                true
            }
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    class BigDecimalTypeAdapter : JsonDeserializer<BigDecimal> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: com.google.gson.JsonDeserializationContext?,
        ): BigDecimal {
            return try {
                json.asBigDecimal
            } catch (e: NumberFormatException) {
                BigDecimal.ZERO
            }
        }
    }

}
