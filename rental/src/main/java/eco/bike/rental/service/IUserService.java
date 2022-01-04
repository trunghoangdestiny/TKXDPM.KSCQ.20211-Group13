package eco.bike.rental.service;

import eco.bike.rental.entity.User;

public interface IUserService {
    User getById(Long userId);
}
