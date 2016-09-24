package com.dsousaAlves.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dsousaAlves.model.StatusTitulo;
import com.dsousaAlves.model.Titulo;
import com.dsousaAlves.repository.filter.TituloFilter;
import com.dsousaAlves.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private static final String CADASTRO_VIEW = "cadastroTitulo";
	
	
	@Autowired
	private CadastroTituloService cadastroTituloService;

	@RequestMapping
	public ModelAndView pesquisaTitulos(@ModelAttribute("filtro")TituloFilter filtro) {
		List<Titulo> todoTitulos = cadastroTituloService.pesquisar(filtro);
		ModelAndView mv = new ModelAndView("pesquisaTitulos");
		mv.addObject("titulos", todoTitulos);
		return mv;
	}

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;

	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		
		try {
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("msg", "Título salvo com sucesso!");
			return "redirect:/titulos/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes){
		cadastroTituloService.excluir(id);
		attributes.addFlashAttribute("msg","Título excluído com sucesso");
		return "redirect:/titulos";
	}
	
	@RequestMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Titulo titulo){
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);		
		return mv;
	}
	
	@RequestMapping(value = "{id}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long id){
		return cadastroTituloService.receber(id);
	}
	
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> getStatusTiTulos() {
		return Arrays.asList(StatusTitulo.values());
	}
}
