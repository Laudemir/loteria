package com.laudemir.loteria

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var mega: TextView
    private lateinit var gerar: Button
    private lateinit var milhar: TextView
    private lateinit var gerarMilhar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mega = findViewById(R.id.megaTextID)
        milhar = findViewById(R.id.megaTextID) // Usando o mesmo TextView, como no código original
        gerar = findViewById(R.id.megaButtonID)
        gerarMilhar = findViewById(R.id.milharButtonID)

        gerar.setOnClickListener {
            val numeros = gerarNumerosUnicos(15, 25)
            mega.text = numeros.sorted().joinToString(" - ")
        }

        gerarMilhar.setOnClickListener {
            val mil = Random.nextInt(0, 10000)
            milhar.text = mil.toString().padStart(4, '0') // Garante 4 dígitos
        }
    }

    private fun gerarNumerosUnicos(qtd: Int, maxValor: Int): Set<Int> {
        val numeros = mutableSetOf<Int>()
        while (numeros.size < qtd) {
            numeros.add(Random.nextInt(1, maxValor + 1))
        }
        return numeros
    }
}