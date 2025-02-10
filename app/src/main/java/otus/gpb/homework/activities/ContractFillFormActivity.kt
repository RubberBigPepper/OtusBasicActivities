package otus.gpb.homework.activities

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class ContractFillFormActivity : ActivityResultContract<UserProfile, UserProfile?>() {

    override fun createIntent(context: Context, input: UserProfile): Intent {
        val intent = Intent(context, FillFormActivity::class.java)
        intent.putExtra(PROFILE, input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserProfile? {
        if (resultCode == AppCompatActivity.RESULT_CANCELED)
            return null

        intent?.extras?.let {
            val profile = UserProfile(
                name = it.getString(NAME, ""),
                surname = it.getString(SURNAME, ""),
                age = it.getString(AGE,"")
            )
            return@parseResult profile
        }
        return null
    }

    companion object{
        const val PROFILE = "profile"

        const val NAME = "name"
        const val SURNAME = "surname"
        const val AGE = "age"
    }
}