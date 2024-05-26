package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.dto.*;
import id.ac.ui.cs.advprog.auth.model.UserEntity;

import java.util.List;

public interface UserService {
    public UserEntity create(RegisterDto user);
    public List<UserEntity> findAll();
    UserDto findByUsername(String username);
    public void update(String username, ProfileEditDto data);
    public AuthResponseDto login(LoginDto user);
}
