package com.laudemir.loteria

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.laudemir.loteria.util.PersistirDados
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var jogo: TextView
    private lateinit var gerar: Button
    private lateinit var gerarMilhar: Button
    private lateinit var nr_total_cartela: EditText
    private lateinit var nr_sorteados: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Inicializar as views
        inicializarViews()
        // Inicializar os listeners
        inicilizarListeners()
        // Recuperar os dados
        recuperarDados()
    }

    // Função para inicializar as views
    private fun inicializarViews(){
        jogo = findViewById(R.id.textResult)
        gerar = findViewById(R.id.megaButtonID)
        gerarMilhar = findViewById(R.id.milharButtonID)
        nr_total_cartela = findViewById(R.id.edt_numero_por_cartela)
        nr_sorteados = findViewById(R.id.edt_numeros_sorteados)
    }

    // Função para inicializar os listeners
    private fun inicilizarListeners(){
        // Listener para o botão de gerar números
        gerar.setOnClickListener {
            gerarNumeros()
        }
        // Listener para o botão de gerar milhar
        gerarMilhar.setOnClickListener {
            gerarMilharUnica()
        }
    }

    // Função para gerar os números
    private fun gerarNumeros() {
        val nCartela = nr_total_cartela.text.toString().toIntOrNull()
        val nSorteio = nr_sorteados.text.toString().toIntOrNull()

        // Limpar erros anteriores
        nr_total_cartela.error = null
        nr_sorteados.error = null

        var houveErro = false

        if (nCartela == null) {
            nr_total_cartela.error = "Informe um número válido"
            houveErro = true
        } else if (nCartela > 100) {
            nr_total_cartela.error = "O número da cartela não pode ser maior que 100"
            houveErro = true
        }

        if (nSorteio == null) {
            nr_sorteados.error = "Informe um número válido"
            houveErro = true
        } else if (nSorteio > 25) {
            nr_sorteados.error = "O número de sorteios não pode ser maior que 25"
            houveErro = true
        }

        if (!houveErro && nCartela != null && nSorteio != null) {
            if (nSorteio > nCartela) {
                nr_sorteados.error = "O número de sorteios não pode ser maior que o número da cartela"
            } else {
                val numeros = gerarNumerosUnicos(nSorteio, nCartela)
                jogo.text = numeros.sorted().joinToString(" - ")
                gravarDados()
            }
        }
    }


    // Função para gerar um milhar único
    private fun gerarMilharUnica() {
        val mil = Random.nextInt(0, 10000)
        jogo.text = mil.toString().padStart(4, '0') // Garante 4 dígitos
    }

    // Função para gerar números únicos
    private fun gerarNumerosUnicos(qtd: Int, maxValor: Int): Set<Int> {
        val numeros = mutableSetOf<Int>()
        while (numeros.size < qtd) {
            numeros.add(Random.nextInt(1, maxValor + 1))
        }
        return numeros
    }

    // Função para gravar os dados
    private fun gravarDados() {
        val persistir = PersistirDados(this)
        persistir.gravarString("nr_cartela", nr_total_cartela.text.toString())
        persistir.gravarString("nr_sorteio", nr_sorteados.text.toString())
    }

    // Função para recuperar os dados
    private fun recuperarDados() {
        val recuperar = PersistirDados(this)

        nr_total_cartela.setText(recuperar.recuperarString("nr_cartela"))
        nr_sorteados.setText(recuperar.recuperarString("nr_sorteio"))
    }
}