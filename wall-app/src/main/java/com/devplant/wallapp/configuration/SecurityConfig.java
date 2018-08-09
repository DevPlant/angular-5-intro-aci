package com.devplant.wallapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // for h2 console IFRAME
        httpSecurity.headers().frameOptions().disable();

        // Enable/Disable Http Basic
        // httpSecurity.httpBasic();
        // Enable/Disable Form Login
        httpSecurity.formLogin();
        // Enable/Disable CSRF ( just so you can test your Calls! )
        httpSecurity.csrf().disable();

        httpSecurity.exceptionHandling()
                //Actually Spring already configures default AuthenticationEntryPoint - LoginUrlAuthenticationEntryPoint
                //This one is REST-specific addition to default one, that is based on PathRequest
                .defaultAuthenticationEntryPointFor(getRestAuthenticationEntryPoint(),
                        new AntPathRequestMatcher("/api/**"));

        configureAppSpecificHttpSecurityPaths(httpSecurity);

    }

    private AuthenticationEntryPoint getRestAuthenticationEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    public static void configureAppSpecificHttpSecurityPaths(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                // allow everything on swagger, the other ones are just resources swagger loads, nothing 'dangerous here'
                .antMatchers("/h2-console/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/**")
                .permitAll();
        // Custom Config follows ! we'll write this
        String[] ACCOUNT_PATHS = { "/api/accounts/me", "/api/accounts/change-password", "/api/accounts/logout" };
        String[] ACCOUNT_REGISTER_PATH = { "/api/accounts/register", "/api/accounts/login" };
        String[] API_PATHS = { "/api/posts/**", "/api/replies/**", "/*" };

        httpSecurity.authorizeRequests().antMatchers(ACCOUNT_REGISTER_PATH).permitAll();

        httpSecurity.authorizeRequests().antMatchers(ACCOUNT_PATHS).authenticated();

        httpSecurity.authorizeRequests().antMatchers(API_PATHS).authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder()).authoritiesByUsernameQuery(
                "select account_username,roles from account_roles where account_username = ?")
                .usersByUsernameQuery("select username,password,enabled from accounts where username = ?");
    }
}