package com.mrmachine.pizza.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrmachine.pizza.persistence.entity.PizzaEntity;
import com.mrmachine.pizza.persistence.repository.PizzaPagSortRepository;
import com.mrmachine.pizza.persistence.repository.PizzaRepository;
import com.mrmachine.pizza.service.dto.UpdatePizzaPriceDTO;
import com.mrmachine.pizza.service.exception.EmailApiException;

@Service
public class PizzaService {
	
	private final PizzaRepository pizzaRepo;
	
	//Esto es una interfaz de CRUD que agrega el ordenado y paginas de la info que presentes
	private final PizzaPagSortRepository pizzaPagSortRepo;
	

    PizzaService(PizzaRepository pizzaRepo, PizzaPagSortRepository pizzaPagSortRepo) {
        this.pizzaRepo = pizzaRepo;
		this.pizzaPagSortRepo = pizzaPagSortRepo;
    }

    public PizzaEntity get(Integer idPizza) {
    	return this.pizzaRepo.findById(idPizza).orElse(null);
    }
	
	public List<PizzaEntity> getAll() {
		return this.pizzaRepo.findAll();
	}
	
	/*
	 * AQUI hay un ejemplo de PageAndSorting
	 * Se devuelven objetos Page para mostrar en la API
	 * en donde los parametros destacables son el numero de 
	 * la pag y la cantidad de elementos a mostrar. 
	 * */
	public Page<PizzaEntity> getAll(int page, int elements) {
		Pageable pageRequest = PageRequest.of(page, elements);
		return this.pizzaPagSortRepo.findAll(pageRequest);
	}
	
	public PizzaEntity save(PizzaEntity pizza) {
		return this.pizzaRepo.save(pizza);
	}
	
	public Boolean exists(Integer idPizza) {
		return this.pizzaRepo.existsById(idPizza);
	}
	
	public void delete(Integer idPizza) {
		this.pizzaRepo.deleteById(idPizza);
	}
	
	public List<PizzaEntity> getAvailable() {
		return this.pizzaRepo.findAllByAvailableTrueOrderByPrice();
	}
	
	/*
	 * Metodo peculiar
	 * realiza la accion de devolver informacion dependiendo
	 * de su numero de pagina, cantidad de elementos, y ordenarlos
	 * dependiendo su dirreccion y por el parametro que deseas EJ
	 * Precio.
	 * */
	public Page<PizzaEntity> getAvailableSort(int page, int elements, String sortBy, String sortDirection) {
		Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
		Pageable pageRequest = PageRequest.of(page, elements, sort);
		return this.pizzaPagSortRepo.findByAvailableTrue(pageRequest);
	}
	
	
	public List<PizzaEntity> findByAvailableIsTrueAndNameIgnoreCaseContaining(String name) {
		return this.pizzaRepo.findByAvailableIsTrueAndNameIgnoreCaseContaining(name);
	}
	
	public List<PizzaEntity> pizzaWith(String receta) {
		return this.pizzaRepo.getByAvailableIsTrueAndDescriptionIgnoringCaseContaining(receta);
	}
	
	public List<PizzaEntity> pizzaWithOut(String receta) {
		return this.pizzaRepo.getByAvailableIsTrueAndDescriptionIgnoringCaseNotContaining(receta);
	}
	
	public List<PizzaEntity> getCheapest(Double price) {
		return this.pizzaRepo.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
	}
	
	/*
	 * La tranasaccion son procesos que comprometen la atomacidad
	 * ej pagar una factura si o si se debe pagar y si hubo un error
	 * se devuelve a su estado inicial, eso es atomacidad
	 * que si alguno de los procesos involucrados en una transaccion
	 * falla se realiza un rollback
	 * */
	@Transactional(noRollbackFor = EmailApiException.class)
	public void updatePrice(UpdatePizzaPriceDTO newPizzaPrice) {
		this.pizzaRepo.updatePrice(newPizzaPrice);
		//this.sendEmail();
	}
	
	private void sendEmail() {
		throw new EmailApiException();
	}
}
