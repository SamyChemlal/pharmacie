package com.Pharmacie.pharma.Autre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Pharmacie.pharma.Service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/inscription").permitAll()
                .antMatchers(HttpMethod.GET, "/api/Produit/liste").permitAll()
                .antMatchers(HttpMethod.POST, "/api/connexion").permitAll()
                .antMatchers(HttpMethod.POST, "/api/connexion/deconnexion").permitAll()
                .antMatchers(HttpMethod.POST, "/api/Produit/AjoutProduit").permitAll()
                .antMatchers(HttpMethod.GET, "/api/connexion/user/details/{email}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/cart/commandes-user/86057b1c-acd5-49ad-a7cc-e706f6b87850").permitAll()
                .antMatchers(HttpMethod.POST, "/api/cart/ajouter").permitAll()
                .antMatchers(HttpMethod.GET, "/api/panier/commandes-user/{user}").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/Cart/supprimer-commande/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/Cart/supprimer-cart/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/Produit/{productId}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/facture/Ajouter-Facture/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/facture/recuperer/{factureId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/Role/{userId}").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/Produit//decrementer-quantite/{nomProduit}/{quantiteDecrementee}").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .and()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
    

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}