package hit.the.biblioteca.models

data class Utente(
    var id: String = "",
    var nome: String = "",
    var email: String = "",
    var ruolo: String = "user" // user o admin
)