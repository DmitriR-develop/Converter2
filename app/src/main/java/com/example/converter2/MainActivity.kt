package com.example.converter2

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.converter2.databinding.ActivityMainBinding
import com.example.converter2.presenter.MainPresenter
import com.example.converter2.view.MainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val RESULT = 2000

    var dialog: Dialog? = null
    val presenter by moxyPresenter {
        MainPresenter(
            Converter(this),
            AndroidSchedulers.mainThread()
        )
    }

    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.convertionButton.setOnClickListener { presenter.starMain() }
    }

    override fun startMain() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/jpg"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        val chooser = Intent.createChooser(intent, getString(R.string.choose))
        startActivityForResult(chooser, RESULT)
    }

    override fun showDialog() {
        dialog = AlertDialog.Builder(this).setTitle("Converting...")
            .setNegativeButton("Dismiss") { dialog, wich ->
                presenter.dismiss()
            }.create()
        dialog?.show()
    }

    override fun hideDialog() {
        dialog?.dismiss()
    }

    override fun showResult() {
        Toast.makeText(this, "Complete!", Toast.LENGTH_LONG).show()
    }

    override fun showError() {
        Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show()

    }

    override fun showInterrupted() {
        Toast.makeText(this, "Interrupted!", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT) {
            if (resultCode == RESULT_OK) {
                data?.data?.let {
                    presenter.convertionImage(it.toString())
                }

            }
        }

    }
}