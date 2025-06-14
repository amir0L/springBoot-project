package com.amir.RESTfulApi.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDto.UserResponse createUser(UserDto.CreateUserRequest request) {
        // Check if user already exists with this email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists with email: " + request.getEmail());
        }

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword()) // Note: In production, password should be hashed
                .build();

        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    public List<UserDto.UserResponse> getAllUsers() {
        List<User> users = userRepository.findAllOrderedByName();
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserDto.UserResponse> getUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(this::mapToUserResponse);
    }

    public Optional<UserDto.UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapToUserResponse);
    }

    public Optional<UserDto.UserWithMatieresResponse> getUserWithMatieres(Integer userId) {
        return userRepository.findById(userId)
                .map(this::mapToUserWithMatieresResponse);
    }

    @Transactional
    public Optional<UserDto.UserResponse> updateUser(Integer userId, UserDto.UpdateUserRequest request) {
        return userRepository.findById(userId)
                .map(user -> {
                    // Check if email is being changed and if new email already exists
                    if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
                        if (userRepository.existsByEmail(request.getEmail())) {
                            throw new RuntimeException("Email already exists: " + request.getEmail());
                        }
                        user.setEmail(request.getEmail());
                    }

                    // Update other fields if they are not null
                    if (request.getFirstname() != null) {
                        user.setFirstname(request.getFirstname());
                    }
                    if (request.getLastname() != null) {
                        user.setLastname(request.getLastname());
                    }
                    if (request.getPassword() != null) {
                        user.setPassword(request.getPassword()); // Note: In production, password should be hashed
                    }

                    User updatedUser = userRepository.save(user);
                    return mapToUserResponse(updatedUser);
                });
    }

    @Transactional
    public boolean deleteUser(Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<UserDto.UserResponse> searchUsersByName(String name) {
        List<User> users = userRepository.findByFirstnameOrLastnameContainingIgnoreCase(name);
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserDto.LoginResponse authenticateUser(UserDto.LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + request.getEmail());
        }

        User user = userOpt.get();

        // Note: In production, you should use proper password hashing (BCrypt, etc.)
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return UserDto.LoginResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .message("Login successful")
                .build();
    }

    // Helper method to calculate average grade for a matiere
    private Double calculateAverageGrade(com.amir.RESTfulApi.matiere.Matiere matiere) {
        double sum = 0;
        int count = 0;

        if (matiere.getNoteExam() != null) {
            sum += matiere.getNoteExam();
            count++;
        }
        if (matiere.getNoteDevoir1() != null) {
            sum += matiere.getNoteDevoir1();
            count++;
        }
        if (matiere.getNoteDevoir2() != null) {
            sum += matiere.getNoteDevoir2();
            count++;
        }

        return count > 0 ? sum / count : null;
    }

    // Helper method to map User entity to UserResponse DTO
    private UserDto.UserResponse mapToUserResponse(User user) {
        return UserDto.UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .matieresCount(user.getMatieres() != null ? user.getMatieres().size() : 0)
                .build();
    }

    // Helper method to map User entity to UserWithMatieresResponse DTO
    private UserDto.UserWithMatieresResponse mapToUserWithMatieresResponse(User user) {
        List<UserDto.MatiereBasicInfo> matiereInfos = null;

        if (user.getMatieres() != null) {
            matiereInfos = user.getMatieres().stream()
                    .map(matiere -> UserDto.MatiereBasicInfo.builder()
                            .matiereId(matiere.getMatiereId())
                            .name(matiere.getName())
                            .anneeScolaire(matiere.getAnneeScolaire())
                            .trimestre(matiere.getTrimestre())
                            .averageGrade(calculateAverageGrade(matiere))
                            .build())
                    .collect(Collectors.toList());
        }

        return UserDto.UserWithMatieresResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .matieres(matiereInfos)
                .build();
    }
}
