package com.lel.bookmingle.dto.mapper;

import com.lel.bookmingle.dto.response.UserResponse;
import com.lel.bookmingle.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
    }
}
