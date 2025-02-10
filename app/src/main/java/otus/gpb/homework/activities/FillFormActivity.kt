package otus.gpb.homework.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(ContractFillFormActivity.PROFILE, UserProfile::class.java)
        } else {
            intent.extras?.getParcelable<UserProfile>(ContractFillFormActivity.PROFILE)
        }?.let {
            binding.editTextName.setText(it.name)
            binding.editTextSurname.setText(it.surname)
            binding.editTextAge.setText(it.age)
        }

        binding.buttonApply.setOnClickListener { sendResult() }
    }

    private fun sendResult() {
        val name = binding.editTextName.text.toString()
        val surname = binding.editTextSurname.text.toString()
        val age = binding.editTextAge.text.toString()
        if (name.isEmpty() || surname.isEmpty() || age.isEmpty()) {
            setAllFieldsWarning()
            return
        }

        val intent = Intent()
        intent.putExtra(ContractFillFormActivity.NAME, name)
        intent.putExtra(ContractFillFormActivity.SURNAME, surname)
        intent.putExtra(ContractFillFormActivity.AGE, age)
        setResult(RESULT_OK, intent)

        finish()
    }

    private fun setAllFieldsWarning(){
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.error)
            .setMessage(R.string.profile_edit_error)
            .setPositiveButton(R.string.close) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}