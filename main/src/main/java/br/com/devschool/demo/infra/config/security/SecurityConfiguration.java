package br.com.devschool.demo.infra.config.security;

import br.com.devschool.demo.infra.api.AuthAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthAPI authAPI;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/project").permitAll()
                .antMatchers(HttpMethod.GET, "/version").permitAll()
                .antMatchers(HttpMethod.GET, "/screen").permitAll()
                .antMatchers(HttpMethod.GET, "/event").permitAll()
                .antMatchers(HttpMethod.GET, "/eventType").permitAll()
                .antMatchers(HttpMethod.GET, "/request").permitAll()
                .antMatchers(HttpMethod.GET, "/requestProperty").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new ValidationFilter(authAPI), UsernamePasswordAuthenticationFilter.class);
    }

}
