package pw.edu.watchin.cdn.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import pw.edu.watchin.cdn.codec.RemoteStreamingResourceMessageWriter

@Configuration
@EnableWebFlux
class WebFluxConfig : WebFluxConfigurer {

    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.customCodecs().register(RemoteStreamingResourceMessageWriter())
    }
}