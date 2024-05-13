package com.mrmachine.pizza.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrmachine.pizza.persistence.entity.OrderEntity;
import com.mrmachine.pizza.persistence.repository.OrderRepository;
import com.mrmachine.pizza.projection.OrderSummary;
import com.mrmachine.pizza.service.dto.RandomPizzaDTO;

@Service
public class OrderService {

	private final OrderRepository orderRepo;

	private static final String DELIVERY = "D";
	private static final String CARRYOUT = "C";
	// private static final String ON_SIDE = "D";

	OrderService(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	public List<OrderEntity> getAll() {
		return this.orderRepo.findAll();
	}

	public List<OrderEntity> getTodayOrders() {
		LocalDateTime today = LocalDate.now().atTime(0, 0);
		return this.orderRepo.findAllByDateAfter(today);
	}

	public List<OrderEntity> getOutSideOrders() {
		List<String> methods = Arrays.asList(DELIVERY, CARRYOUT);
		return this.orderRepo.findAllByMethodIn(methods);
	}

	@Secured("ROLE_ADMIN") // Asi bloqueamos el acceso a este servicio, solo los admin pueden acceder
	public List<OrderEntity> getCustomerOrders(String idCustommer) {
		return this.orderRepo.findCustomerOrders(idCustommer);
	}

	/*
	 * Encontrar una informacion utilizando una proyeccion ir al repositorio clic en
	 * el objetoSumary para la explicacion
	 */
	public OrderSummary getSummary(Integer orderId) {
		return this.orderRepo.findSummary(orderId);
	}

	/*
	 * Metodo que en resumen, el procedimiento selecciona una pizza aleatoria
	 * disponible, aplica un descuento, crea un nuevo pedido en la base de datos con
	 * la pizza aleatoria y maneja posibles errores. Al final, establece el estado
	 * de order_taken para indicar si el pedido se ha tomado con éxito.
	 */
	@Transactional
	public Boolean saveRandomOrder(RandomPizzaDTO ranOrderDto) {
		return this.orderRepo.saveRandomOrder(ranOrderDto.getName(), ranOrderDto.getPrice());
	}
}
