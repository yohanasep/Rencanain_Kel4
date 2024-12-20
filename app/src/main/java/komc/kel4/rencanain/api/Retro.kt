package komc.kel4.rencanain.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    fun getRetroClientInstance(token: String? = null): Retrofit {
        val clientBuilder = OkHttpClient.Builder()

        if (token != null) {
            val authInterceptor = Interceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            clientBuilder.addInterceptor(authInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl("http://192.168.8.165:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }
}
