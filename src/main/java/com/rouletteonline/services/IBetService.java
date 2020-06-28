package com.rouletteonline.services;

import java.util.List;

import com.rouletteonline.models.entity.Bet;


public interface IBetService {

	public Bet save(Bet bet);

	public int setBetInfoByRouletteId(Long rouletteId);

	public List<Bet> findBetsByRouletteId(Long rouletteId);

}
