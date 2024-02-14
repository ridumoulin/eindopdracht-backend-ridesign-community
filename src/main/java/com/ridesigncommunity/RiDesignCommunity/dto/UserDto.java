package com.ridesigncommunity.RiDesignCommunity.dto;

public class UserDto {
    @NotBlank
    public String firstName;

    @NotBlank
    public String lastName;

    @NotBlank
    public String userName;

    @NotBlank
    public String email;

    @NotBlank
    @Size(min=7, max=30)
    public String password;

}
