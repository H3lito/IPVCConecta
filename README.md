# 📍 IPVCConecta

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?style=for-the-badge&logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-M3-green?style=for-the-badge&logo=android)
![Firebase](https://img.shields.io/badge/Firebase-Auth%20%7C%20Firestore-orange?style=for-the-badge&logo=firebase)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)

**IPVCConecta** é uma aplicação nativa Android desenvolvida para auxiliar a integração e mobilidade de novos alunos no Instituto Politécnico de Viana do Castelo. A app funciona como um guia de bolso interativo, permitindo localizar escolas, serviços e pontos de interesse.

---

## ✨ Funcionalidades Principais

* 🗺️ **Mapa Interativo:** Visualização de pontos de interesse no Google Maps.
* 💾 **Offline-First:** A app guarda os dados localmente (Room Database), permitindo consultar o mapa e favoritos mesmo **sem internet**.
* 🔍 **Pesquisa e Filtros:** Encontra locais por nome ou categoria (Escolas, Alimentação, Transportes).
* 👤 **Perfil de Utilizador:** Login, Registo e Upload de Foto de Perfil.
* ⭐ **Favoritos:** Guarda os teus locais preferidos para acesso rápido.

---

## 📱 Capturas de Ecrã

| Login | Registo |
|:---:|:---:|
| <img src="IPVCConecta/Login.jpeg" width="250"> | <img src="IPVCConecta/Register.jpeg" width="250"> |<img src="IPVCConecta/Map.jpeg" width="250"> |
| **Autenticação Segura** | **Google Maps Integrado** |

| Detalhes do Local | Perfil de Utilizador |
|:---:|:---:|
| <img src="IPVCConecta/Detalhes_marcador.jpeg" width="250"> |<img src="IPVCConecta/perfil.jpeg" width="250">|


| Explorar Categorias |
|:---:|
| <img src="IPVCConecta/Explorar.jpeg" width="250"> |<img src="IPVCConecta/Detalhes.jpeg" width="250">|


---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Kotlin
* **UI:** Jetpack Compose (Material Design 3)
* **Arquitetura:** MVVM (Model-View-ViewModel)
* **Base de Dados Local:** Room Database
* **Cloud & Backend:** Firebase (Firestore, Auth, Storage)
* **Mapas:** Google Maps SDK
* **Imagens:** Coil (Async Image Loading)
* **Navegação:** Navigation Compose

---

## ⚙️ Configuração do Projeto

> ⚠️ **Nota Importante:** Este projeto utiliza o Google Maps SDK. Por questões de segurança, a Chave de API não está incluída no repositório.

Para executar o projeto:
1.  Clone este repositório.
2.  Crie um ficheiro `apikey.properties` na raiz do projeto.
3.  Adicione a sua chave: `MAPS_API_KEY=AIzaSyA...`
4.  Compile no Android Studio.

---

### 👨‍💻 Autor

Desenvolvido por **Hélito Mendes** (Nº 32440)
Licenciatura em Engenharia de Redes e Sistemas de Computadores - IPVC.


