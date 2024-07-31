package vn.capigiba.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.capigiba.laptopshop.domain.User;
import vn.capigiba.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public User handleSaveUser(User user) {
        User capigiba = this.userRepository.save(user);
        System.out.println(capigiba);
        return capigiba;
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }
}
