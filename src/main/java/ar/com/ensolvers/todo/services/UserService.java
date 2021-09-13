package ar.com.ensolvers.todo.services;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ar.com.ensolvers.todo.entities.*;
import ar.com.ensolvers.todo.security.Crypto;

import ar.com.ensolvers.todo.repos.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User login(String username, String password) {

    User u = findByUsername(username);

    if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {

      throw new BadCredentialsException("Username or password invalid");
    }

    return u;
  }

  public User create(String email, String password) {

    User user = new User();
    user.setUsername(email);
    user.setEmail(email);
    user.setPassword(Crypto.encrypt(password, email.toLowerCase()));

    return user;
  }

  public User findByEmail(String email) {

    return userRepository.findByEmail(email);
  }

  public User findBy(Integer id) {
    Optional<User> customerOp = userRepository.findById(id);

    if (customerOp.isPresent()) {
      return customerOp.get();
    }

    return null;
  }

  public UserDetails getUserAsUserDetail(User user) {
    UserDetails uDetails;

    uDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        getAuthorities(user));

    return uDetails;
  }

  // Usamos el tipo de datos SET solo para usar otro diferente a List private
  Set<? extends GrantedAuthority> getAuthorities(User user) {

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + user.toString()));

    return authorities;
  }

  public Map<String, Object> getUserClaims(User loggedUser) {
    return new HashMap<String, Object>();
  }
}
