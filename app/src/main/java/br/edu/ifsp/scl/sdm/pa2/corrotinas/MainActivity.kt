package br.edu.ifsp.scl.sdm.pa2.corrotinas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.scl.sdm.pa2.corrotinas.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(activityMainBinding){
            lancarBt.setOnClickListener {
                val random = Random(System.currentTimeMillis())

                texto1Tv.text = ""
                texto2Tv.text = ""
                texto3Tv.text = ""

                //corrotina 1
                GlobalScope.launch (Dispatchers.IO) {
                    val texto1Deferred: Deferred<String> = async {
                        buscaTexto1(random.nextLong(3000))
                    }
                    val texto1 = texto1Deferred.await()
                    runOnUiThread {
                        texto1Tv.text = texto1
                    }
                }

                //corrotina 2
                GlobalScope.launch (Dispatchers.IO) {
                    val texto2 = buscaTexto2(random.nextLong(3000))
                    runOnUiThread {
                        texto2Tv.text = texto2
                    }
                }
                texto3Tv.text = "Acorda o bezerro e a vaquinha"
            }
        }


    }

    suspend fun buscaTexto1(tempo: Long): String{
        delay(tempo)
        return "Bom dia"
    }

    suspend fun buscaTexto2(tempo: Long): String{
        delay(tempo)
        return "O sal já nasceu lá na fazendinha!"
    }
}