package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.dto.AuthResponseDto;
import id.ac.ui.cs.advprog.auth.dto.LoginDto;
import id.ac.ui.cs.advprog.auth.dto.ProfileEditDto;
import id.ac.ui.cs.advprog.auth.dto.RegisterDto;
import id.ac.ui.cs.advprog.auth.model.UserEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    public CompletableFuture<UserEntity> create(RegisterDto user);
    public CompletableFuture<List<UserEntity>> findAll();
    CompletableFuture<UserEntity> findByUsername(String username);
    public void update(String username, ProfileEditDto data);
    public CompletableFuture<AuthResponseDto> login(LoginDto user);
}
