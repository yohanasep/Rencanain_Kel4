package komc.kel4.rencanain

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
//    fun getRetroClientInstance(): Retrofit {
//        val gson = GsonBuilder().setLenient().create()
//        return Retrofit.Builder()
//            .baseUrl("http://192.168.125.165:8000/api/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//    }

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
            .baseUrl("http://192.168.244.165:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }
}
