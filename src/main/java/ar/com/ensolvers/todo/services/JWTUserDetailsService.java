package ar.com.ensolvers.todo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.secure.spi.GrantedPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.ensolvers.todo.security.jwt.JWTTokenUtil;
import io.jsonwebtoken.Claims;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ar.com.ensolvers.todo.entities.User u = userService.findByUsername(username);

        if (u != null) {
            return new User(u.getUsername(), u.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    public UserDetails loadUserByUsername(String username, String jwtToken) throws UsernameNotFoundException {

        Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        claims.forEach((key, value) -> {

            authorities.add(new SimpleGrantedAuthority("CLAIM_" + key + "_" + value));

        });

      
        return new User(username, "", authorities);

    }
}
