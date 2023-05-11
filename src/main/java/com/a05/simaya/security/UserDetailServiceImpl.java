package com.a05.simaya.security;

import com.a05.simaya.anggota.model.AnggotaModel;
import com.a05.simaya.anggota.repository.AnggotaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AnggotaDb anggotaDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AnggotaModel anggota = anggotaDb.findByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(anggota.getRole().toString()));
        return new CustomUserDetails(anggota.getUsername(), anggota.getPassword(), anggota.getProfile().getPhotoUrl(), grantedAuthorities);
    }

    private final static class CustomUserDetails extends User implements UserDetails {
        private String imageUrl;
        private CustomUserDetails(String username, String password, String imageUrl, Set<GrantedAuthority> grantedAuthorities) {
            super(username, password, grantedAuthorities);
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
