package com.example.androidnativesample

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.androidnativesample.databinding.FragmentFirstBinding
import java.io.File

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val extStoragePath = Environment.getExternalStorageDirectory()
//        Log.d("TAG", "onViewCreated: $extStoragePath")    //   onViewCreated: /storage/emulated/0
//        val checkFile = File("$extStoragePath/WXLog.log")
//        Log.d("TAG", "is existed: ${checkFile.exists()}")

//        val flutterWebFile = File(extStoragePath,"index.html")
//        Log.d("TAG", "pathFlutterWeb is existed: ${flutterWebFile.exists()}")   // true
//        Log.d("TAG", "flutterWebFile path: ${flutterWebFile.absolutePath}") // /storage/emulated/0/web/index.html
//        val pathFlutterWeb = "file:///${flutterWebFile.absolutePath}"
//
//        binding.webview.settings.allowFileAccess = true
//        binding.webview.settings.allowContentAccess = true
//        binding.webview.loadUrl(pathFlutterWeb)

        binding.btn.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    val outdir =    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    if (!outdir.exists()) {
                        outdir.mkdirs()
                    }
                    // eg: /storage/emulated/0/Download/
                    val path = File(outdir.absolutePath,"index.html").absoluteFile
                    Log.d("TAG", "is existed: ${path.exists()}")
                    this.binding.webview.loadUrl("file://$path")

                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {}
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        0x100)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}