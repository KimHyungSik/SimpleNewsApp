package com.example.newsapp.Retrofit
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.newsapp.App
import com.example.newsapp.Utils.API.API_KEY
import com.example.newsapp.Utils.Utility.TAG
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsRetrofitClient {
    private var retrofitClient : Retrofit? = null
    
    fun getClient(baseUrl : String): Retrofit?{

        val client = OkHttpClient.Builder()

        val baseParameterInterceptor : Interceptor = (object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {

                val originalRequest = chain.request()

                // 기본 파라미터 추가
                val addedUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()

                val finalRequest = originalRequest.newBuilder()
                    .url(addedUrl)
                    .method(originalRequest.method, originalRequest.body)
                    .build()

                val response = chain.proceed(finalRequest)
                if(response.code != 200){
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(App.instance, "${response.code} 에러입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                return response
            }
        })
        client.addInterceptor(baseParameterInterceptor)

        if(retrofitClient == null){
           retrofitClient = Retrofit.Builder()
               .baseUrl(baseUrl)
               .addConverterFactory(GsonConverterFactory.create())
               .client(client.build())
               .build()
        }
        return retrofitClient
    }
}