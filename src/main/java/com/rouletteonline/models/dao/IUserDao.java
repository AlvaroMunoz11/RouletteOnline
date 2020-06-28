package com.rouletteonline.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rouletteonline.models.entity.User;


public interface IUserDao extends JpaRepository<User, Long> {

}