package cinema

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CinemaConfig {

    @Bean
    open fun cinema(): Cinema {
        return Cinema()
    }
}
