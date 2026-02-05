package com.example.ipvcconecta.ui.theme.components.locais
object LocaisData {
    fun carregarLocaisIniciais(): List<LocalDetalhe> {
        return listOf(
            LocalDetalhe(
                nome = "ESTG - IPVC",
                categoria = "Escolas",
                descricao = "Escola Superior de Tecnologia e Gestão. Campus principal de engenharia e gestão.",
                morada = "Av. do Atlântico, Viana do Castelo",
                horario = "08:00 - 23:00",
                latitude = 41.6935,
                longitude = -8.8326
            ),
            LocalDetalhe(
                nome = "ESE - IPVC",
                categoria = "Escolas",
                descricao = "Escola Superior de Educação. Formação de professores e educadores.",
                morada = "Av. Capitão Gaspar de Castro, Viana do Castelo",
                horario = "08:00 - 20:00",
                latitude = 41.7025,
                longitude = -8.8202
            ),
            LocalDetalhe(
                nome = "ESS - IPVC",
                categoria = "Escolas",
                descricao = "Escola Superior de Saúde. Cursos de enfermagem e tecnologias da saúde.",
                morada = "Rua D. Moisés Alves de Pinho, Viana do Castelo",
                horario = "08:00 - 20:00",
                latitude = 41.6974,
                longitude = -8.8367
            ),
            LocalDetalhe(
                nome = "Serviços Centrais (SAS)",
                categoria = "Serviços",
                descricao = "Serviços de Ação Social, Bolsas e Alojamento.",
                morada = "Rua Escola Industrial e Comercial de Nun'Álvares",
                horario = "09:00 - 16:00",
                latitude = 41.6950,
                longitude = -8.8300
            ),
            LocalDetalhe(
                nome = "Residência Centro Histórico",
                categoria = "Alojamento",
                descricao = "Residência de estudantes no coração da cidade.",
                morada = "Rua do Loureiro",
                horario = "24h",
                latitude = 41.6918,
                longitude = -8.8285
            ),
            LocalDetalhe(
                nome = "Cantina ESTG",
                categoria = "Alimentação",
                descricao = "Refeições sociais para estudantes e funcionários.",
                morada = "Campus da ESTG",
                horario = "12:00 - 14:30 | 19:00 - 21:00",
                latitude = 41.6938, // Ligeiramente ao lado do edifício principal
                longitude = -8.8320
            )
        )
    }
}