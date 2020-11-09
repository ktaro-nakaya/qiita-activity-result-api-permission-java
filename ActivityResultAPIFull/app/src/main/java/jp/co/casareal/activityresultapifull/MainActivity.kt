package jp.co.casareal.activityresultapifull

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    private val multiPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultMap ->

            resultMap.values.forEach {
                if (!it) {
                    Toast.makeText(this, "パーミッションが取得できなかった", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
                Toast.makeText(this, "パーミッションがすべて取得できた", Toast.LENGTH_SHORT).show()
            }
        }

    fun clickBtnRequestMultiplePermissions(view: View) {

        val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_MEDIA_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

        multiPermissionLauncher.launch(permissions)
    }

    private val singlePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                Toast.makeText(this, "パーミッションが取得できなかった", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "パーミッションがすべて取得できた", Toast.LENGTH_SHORT).show()
            }
        }

    fun clickBtnRequestPermission(view: View) {
        singlePermissionLauncher.launch(android.Manifest.permission.READ_CALENDAR)
    }


    val createDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.CreateDocument()) { uri ->
            documentUri = uri
            contentResolver.openInputStream(uri)?.bufferedReader(Charset.defaultCharset()).use {
                it?.readText()?.let {

                    AlertDialog.Builder(this).apply {
                        setTitle("uriの内容")
                        setMessage("${uri.toString()}")
                        setPositiveButton(android.R.string.ok, null)

                    }.show()
                }

            }
        }

    lateinit var documentUri: Uri
    fun clickBtnCreateDocument(view: View) {

        createDocumentLauncher.launch("myDocument.txt")
    }

    val contentLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            AlertDialog.Builder(this).apply {
                setTitle("uriの内容")
                setMessage("${it.toString()}")
                setPositiveButton(android.R.string.ok, null)

            }.show()

        }
    }


    fun clickBtnGetContent(view: View) {
        contentLauncher.launch("text/plain")
    }

    private val multiGetContents =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            it?.let {
                AlertDialog.Builder(this).apply {
                    setTitle("uriの内容")
                    setMessage("${it.toString()}")
                    setPositiveButton(android.R.string.ok, null)

                }.show()
            }
        }

    fun clickBtnGetMultipleContents(view: View) {
        multiGetContents.launch("text/plain")
    }

    private val openDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            it?.let {
                AlertDialog.Builder(this).apply {
                    setTitle("uriの内容")
                    setMessage("${it.toString()}")
                    setPositiveButton(android.R.string.ok, null)

                }.show()
            }
        }

    fun clickBtnOpenDocument(view: View) {
        openDocumentLauncher.launch(arrayOf("text/*"))
    }

    private val openTreeDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            it?.let {
                AlertDialog.Builder(this).apply {
                    setTitle("uriの内容")
                    setMessage("${it.toString()}")
                    setPositiveButton(android.R.string.ok, null)

                }.show()
            }
        }

    fun clickBtnOpenDocumentTree(view: View) {

        try {
            openTreeDocumentLauncher.launch(documentUri)
        } catch (e: Exception) {

            AlertDialog.Builder(this).apply {
                setTitle("注意")
                setMessage("CreateDocumentを実施した上でもう一度、OpenDocumentTreeをクリックしてください。")
                setPositiveButton(android.R.string.ok, null)

            }.show()

        }
    }

    private val multiOpenDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) {
            it?.let {
                AlertDialog.Builder(this).apply {
                    setTitle("uriの内容")
                    setMessage("${it.toString()}")
                    setPositiveButton(android.R.string.ok, null)

                }.show()
            }
        }

    fun clickBtnOpenMultipleDocuments(view: View) {
        multiOpenDocumentLauncher.launch(arrayOf("text/*"))
    }

    private val pickContactsLauncher =
        registerForActivityResult(ActivityResultContracts.PickContact()) {
            it?.let {
                AlertDialog.Builder(this).apply {
                    setTitle("uriの内容")
                    setMessage("${it.toString()}")
                    setPositiveButton(android.R.string.ok, null)

                }.show()
            }
        }

    fun clickBtnPickContact(view: View) {
        pickContactsLauncher.launch(null)
    }

    private val startActivityForResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.getStringExtra("message")?.let {
                AlertDialog.Builder(this).apply {
                    setTitle("入力した内容")
                    setMessage("${it.toString()}")
                    setPositiveButton(android.R.string.ok, null)
                }.show()
            }

        }

    fun clickBtnStartActivityForResult(view: View) {

        startActivityForResultLauncher.launch(Intent(this, InputActivity::class.java))
    }

    private val startIntentSenderForResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            it.data?.toString()?.let {
                AlertDialog.Builder(this).apply {
                    setTitle("結果")
                    setMessage("${it.toString()}")
                    setPositiveButton(android.R.string.ok, null)
                }.show()

            }
        }

    fun clickBtnStartIntentSenderForResult(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))

        startActivityForResultLauncher.launch(browserIntent)
    }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {

            if (it) {
                Toast.makeText(this, "写真撮影成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "写真撮影失敗", Toast.LENGTH_SHORT).show()
            }
        }

    fun clickBtnTakePicture(view: View) {
        try {
            takePictureLauncher.launch(documentUri)
        } catch (e: Exception) {

            AlertDialog.Builder(this).apply {
                setTitle("注意")
                setMessage("CreateDocument、RequestMultiplePermissions、を実施した上でもう一度、OpenDocumentTreeをクリックしてください。")
                setPositiveButton(android.R.string.ok, null)

            }.show()
        }
    }

    private val tackPicturePreviewLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            it?.let {
                val imageView = ImageView(this).apply {
                    setImageBitmap(it)
                }
                AlertDialog.Builder(this).apply {
                    setTitle("結果")
                    setView(imageView)
                    setPositiveButton(android.R.string.ok, null)
                }.show()
            }
        }

    fun clickBtnTakePicturePreview(view: View) {

        tackPicturePreviewLauncher.launch(null)
    }

    private val takeVideoLauncher = registerForActivityResult(ActivityResultContracts.TakeVideo()) {
        it?.let {
            val imageView = ImageView(this).apply {
                setImageBitmap(it)
            }
            AlertDialog.Builder(this).apply {
                setTitle("結果")
                setView(imageView)
                setPositiveButton(android.R.string.ok, null)
            }.show()
        }
    }

    fun clickBtnTakeVideo(view: View) {

        try {
            takeVideoLauncher.launch(documentUri)
        } catch (e: Exception) {

            AlertDialog.Builder(this).apply {
                setTitle("注意")
                setMessage("CreateDocument、RequestMultiplePermissions、を実施した上でもう一度、OpenDocumentTreeをクリックしてください。")
                setPositiveButton(android.R.string.ok, null)

            }.show()
        }
    }
}
