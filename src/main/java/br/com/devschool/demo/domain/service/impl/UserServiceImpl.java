package br.com.devschool.demo.domain.service.impl;

import br.com.devschool.demo.domain.model.internal.User;
import br.com.devschool.demo.domain.service.UserService;
import br.com.devschool.demo.infra.exception.UserNotFoundException;
import br.com.devschool.demo.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserId(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    }

    @Override
    public User createUser(User user) {
        User newUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .passwordHash(passwordEncoder.encode(user.getPasswordHash()))
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(Integer id, User user) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User userExistent = userOptional.get();

        User updatedUser = User.builder()
                .id(id)
                .name(user.getName())
                .email(user.getEmail())
                .passwordHash(passwordEncoder.encode(user.getPasswordHash()))
                .createdDate(userExistent.getCreatedDate())
                .updatedDate(LocalDate.now())
                .build();

        return userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUserId(Integer id) {
        if (userRepository.findById(id).isEmpty()) {
            throw  new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }
}
