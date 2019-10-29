package com.hammez.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hammez.spring.domain.AccountCredentials;
import com.hammez.spring.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

// ************************************************************************\
// Static Variables                                                        *
// ************************************************************************/

// ************************************************************************\
// Instance Variables                                                      *
// ************************************************************************/

// ************************************************************************\
// Constructors                                                            *
// ************************************************************************/

    protected LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authManager);
    }

    protected LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

// ************************************************************************\
// Public Methods                                                          *
// ************************************************************************/

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), Collections.emptyList())
        );
    }

// ************************************************************************\
// Protected Methods                                                       *
// ************************************************************************/

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        AuthenticationService.addToken(response, authResult.getName());
    }

// ************************************************************************\
// Private Methods                                                         *
// ************************************************************************/

// ************************************************************************\
// Inner Classes                                                           *
// ************************************************************************/

// ************************************************************************\
// Static Methods                                                          *
// ************************************************************************/

}