package ch.oliatmo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshToken {
    private String access_token;
    private String refresh_token;
    private Object scope;
    private int expires_in;
    private int expire_in;
}

