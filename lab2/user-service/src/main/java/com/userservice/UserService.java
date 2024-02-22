package com.userservice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CompanyServiceFeignClient companyServiceFeignClient;


    @Autowired
    public UserService(UserRepository userRepository, CompanyServiceFeignClient companyServiceFeignClient) {
        this.userRepository = userRepository;
        this.companyServiceFeignClient = companyServiceFeignClient;
    }


    public boolean doesCompanyExist(Integer companyId) {
        return companyServiceFeignClient.existById(companyId);
    }

    public boolean doesUserExist(Integer userId) {
        return userRepository.existsById(userId);
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return transformToDTOList(users);
    }

    public UserDTO getUserById(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!user.getEnabled()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User disabled");
        }
        return transformToDTO(user);
    }

    public String getUserNameByID(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getName();
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = transformToEntity(userDTO);
        UserEntity savedUser = userRepository.save(userEntity);
        if (!doesCompanyExist(userDTO.getCompanyId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no company with this id");
        }
        return transformToDTO(savedUser);
    }

    public UserDTO changeUserEnabledField(Integer userId, boolean enabled) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setEnabled(enabled);
        UserEntity updatedUser = userRepository.save(user);
        return transformToDTO(updatedUser);
    }

    public UserDTO changeUserName(Integer userId, String newName) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setName(newName);
        UserEntity updatedUser = userRepository.save(user);
        return transformToDTO(updatedUser);
    }

    public UserDTO changeUserEmail(Integer userId, String newEmail) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setEmail(newEmail);
        UserEntity updatedUser = userRepository.save(user);
        return transformToDTO(updatedUser);
    }

    public UserDTO changeUserCompany(Integer userId, Integer newCompanyId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!doesCompanyExist(newCompanyId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no company with this id");
        }
        user.setCompanyId(newCompanyId);
        UserEntity updatedUser = userRepository.save(user);
        return transformToDTO(updatedUser);
    }


    private UserDTO transformToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setEnabled(user.getEnabled());
        userDTO.setCompanyId(user.getCompanyId());
        userDTO.setCompanyName(companyServiceFeignClient.getCompanyNameById(user.getCompanyId()));
        return userDTO;
    }

    private List<UserDTO> transformToDTOList(List<UserEntity> users) {
        return users.stream()
                .map(this::transformToDTO)
                .collect(Collectors.toList());
    }

    private UserEntity transformToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setLogin(userDTO.getLogin());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setEnabled(userDTO.getEnabled());
        userEntity.setCompanyId(userDTO.getCompanyId());
        return userEntity;
    }
}
