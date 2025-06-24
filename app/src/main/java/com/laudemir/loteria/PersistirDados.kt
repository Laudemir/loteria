package com.laudemir.loteria

import android.content.Context
import android.content.SharedPreferences

class PersistirDados (context: Context){

    /*
    val texto = "Vou salvar esse texto"
    val persistencia = Persistir(this)
    persistencia.gravarString("nome", texto)
    val textoRecuperado = persistencia.recuperarString("nome")

    ou

    PersistirDados(this).gravarString("nome", texto)
    var str1 = DataPersist(this).recuperarString("nome")
    if(str1 != ""{
        Toast.makeText(this, str1, Toast.LENGTH_SHORT).show()
    }
     */

        private val dados: SharedPreferences = context.getSharedPreferences("PERSIST", Context.MODE_PRIVATE)


        fun gravarString(key: String, str: String){
            dados.edit().putString(key, str).apply()
        }

        fun recuperarString(key: String): String{
            return dados.getString(key, "") ?: ""
        }
}