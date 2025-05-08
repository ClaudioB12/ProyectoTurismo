package pe.edu.upeu.systurismojpc.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pe.edu.upeu.systurismojpc.data.remote.RestActividad
import pe.edu.upeu.systurismojpc.data.remote.RestCliente
import pe.edu.upeu.systurismojpc.data.remote.RestDestino
import pe.edu.upeu.systurismojpc.data.remote.RestUsuario
import pe.edu.upeu.systurismojpc.utils.TokenUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    var retrofit: Retrofit?=null
    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl()= TokenUtils.API_URL
    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl:String):
            Retrofit {


        val okHttpClient= OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
        if (retrofit==null){
            val gson = GsonBuilder()
                .setLenient()
                .create()

            retrofit= Retrofit.Builder()

                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl).build()
        }
        return retrofit!!
    }
    @Singleton
    @Provides
    fun restUsuario(retrofit: Retrofit): RestUsuario{
        return retrofit.create(RestUsuario::class.java)
    }

    @Singleton
    @Provides
    fun restCliente(retrofit: Retrofit): RestCliente {
        return retrofit.create(RestCliente::class.java)
    }

    @Singleton
    @Provides
    fun restDestino(retrofit: Retrofit): RestDestino {
        return retrofit.create(RestDestino::class.java)
    }

    @Provides
    @Singleton
    fun restActividad(retrofit: Retrofit): RestActividad {
        return retrofit.create(RestActividad::class.java)
    }

}