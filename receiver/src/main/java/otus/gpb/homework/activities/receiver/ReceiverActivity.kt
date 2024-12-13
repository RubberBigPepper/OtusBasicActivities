package otus.gpb.homework.activities.receiver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.gpb.homework.activities.receiver.databinding.ActivityReceiverBinding

class ReceiverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiverBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent){
        if (intent.action != Intent.ACTION_SEND)
            return
        val title = if (intent.hasExtra("Title")) intent.getStringExtra("Title") else ""
        binding.titleTextView.text = title
        val year = if (intent.hasExtra("Year")) intent.getStringExtra("Year") else ""
        binding.yearTextView.text = year
        val description = if (intent.hasExtra("Description")) intent.getStringExtra("Description") else ""
        binding.descriptionTextView.text = description

        when (title) {
            "Славные парни" -> {
                binding.posterImageView.setImageDrawable(getDrawable(R.drawable.niceguys))
            }
            "Интерстеллар" -> {
                binding.posterImageView.setImageDrawable(getDrawable(R.drawable.interstellar))
            }
        }
    }
}
