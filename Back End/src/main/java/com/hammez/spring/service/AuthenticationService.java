/*
 * File: AuthenticationServer
 *
 * Copyright(c) 2019 Pole Star EAM Limited.  All Rights Reserved.
 * This software is the proprietary information of Pole Star.
 *
 * @created: 16/10/2019, 12:58
 * @copyright: Pole Star
 */
package com.hammez.spring.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

public class AuthenticationService {

// ************************************************************************\
// Static Variables                                                        *
// ************************************************************************/

    private static final long EXPIRATIONTIME = 864_000_00; // 1 day in milliseconds
    private static final String SIGNINKEY = "SecretKey";
    private static final String PREFIX = "Bearer";

// ************************************************************************\
// Instance Variables                                                      *
// ************************************************************************/

// ************************************************************************\
// Constructors                                                            *
// ************************************************************************/

// ************************************************************************\
// Public Methods                                                          *
// ************************************************************************/

// ************************************************************************\
// Protected Methods                                                       *
// ************************************************************************/

// ************************************************************************\
// Private Methods                                                         *
// ************************************************************************/

// ************************************************************************\
// Inner Classes                                                           *
// ************************************************************************/

// ************************************************************************\
// Static Methods                                                          *
// ************************************************************************/

    // Add token to Authorisation header
    public static void addToken(HttpServletResponse res, String username) {
        String jwtToken = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SIGNINKEY).compact();
        res.addHeader("Authorization", PREFIX + " " + jwtToken);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

    // Get Token from Authorisation header
    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SIGNINKEY)
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }

}