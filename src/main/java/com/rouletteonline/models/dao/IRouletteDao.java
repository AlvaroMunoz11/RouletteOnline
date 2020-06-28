package com.rouletteonline.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rouletteonline.models.entity.Roulette;


public interface IRouletteDao extends JpaRepository<Roulette, Long> {

}
