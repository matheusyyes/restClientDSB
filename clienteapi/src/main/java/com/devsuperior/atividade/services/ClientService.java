package com.devsuperior.atividade.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.atividade.dto.ClientDTO;
import com.devsuperior.atividade.entities.Client;
import com.devsuperior.atividade.repositories.ClientRepository;
import com.devsuperior.atividade.services.exceptions.DatabaseException;
import com.devsuperior.atividade.services.exceptions.ResourceNotFoundException;


@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepo;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageable){
		Page<Client> list = clientRepo.findAll(pageable);
		return list.map(x -> new ClientDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		 Optional<Client> obj= clientRepo.findById(id);
		 Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		 ClientDTO dto = new ClientDTO(entity);
		 return dto;
	}
	
	@Transactional
	public ClientDTO insert( ClientDTO dto) {
		
		Client entity = new Client();
		copyDtoToEntity(entity, dto);
		
		clientRepo.save(entity);
		
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(ClientDTO dto, Long id) {
		
		try {
		Client entity = clientRepo.getOne(id);
		copyDtoToEntity(entity, dto);
		
		return new ClientDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado");
		}
		
		
	}
	
	@Transactional
	public void delete(Long id) {
		
		try {
			clientRepo.deleteById(id);
			
		}catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}

	public void copyDtoToEntity (Client entity, ClientDTO dto) {
		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		
	}

	
}
