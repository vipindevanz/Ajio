package com.pns.ajio.ui.main.spotlight

import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.pns.ajio.R
import com.pns.ajio.data.models.SpotlightModel

class SpotlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var desc: TextView
    lateinit var descTexts: TextView
    lateinit var scrollView: ScrollView
    lateinit var videoView: VideoView
    lateinit var progressBar: ProgressBar

    fun setData(model: SpotlightModel) {

        itemView.apply {

            desc = itemView.findViewById(R.id.tv_desc)
            descTexts = itemView.findViewById(R.id.tv_desc_text)
            scrollView = itemView.findViewById(R.id.scroll)
            videoView = itemView.findViewById(R.id.videoView)
            progressBar = itemView.findViewById(R.id.progressBar)

            videoView.setVideoPath(model.videoUrl)

            val links: Array<String> = model.des.split("@".toRegex()).toTypedArray()
            val string = StringBuilder()

            links.iterator().forEach {
                string.append(it + "\n\n")
            }

            desc.text = string

            videoView.setOnPreparedListener {

                progressBar.visibility = View.INVISIBLE
                descTexts.visibility = View.VISIBLE

                it.start()

                val videoRatio: Float = it.videoWidth / it.videoHeight.toFloat()
                val screenRatio: Float = videoView.width / videoView.height.toFloat()

                val scale: Float = videoRatio / screenRatio

                if (scale >= 1F) {
                    videoView.scaleX = scale
                } else {
                    videoView.scaleY = 1F / scale
                }
            }

            videoView.setOnCompletionListener { it.start() }
        }
    }
}