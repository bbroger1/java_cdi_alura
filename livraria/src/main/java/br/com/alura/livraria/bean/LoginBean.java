package br.com.alura.livraria.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.alura.livraria.dao.UsuarioDao;
import br.com.alura.livraria.modelo.Usuario;
import br.com.rcssoft.rcssoft_lib.helper.MessageHelper;

@Named
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	private UsuarioDao usuarioDao;
	private FacesContext context;

	private MessageHelper helper;
	
	@Inject
	public LoginBean(UsuarioDao usuarioDao, FacesContext context, MessageHelper helper) {
		this.usuarioDao = usuarioDao;
		this.context = context;
		this.helper = helper;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public String efetuaLogin() {
		System.out.println("fazendo login do usuario " + this.usuario.getEmail());
		
		boolean existe = usuarioDao.existe(this.usuario);
		if(existe ) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		}
		
		helper
			.onFlash()
				.addMessage(new FacesMessage("Usuário não encontrado"));
		
		return "login?faces-redirect=true";
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}
}
