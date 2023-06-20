package com.lel.bookmingle.utility.context;

import com.lel.bookmingle.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Context {

    private String clientIp;
    private String token;
    private User user;
}
