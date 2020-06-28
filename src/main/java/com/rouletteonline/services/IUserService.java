package com.rouletteonline.services;

import com.rouletteonline.models.entity.User;

public interface IUserService {

	public User findUserById(Long id);
}
