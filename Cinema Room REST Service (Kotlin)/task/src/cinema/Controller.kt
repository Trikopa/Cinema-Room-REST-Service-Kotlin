package cinema

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@RestController
class CinemaController {
    @get:GetMapping("/seats")
    val cinema = Cinema()
    private val purchasedTickets: MutableMap<UUID, Seat> = ConcurrentHashMap()
    @PostMapping("/purchase")
    fun purchaseSeat(@RequestBody seat: Seat): ResponseEntity<*> {
        if (seat.row < 1 || seat.row > cinema.rows || seat.column < 1 || seat.column > cinema.seatsInRow) {
            return ResponseEntity.badRequest().body(
                    ConcurrentHashMap(java.util.Map.of("error", "The number of a row or a column is out of bounds!")))
        } else if (!cinema.availableSeats.contains(seat)) {
            return ResponseEntity.badRequest().body(
                    ConcurrentHashMap(java.util.Map.of("error", "The ticket has been already purchased!"))
            )
        }

        // Generate token and add to purchasedTickets map
        val token = UUID.randomUUID()
        purchasedTickets[token] = seat


        // Remove seat from available seats in cinema
        cinema.deleteFromAvailableSeats(seat)

        // Create JSON response
        val response: MutableMap<String, Any> = ConcurrentHashMap()
        response["token"] = token.toString()
        response["ticket"] = seat
        return ResponseEntity.ok<Map<String, Any>>(response)
    }

    @PostMapping("/return")
    fun returnSeat(@RequestBody requestBody: Map<String?, Any?>): ResponseEntity<*> {
        val token = UUID.fromString(requestBody["token"] as String?)

        // Find purchased seat by token
        val seat = purchasedTickets[token]
                ?: return ResponseEntity.badRequest().body(
                        ConcurrentHashMap(java.util.Map.of("error", "Wrong token!")))

        // Mark seat as available again
        cinema.addToAvailableSeats(seat)

        // Remove token from purchasedTickets map
        purchasedTickets.remove(token)

        // Create JSON response
        val response: MutableMap<String, Any> = ConcurrentHashMap()
        response["returned_ticket"] = seat
        return ResponseEntity.ok<Map<String, Any>>(response)
    }
}