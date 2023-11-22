package kr.ac.kumoh.ce.s20180405.s23w1202retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception

class SongViewModel : ViewModel() {
    private val SERVER_URL = "https://port-0-s23w10backend-5yc2g32mlomirj0m.sel5.cloudtype.app/"
    private val songApi: SongApi

    private val _songList = MutableLiveData<List<Song>>()
    val songList: LiveData<List<Song>>
        get() = _songList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        songApi = retrofit.create(SongApi::class.java)
        fetchSong()
    }

    private fun fetchSong() {
        viewModelScope.launch {
            try {
                val response = songApi.getSongs()
                _songList.value = response
            } catch (e: Exception) {
                Log.e("fetchSong()", e.toString())
            }
        }
    }
}