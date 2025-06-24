package com.laudemir.loteria

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var mega: TextView
    private lateinit var gerar: Button
    private lateinit var milhar: TextView
    private lateinit var gerarMilhar: Button
    private lateinit var nr_total_cartela: EditText
    private lateinit var nr_sorteados: EditText
    //private lateinit var n_cartela: Int
    //private lateinit var n_sorteio: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mega = findViewById(R.id.megaTextID)
        milhar = findViewById(R.id.megaTextID) // Usando o mesmo TextView, como no código original
        gerar = findViewById(R.id.megaButtonID)
        gerarMilhar = findViewById(R.id.milharButtonID)
        nr_total_cartela = findViewById(R.id.edt_numero_por_cartela)
        nr_sorteados = findViewById(R.id.edt_numeros_sorteados)

        recuperarDados()

        gerar.setOnClickListener {
            val n_cartela = nr_total_cartela.text.toString().toIntOrNull()
            val n_sorteio = nr_sorteados.text.toString().toIntOrNull()

            if (n_cartela != null && n_sorteio != null) {
                when {
                    n_cartela > 100 -> {
                        Toast.makeText(this, "O número da cartela não pode ser maior que 100", Toast.LENGTH_SHORT).show()
                    }
                    n_sorteio > 25 -> {
                        Toast.makeText(this, "O números de sorteio não pode ser maior que 25", Toast.LENGTH_SHORT).show()
                    }
                    n_sorteio > n_cartela -> {
                        Toast.makeText(this, "O número de sorteios não pode ser maior que o número da cartela", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        val numeros = gerarNumerosUnicos(n_sorteio, n_cartela)
                        mega.text = numeros.sorted().joinToString(" - ")
                        gravarDados()
                    }
                }
            } else {
                Toast.makeText(this, "Preencha os dois campos com números válidos", Toast.LENGTH_SHORT).show()
            }
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

    private fun gravarDados() {
        val persistir = PersistirDados(this)
        persistir.gravarString("nr_cartela", nr_total_cartela.text.toString())
        persistir.gravarString("nr_sorteio", nr_sorteados.text.toString())
    }

    private fun recuperarDados(){
        val recuperar = PersistirDados(this)

        nr_total_cartela.setText(recuperar.recuperarString("nr_cartela"))
        nr_sorteados.setText(recuperar.recuperarString("nr_sorteio"))
    }
}