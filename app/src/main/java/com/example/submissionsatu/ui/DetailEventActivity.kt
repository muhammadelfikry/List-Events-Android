package com.example.submissionsatu.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submissionsatu.data.response.Event
import com.example.submissionsatu.data.response.EventDetailResponse
import com.example.submissionsatu.data.retrofit.ApiConfig
import com.example.submissionsatu.databinding.ActivityDetailEventBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding

    companion object {
        private const val TAG = "DetailEventActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getEventDetail()
    }

    private fun getEventDetail() {
        showLoading(true)
        val eventId = intent.getIntExtra("EVENT_ID", 0)
        val client = ApiConfig.getApiService().getDetailEvent(id = eventId)
        client.enqueue(object : Callback<EventDetailResponse>{
            override fun onResponse(
                call: Call<EventDetailResponse>,
                response: Response<EventDetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setEventDetail(responseBody.event)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setEventDetail(eventDetail: Event) {
        binding.tvNameEvent.text = eventDetail.name
        binding.tvOwner.text = eventDetail.ownerName
        binding.tvTime.text = eventDetail.beginTime
        binding.tvQuota.text = eventDetail.quota.toString()
//        binding.tvDesc.text = HtmlCompat.fromHtml(
//            eventDetail.description,
//            HtmlCompat.FROM_HTML_MODE_LEGACY
//        )
        Glide.with(binding.root.context)
            .load(eventDetail.mediaCover)
            .into(binding.ivImageCover)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}