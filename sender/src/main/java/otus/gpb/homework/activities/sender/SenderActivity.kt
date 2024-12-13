package otus.gpb.homework.activities.sender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.receiver.R
import otus.gpb.homework.activities.receiver.databinding.ActivitySenderBinding


class SenderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySenderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createListeners()
    }

    private fun createListeners(){
        binding.buttonToGoogleMap.setOnClickListener {
            openGoogleMap()
        }

        binding.buttonSendEmail.setOnClickListener {
            sendEmail()
        }

        binding.buttonOpenReceiver.setOnClickListener {
            openReceiver()
        }
    }

    private fun tryStartActivity(intent: Intent){
        try{
            startActivity(intent)
        }
        catch (ex: Exception){
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun openGoogleMap() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            `package` = "com.google.android.apps.maps"
            data = Uri.parse("geo:0,0?q=restaurants")
        }
        tryStartActivity(intent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("android@otus.ru"))
            putExtra(Intent.EXTRA_SUBJECT, "Тестовое письмо")
            putExtra(Intent.EXTRA_TEXT, "Просто текст письма")
        }
        tryStartActivity(intent)
    }

    private fun openReceiver() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            addCategory(Intent.CATEGORY_DEFAULT)
            putExtra("Title", "Славные парни")
            putExtra("Year", "2016")
            putExtra("Description", "Что бывает, когда напарником брутального " +
                    "костолома становится субтильный лопух? Наемный охранник Джексон Хили и " +
                    "частный детектив Холланд Марч вынуждены работать в паре, чтобы распутать " +
                    "плевое дело о пропавшей девушке, которое оборачивается преступлением века. " +
                    "Смогут ли парни разгадать сложный ребус, если у каждого из них – свои, весьма " +
                    "индивидуальные методы.")
        }
        tryStartActivity(intent)
    }
}