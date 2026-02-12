package com.example.ipvcconecta.ui.theme.components.Datas.repository

import android.app.Application
import com.example.ipvcconecta.ui.theme.components.Datas.AppDatabase
import com.example.ipvcconecta.ui.theme.components.Datas.LocalEntity
import com.example.ipvcconecta.ui.theme.components.locais.LocalDetalhe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class LocaisRepository(application: Application) {

    private val dbLocal = AppDatabase.getDatabase(application).favoritoDao()
    private val dbRemoto = FirebaseFirestore.getInstance().collection("locais")

    val locais: Flow<List<LocalDetalhe>> = dbLocal.getAllLocais().map { entities ->
        entities.map { it.toDetalhe() }
    }

    suspend fun syncLocais() {
        try {
            val snapshot = dbRemoto.get().await()

            // Mapeamento Manual
            val locaisRemotos = snapshot.documents.map { doc ->
                LocalEntity(
                    id = doc.id,
                    nome = doc.getString("nome") ?: "Sem Nome",
                    categoria = doc.getString("categoria") ?: "Outros",
                    descricao = doc.getString("descricao") ?: "",
                    morada = doc.getString("morada") ?: "",
                    horario = doc.getString("horario") ?: "",
                    latitude = doc.getDouble("latitude") ?: 0.0,
                    longitude = doc.getDouble("longitude") ?: 0.0
                )
            }

            if (locaisRemotos.isNotEmpty()) {
                dbLocal.insertAllLocais(locaisRemotos)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}