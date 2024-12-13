package otus.gpb.homework.activities

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val permissionCameraRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                imageView.setImageResource(R.drawable.cat)
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this, Manifest.permission.CAMERA
                    )
                ) {
                    openRationaleDialog()
                } else {
                    openApplicationSettingsDialog()
                }
            }
        }

    private val pickPictureRequest =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                populateImage(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        imageView = findViewById(R.id.imageview_photo)
        imageView.setOnClickListener { openActionDialog() }

        findViewById<Toolbar>(R.id.toolbar).apply {
            inflateMenu(R.menu.menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.send_item -> {
                        openSenderApp()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    /**
     * Используйте этот метод чтобы отобразить картинку полученную из медиатеки в ImageView
     */
    private fun populateImage(uri: Uri) {
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        imageView.setImageBitmap(bitmap)
        imageView.tag = uri
    }

    private fun openSenderApp() {
        TODO("В качестве реализации метода отправьте неявный Intent чтобы поделиться профилем. В качестве extras передайте заполненные строки и картинку")
    }

    private fun openActionDialog(){
        MaterialAlertDialogBuilder(this)
            .setItems(R.array.Actions) { _, what ->
                when (what) {
                    0 -> {
                        requestCameraPermission()
                    }

                    1 -> {
                        pickPhotoFromGallery()
                    }
                }
            }
            .show()
    }

    private fun requestCameraPermission() {
        permissionCameraRequest.launch("camera")
    }

    private fun pickPhotoFromGallery(){
        pickPictureRequest.launch("image/*")
    }

    private fun openRationaleDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.title_rationale)
            .setMessage(R.string.message_rationale)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                requestCameraPermission()
                dialog.dismiss()
            }
            .setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun openApplicationSettingsDialog(){
        MaterialAlertDialogBuilder(this)
            .setPositiveButton(R.string.open_settings) { dialog, _ ->
                openApplicationSystemSettings()
                dialog.dismiss()
            }
            .show()
    }

    private fun openApplicationSystemSettings(){
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${this.packageName}")).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}