package cinema

import com.fasterxml.jackson.annotation.JsonProperty


data class Cinema(
    @JsonProperty("total_rows")val rows: Int = 9,
    @JsonProperty("total_columns")val seatsInRow: Int = 9,
    @JsonProperty("available_seats")val availableSeats:MutableList<Seat> = mutableListOf()) {

    init {
        createCinema()
    }

    private fun createCinema() {
        for (i in 1..rows) {
            for (j in 1..seatsInRow) {
                val price = if (rows <= 4) 8 else 10
                availableSeats.add(Seat(i, j, price))
            }
        }
    }
    fun deleteFromAvailableSeats(seat: Seat) {
        this.availableSeats.remove(seat)
    }

    fun addToAvailableSeats(seat: Seat) {
       availableSeats.add(seat)
    }


}