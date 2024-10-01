package com.example.ckeep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ckeep.databinding.ActivityEncryptionKeyBinding

class EncryptionKeyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEncryptionKeyBinding

    private var encryptionKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition{true}

        super.onCreate(savedInstanceState)

        binding = ActivityEncryptionKeyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9,
            binding.button0
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                if (encryptionKey.length < 4) {
                    encryptionKey += button.text
                    binding.encryptionKeyTextView.text = encryptionKey
                }
            }
        }

        binding.confirmButton.setOnClickListener {
            if (encryptionKey.length == 4) {
                // Переход к MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("ENCRYPTION_KEY", encryptionKey)
                startActivity(intent)
                finish()
            }
        }

        // Задержка скрытия Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            splashScreen.setKeepOnScreenCondition{false}
        }, 2000)
    }
}

