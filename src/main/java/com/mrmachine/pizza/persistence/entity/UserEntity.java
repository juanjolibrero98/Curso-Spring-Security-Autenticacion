package com.mrmachine.pizza.persistence.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
	
	@Id
	@Column(nullable = false, length = 20)
	private String username;
	
	@Column(nullable = false, length = 200)
	private String password;
	
	@Column(length = 50)
	private String email;
	
	@Column(nullable = false)
	private Boolean locked;
	
	@Column(nullable = false)
	private Boolean disabled;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserRoleEntity> roles;
}
