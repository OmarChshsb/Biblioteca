package hit.the.biblioteca.models

data class Prestito(
    var id: String = "",
    var idLibro: String = "",
    var idUtente: String = "",
    var dataInizio: Long = 0L,
    var dataFine: Long = 0L,
    var stato: String = "attivo" // attivo, restituito
)