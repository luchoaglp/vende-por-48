package ar.com.vendepor.vendepor48.security;

import ar.com.vendepor.vendepor48.domain.User;
import ar.com.vendepor.vendepor48.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRespository;

    public CustomUserDetailsService(ClientRepository clientRespository) {
        this.clientRespository = clientRespository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        // Let people login with username or email
        User user = clientRespository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found.")
                );

        return UserPrincipal.create(user);
    }

}
