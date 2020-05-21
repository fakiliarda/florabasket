package com.yaf.florabasket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 *
 * @date 15.05.2020
 */

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String USERS_QUERY = "select email, password, enabled from fbuser where email=?";
    private final String ROLES_QUERY = "select u.email, r.role from fbuser u inner join user_role ur on (u.id = ur.fbuser_id) inner join role r on (ur.role_id=r.id) where u.email=?";

    @Autowired
    public ApplicationSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.jdbcAuthentication()
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(ROLES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "index", "/css/**", "/js/**", "/img/**", "/img/**", "/scss/**", "/fonts/**", "/home/**", "/shop/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/signup_seller").permitAll()
                .antMatchers("/sendmail").permitAll()
                .antMatchers("/order/**").hasAuthority(Roles.CLIENT.toString())
                .antMatchers("/cart/**").hasAuthority(Roles.CLIENT.toString())
                .antMatchers("/product_details/**").hasAuthority(Roles.CLIENT.toString())
//                .antMatchers("/shop/**").hasAuthority(Roles.CLIENT.toString())
                .antMatchers("/c_order_list/**").hasAuthority(Roles.COURIER.toString())
                .antMatchers("/flowerSeller/**").hasAuthority(Roles.SELLER.toString())
                .antMatchers("/sellerOrder/**").hasAuthority(Roles.SELLER.toString())
                .antMatchers("/release/**").hasAuthority(Roles.SELLER.toString())
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .successHandler((req, res, auth) -> {    //Success handler invoked after successful authentication
                    res.sendRedirect("/"); // Redirect user to index/home page
                })
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home");


//                .and().rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(60 * 60)
//                .and().exceptionHandling().accessDeniedPage("/access_denied");


//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*", "/flowers/**").permitAll()
//                .antMatchers("/myorders/**").hasRole(CLIENT.name())
//                .antMatchers("/ordertrack/**").hasRole(SELLER.name())
//                .antMatchers("/orderassigned/**").hasRole(COURIER.name())
//                .anyRequest()
//                .authenticated()
//                .and();
//                .formLogin()
//                .loginPage("/login.html");
//                .and()
//                .logout();
//                .permitAll();

    }

    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }


//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails clientUser = User.builder()
//                .username("client")
//                .password(passwordEncoder.encode("password"))
//                .roles(CLIENT.name())
//                .build();
//
//        UserDetails sellerUser = User.builder()
//                .username("seller")
//                .password(passwordEncoder.encode("password"))
//                .roles(SELLER.name())
//                .build();
//
//        UserDetails courierUser = User.builder()
//                .username("courier")
//                .password(passwordEncoder.encode("password"))
//                .roles(COURIER.name())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                clientUser,
//                sellerUser,
//                courierUser
//        );
//    }
}
