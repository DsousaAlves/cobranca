package com.dsousaAlves.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dsousaAlves.model.StatusTitulo;
import com.dsousaAlves.model.Titulo;
import com.dsousaAlves.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private Titulos titulos;
	
	
	@RequestMapping("/novo")
	public String novo(){
		return "cadastroTitulo";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo){
		titulos.save(titulo);
		ModelAndView mv = new ModelAndView("cadastroTitulo");
		mv.addObject("msg", "Título salvo com sucesso!");
		return mv;
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> getStatusTiTulos(){
		return Arrays.asList(StatusTitulo.values());
	}
}
