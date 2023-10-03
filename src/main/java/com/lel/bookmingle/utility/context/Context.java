package com.lel.bookmingle.utility.context;

import com.lel.bookmingle.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Context {

    private String clientIp;
    private String token;
    private User user;
}
