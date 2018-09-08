package com.wrongwrong.retrofittest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

class MainActivity : AppCompatActivity() {
    interface IGetRepos{
        @GET("{id}/repos")
        fun getRepos(@Path("id") userID : String) : Call<List<Repo>>
    }

    private val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .baseUrl("https://api.github.com/users/")
            .build()
    private val service: IGetRepos = retrofit.create(IGetRepos::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = service.getRepos("k163377")
        call.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                try{
                    var arr: List<Repo>? = response!!.body()
                    Log.d("onResponse", arr!![0].full_name)
                }catch (e: IOException){
                    Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}
