package com.ridesigncommunity.RiDesignCommunity.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@IdClass(AuthorityKey.class)
@Table(name = "authorities")
public class Authority implements Serializable {
    @Id
    @Column(nullable = false)
    private String email;

    @Id
    @Column(nullable = false)
    private String authority;

    public Authority() {
    }

    public Authority(String email, String authority) {
        this.email = email;
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
