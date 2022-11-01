package pw.edu.watchin.mailing.extension

import org.thymeleaf.context.Context
import pw.edu.watchin.common.properties.ApplicationProperties

fun Context.setApplicationVariables(applicationProperties: ApplicationProperties) {
    setVariable("frontendUrl", applicationProperties.frontend.url)
    setVariable("logoUrl", applicationProperties.frontend.run { url + rasterLogoPath } )
}
