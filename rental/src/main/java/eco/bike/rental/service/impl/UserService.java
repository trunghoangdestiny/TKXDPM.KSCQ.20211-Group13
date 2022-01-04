package eco.bike.rental.service.impl;

import eco.bike.rental.entity.User;
import eco.bike.rental.repository.IUserRepository;
import eco.bike.rental.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public User getById(Long userId) {
        return userRepository.getById(userId);
    }
}
