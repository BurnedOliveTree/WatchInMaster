package pw.edu.watchin.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import pw.edu.watchin.server.properties.AccountProperties;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AccountProperties accountProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
            .antMatchers(provideUnprotectedApiRoutes())
            .permitAll();

        http.authorizeRequests()
            .antMatchers("/api/**")
            .authenticated();

        http.exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint);

        http.formLogin()
            .loginProcessingUrl(accountProperties.getApi().getLoginPath())
            .failureHandler(authenticationFailureHandler)
            .successHandler(authenticationSuccessHandler)
            .permitAll();

        http.rememberMe()
            .rememberMeServices(persistentTokenBasedRememberMeServices);

        http.logout()
            .logoutUrl(accountProperties.getApi().getLogoutPath())
            .logoutSuccessHandler(logoutSuccessHandler);
    }

    private String[] provideUnprotectedApiRoutes() {
        return new String[] {
            "/api/account/login",
            "/api/account/logout",
            "/api/account/register",
            "/api/account/activate",
            "/api/account/forgotten-password",
            "/api/account/reset-password",

            "/api/video/*",
            "/api/video/*/view",
            "/api/video/*/comments",

            "/api/videos/popular",
            "/api/videos/newest",
            "/api/videos/*/related",

            "/api/videos/search/autocomplete",
            "/api/videos/search/filter",

            "/api/streams/list",
            "/api/stream/manage/*",
            "/api/stream/manage/*/done",

            "/api/channel/*",
            "/api/channel/*/subscription"
        };
    }
}


