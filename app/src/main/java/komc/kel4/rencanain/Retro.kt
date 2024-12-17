package komc.kel4.rencanain

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retro {
    fun getRetroClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("http://192.168.11.91:8000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}
