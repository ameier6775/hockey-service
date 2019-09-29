package edu.ameier.hockey.services;

import edu.ameier.hockey.models.AppUser;
import edu.ameier.hockey.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static edu.ameier.hockey.security.SecurityConstants.*;

@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AppUser addUser(AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    public Map<String, Long> getUserId(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token == null)
        {
            throw new RuntimeException("token is null");
        }
        SecretKey key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA512");
        // Parsing the token
        String userName = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        AppUser appUser = userRepository.findByUserName(userName);

        Map<String, Long> response = new HashMap<>();
        response.put("userId", appUser.getId());
        return response;
    }



    }
