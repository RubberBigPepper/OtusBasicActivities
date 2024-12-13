package otus.gpb.homework.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import otus.gpb.homework.activities.databinding.ActivityFillFormBinding

class FillFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFillFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFillFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonApply.setOnClickListener { sendResult() }
    }

    private fun sendResult() {
        val name = binding.editTextName.text
        val surname = binding.editTextSurname.text
        val age = binding.editTextAge.text
        if (name.isEmpty() || surname.isEmpty() || age.isEmpty())
            return

        val intent = Intent()
        intent.putExtra(, name)
        intent.putExtra(, surname)
        intent.putExtra(, age)
        setResult(RESULT_OK, intent)
        finish()
    }
}