package aman.com.example.spring_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static aman.com.example.spring_security.security.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginPage("/login")
                .permitAll().defaultSuccessUrl("/courses", true)
                .passwordParameter("password")
                .usernameParameter("username");
        //MEANS THAT ONLY STUDENTS CAN ACCESSES TO THE API
               /* .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAnyAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
                .antMatchers( "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())*/
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails ammanAUser = User.builder().username("amanA").password(passwordEncoder.encode("password"))
                // roles(ApplicationUserRole.STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        UserDetails lindaUser = User.builder().username("linda").password(passwordEncoder.encode("password123"))
                //  roles(ApplicationUserRole.ADMIN.name())
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();
        UserDetails tomUser = User.builder().username("tom").password(passwordEncoder.encode("password123"))
                // roles(ApplicationUserRole.ADMINTRAINEE.name())
                .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(ammanAUser, lindaUser, tomUser);
    }
}
