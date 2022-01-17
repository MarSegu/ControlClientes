package mx.com.gm.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Admin
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder build){
        try {
            build.userDetailsService(userDetailsService);
        } catch (Exception ex) {
            Logger.getLogger(SecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.authorizeRequests()
                    .antMatchers("/editar/**", "/agregar/**", "/eliminar")
                    .hasRole("ADMIN")
                    .antMatchers("/")
                    .hasAnyRole("USER", "ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .and()
                    .exceptionHandling().accessDeniedPage("/errores/403");
            ;
        } catch (Exception ex) {
            Logger.getLogger(SecurityConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
