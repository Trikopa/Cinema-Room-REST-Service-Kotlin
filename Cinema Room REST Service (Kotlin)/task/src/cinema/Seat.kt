package cinema

import com.fasterxml.jackson.annotation.JsonIgnore

data class Seat(val row: Int,
                val column: Int,
                var price: Int,
                @JsonIgnore var booked: Boolean = false,
                @JsonIgnore var token: String? = null
                ){


    init {
        price = if (row <= 4) 10 else 8
    }
    override fun toString(): String {
        return "{\"row\":$row,\"column\":$column}"
    }
}
