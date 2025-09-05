package hit.the.biblioteca.models

data class Libro(
    var id: String = "",
    var titolo: String = "",
    var autore: String = "",
    var categoria: String = "",
    var anno: Int = 0,
    var tipo: String = "libro", // libro, musica, film
    var disponibile: Boolean = true,
    var createdAt: Long = System.currentTimeMillis()
)