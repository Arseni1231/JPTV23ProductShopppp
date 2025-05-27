package ee.ivkhkdev.productshop.services;

import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.model.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> getUserById(Long id) {
        return appUserRepository.findById(id);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username).orElse(null);
    }

    public void addOrUpdateCustomer(AppUser user) {
        Optional<AppUser> existingUser = appUserRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        appUserRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (appUserRepository.existsById(id)) {
            appUserRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Пользователь не найден");
        }
    }
}