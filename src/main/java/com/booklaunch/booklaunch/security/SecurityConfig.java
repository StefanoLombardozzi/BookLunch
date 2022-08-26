package com.booklaunch.booklaunch.security;

import com.booklaunch.booklaunch.security.filter.CustomAuthenticationFilter;
import com.booklaunch.booklaunch.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Metodo che richiama il metodo loadUserByUsername(String username),
     * che controlla l'esistenza dell'utente al momento del login.
     * Se non presente, verrà restituito un messaggio di errore
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


    /**
     * Meotodo che configura l'accesso agli endpoint in base al ruolo
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers( "/login**","/token/refresh/**").permitAll();
        http.
                authorizeRequests()
                .antMatchers("/", "/public/**", "/resources/static/**")
                .permitAll();


        /**
         * Gestione delle authorization per l'accesso agli endpoint di tipo Utente
         */
        http.authorizeRequests().antMatchers(GET, "/api/utente/findAll").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/utente/createUtente").permitAll();
        http.authorizeRequests().antMatchers(DELETE, "/api/utente/deleteUtente").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/utente/deleteUtente").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/api/utente/createAdmin").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/utente/updateUtente").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/utente/updateUtente").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(GET, "/api/utente/findEmail").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/utente/findCognome").hasAnyAuthority("ROLE_ADMIN");

        /**
         * Gestione delle authorization per l'accesso agli endpoint di tipo Prenotazione
         */
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/findAll").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/prenotazione/insertPrenotazione").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/prenotazione/insertPrenotazione").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(DELETE, "/api/prenotazione/deletePrenotazione").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(DELETE, "/api/prenotazione/deletePrenotazione").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/findByData").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/countByPranzoAndData").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/countByCenaAndData").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/countByColazioneAndData").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/countBySacchettoPranzoAndData").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/countBySacchettoCenaAndData").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/prenotazione/updatePrenotazione").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/prenotazione/updatePrenotazione").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/findByUtente").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/prenotazione/findByUtente").hasAnyAuthority("ROLE_USER");


        /**
         * Gestione delle authorization per l'accesso agli endpoint di tipo Richiesta
         */
        http.authorizeRequests().antMatchers(GET, "/api/richiesta/findAll").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/richiesta/createRichiesta").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/api/richiesta/createRichiesta").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/richiesta/deleteRichiesta").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(DELETE, "/api/richiesta/deleteRichiesta").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/richiesta/getRichiesteUtente").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/richiesta/getRichiesteAdmin").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/richiesta/getRichiesteByUtente").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(PUT, "/api/richiesta/accettaRichiesta").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/richiesta/rifiutaRichiesta").hasAnyAuthority("ROLE_ADMIN");


        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);

        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
