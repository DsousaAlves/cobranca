package com.dsousaAlves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dsousaAlves.model.StatusTitulo;
import com.dsousaAlves.model.Titulo;
import com.dsousaAlves.repository.Titulos;
import com.dsousaAlves.repository.filter.TituloFilter;



@Service
public class CadastroTituloService {
	
	@Autowired
	Titulos titulos;

	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
		  throw new	IllegalArgumentException("Formato da data inválida");
		}
	}

	public void excluir(Long id) {
		titulos.delete(id);
	}

	public String receber(Long id) {
		Titulo titulo = titulos.findOne(id);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> pesquisar(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}
	
	
	
}
