package uz.pdp.provalyutakurslari.retrofit

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather.retrofit.ApiClient
import com.example.myweather.retrofit.ApiServis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.pdp.provalyutakurslari.models.Course_model

class NetworkServise : ViewModel() {

    var liveData = MutableLiveData<List<Course_model>>()


    fun getData(): LiveData<List<Course_model>> {
        ApiClient.getRetrofit().create(ApiServis::class.java).getData()
            .enqueue(object : Callback<List<Course_model>> {
                override fun onResponse(
                    call: Call<List<Course_model>>,
                    response: Response<List<Course_model>>
                ) {
                    if (response.isSuccessful) {
                        liveData.value = response.body()

                    }
                }

                override fun onFailure(call: Call<List<Course_model>>, t: Throwable) {
                    Log.d("Error_Retrofit", t.message.toString())
                }


            })
        return liveData
    }


}