package pw.edu.watchin.mailing.config

import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HtmlCompressorConfig {

    @Bean
    fun htmlCompressor(): HtmlCompressor {
        return HtmlCompressor()
    }
}