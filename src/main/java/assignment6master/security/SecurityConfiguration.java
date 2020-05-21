package assignment6master.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
			.antMatchers("/authenicate/createUser").hasRole("ADMIN")
			.antMatchers("/AccountHolder/").hasRole("ADMIN")
			.antMatchers("/AccountHolder/CDAccounts").hasRole("ADMIN")
			.antMatchers("/CDOffering/update/{term}/{interest}").hasRole("ADMIN")
			.antMatchers("/AccountHolder/CheckingAccounts").hasRole("ADMIN")
			.antMatchers("/AccountHolder/SavingsAccounts").hasRole("ADMIN")
			.antMatchers("/CDOffering/all").hasAnyRole("ADMIN", "USER")
			.antMatchers("/AccountHolder/{id}").hasRole("USER")
			.antMatchers("/AccountHolder/{id}/CDAccounts").hasRole("USER")
			.antMatchers("/AccountHolder/{id}/CheckingAccounts").hasRole("USER")
			.antMatchers("/AccountHolder/{id}/SavingsAccounts").hasRole("USER")
			.antMatchers("/authenticate").permitAll()
			.anyRequest().authenticated().and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
