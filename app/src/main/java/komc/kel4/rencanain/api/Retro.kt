package komc.kel4.rencanain.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetroClientInstance(token: String? = null): Retrofit {
        val clientBuilder = OkHttpClient.Builder()

        if (token != null) {
            val authInterceptor = Interceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                println("Request: ${request.url}, Headers: ${request.headers}, Body: ${request.body}")
                chain.proceed(request)
            }
            clientBuilder.addInterceptor(authInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl("http://192.168.8.51:8000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientBuilder.build())
            .build()
    }
}
