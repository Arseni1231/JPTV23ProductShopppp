package ee.ivkhkdev.productshop.services;

import ee.ivkhkdev.productshop.JPTV23ProductShopApplication;
import ee.ivkhkdev.productshop.model.entity.AppUser;
import ee.ivkhkdev.productshop.model.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public boolean authenticate(String username, String password) {
        Optional<AppUser> userOpt = appUserRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            JPTV23ProductShopApplication.currentUser = userOpt.get();
            return true;
        }
        return false;
    }

    public void logout() {
        JPTV23ProductShopApplication.currentUser = null;
    }
}