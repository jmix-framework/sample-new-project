package com.company.sample.security;

import io.jmix.core.CoreProperties;
import io.jmix.core.security.UserRepository;
import io.jmix.core.security.impl.CoreUser;
import io.jmix.core.security.impl.InMemoryUserRepository;
import io.jmix.core.security.impl.SystemAuthenticationProvider;
import io.jmix.security.OnStandardSecurityImplementation;
import io.jmix.security.authentication.SecuredAuthenticationProvider;
import io.jmix.security.role.RoleRepository;
import io.jmix.security.role.assignment.RoleAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"com.company.sample.security", "io.jmix.security"})
@ConfigurationPropertiesScan(basePackages = {"com.company.sample.security", "io.jmix.security"})
@Conditional(OnLocalSecurityImplementation.class)
@EnableWebSecurity
public class LocalSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CoreProperties coreProperties;

    private final RoleRepository roleRepository;

    private final RoleAssignmentRepository roleAssignmentRepository;

    public LocalSecurityConfiguration(CoreProperties coreProperties, RoleRepository roleRepository, RoleAssignmentRepository roleAssignmentRepository) {
        this.coreProperties = coreProperties;
        this.roleRepository = roleRepository;
        this.roleAssignmentRepository = roleAssignmentRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new SystemAuthenticationProvider(userRepository()));

        SecuredAuthenticationProvider securedAuthenticationProvider = new SecuredAuthenticationProvider(roleRepository,
                roleAssignmentRepository);
        securedAuthenticationProvider.setUserDetailsService(userRepository());
        securedAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        auth.authenticationProvider(securedAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .anonymous(anonymousConfigurer -> {
                    anonymousConfigurer.key(coreProperties.getAnonymousAuthenticationTokenKey());
                    anonymousConfigurer.principal(userRepository().getAnonymousUser());
                })
                .csrf().disable()
                .headers().frameOptions().sameOrigin();
    }

    @Bean(name = "sec_AuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean(UserRepository.NAME)
    public UserRepository userRepository() {
        CoreUser systemUser = new CoreUser("system", "{noop}", "System");
        CoreUser anonymousUser = new CoreUser("anonymous", "{noop}", "Anonymous");
        InMemoryUserRepository repository = new InMemoryUserRepository(systemUser, anonymousUser);
        repository.createUser(new CoreUser("admin", "{noop}admin", "Admin"));
        return repository;
    }

    @Bean(name = "sec_PasswordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
