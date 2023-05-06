package com.techtrader.service;

import com.techtrader.model.TraderDetails;
import com.techtrader.repository.TraderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final TraderRepository traderRepository;

    public JpaUserDetailsService(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return traderRepository.findByUsername(username)
                .map(TraderDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + " Not Found"));
    }
}
