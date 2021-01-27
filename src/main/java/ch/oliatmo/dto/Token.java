package ch.oliatmo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private String access_token;
    private String refresh_token;
    private Object scope;
    private int expires_in;
    private int expire_in;
}
