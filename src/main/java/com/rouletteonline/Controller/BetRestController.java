package com.rouletteonline.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = {"http://localhost:4200"} )
@RestController
@RequestMapping("/api")

public class BetRestController {
	

	@Autowired
	private IBetService betService;
	
	@Autowired
	private IUserService UserService;
	
	@Autowired
	private IRouletteService rouletteService;
	
	@PostMapping("bet/{id}")
	public ResponseEntity<?> create(@Valid @RequestBody Bet bet, BindingResult result, @PathVariable Long id) {
		
		Bet betNew = null;
		
		User currentUser = userService.findUserById(id);
		
		Roulette roulette = rouletteService.findById(bet.getRoulette().getId());
		
		Map<String, Object> response = new HashMap<>();

		if (roulette.getState().equals("Cerrada")) {
			response.put("mensaje:", "Lo siento la ruleta se encuentra cerrada");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		} else if (currentUser.getCredit() < bet.getAmountMoney()) {
			response.put("mensaje", "Lo sentimos tu apuesta en estos momentos no esta disponible saldo actual: $"
					+ currentUser.getCredit());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		} else if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El Campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		try {
			betNew = betService.save(bet);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "la apuesta ha sido creado con éxito!");
		response.put("ruleta", betNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	Map<String, Object> response = new HashMap<>();

	if (roulette.getState().equals("Cerrada")) {
		response.put("mensaje:", "Lo siento la ruleta se encuentra cerrada");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

	} else if (currentUser.getCredit() < bet.getAmountMoney()) {
		response.put("mensaje", "Lo sentimos tu apuesta en estos momentos no esta disponible saldo actual: $"
				+ currentUser.getCredit());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

	} else if (result.hasErrors()) {

		List<String> errors = result.getFieldErrors().stream()
				.map(err -> "El Campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
		response.put("errors", errors);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

	}

	try {
		betNew = betService.save(bet);
	} catch (DataAccessException e) {
		response.put("mensaje", "Error al realizar el insert en la base de datos");
		response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	response.put("mensaje", "la apuesta ha sido creado con éxito!");
	response.put("ruleta", betNew);
	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

}
}