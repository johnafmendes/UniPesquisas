package unipesquisas.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Superclasse de todos os beans CDI
 */
public class AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814753648181900301L;

	/**
	 * Adiciona uma mensagem ao escopo da request. Esta mensagem pode ser exibida na tela através da chamada
	 * requestParam.<message>.
	 */
	protected void addMessageToRequest(String message) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.setAttribute("messages", message);
	}
	
	/**
	 * A partir de um outcome, retorna o outcome como sendo um redirect ao invés de forward
	 */
	protected String redirect(String outcome) {
		return outcome + "?faces-redirect=true";
	}
	
	protected void showMessage(String msg, FacesMessage.Severity type){
		FacesMessage fm = new FacesMessage(type, msg, msg);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);
	}
}
