package unipesquisas.diversos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;  
import org.primefaces.model.UploadedFile;

import unipesquisas.model.entity.ConfiguracoesSistema;
import unipesquisas.model.entity.EmailPadrao;
import unipesquisas.model.entity.Usuario;

public class Diversos implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2598611754967130179L;
	
	/*private Integer idEmpresa;
	private Integer idUsuario;
	private Integer idCandidato;*/

	public String copyFileToDir(UploadedFile file, String dir, String id) {
		if (file == null) {
			return null;
		}
		
		OutputStream out = null;
		String extension = "";
		
		try {
			try {
				InputStream in = file.getInputstream();

				extension = getExtension(file);
				
				File outputFile = new File(dir, id + "." + extension);	
				out = new FileOutputStream(outputFile);
				
				byte[] buffer = new byte[1024];
				
				while (true) {
					int bytes = in.read(buffer);
					if (bytes < 0) {
						break;
					}
					
					out.write(buffer, 0, bytes);
				}
			} finally {
				if (out != null) {
					out.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id + "." + extension;
	}

	public String getExtension(UploadedFile file) {
		
		int i = file.getFileName().lastIndexOf('.');
		int p = Math.max(file.getFileName().lastIndexOf('/'), file.getFileName().lastIndexOf('\\'));

		if (i > p) {
		    return file.getFileName().substring(i+1);
		}
		return null;
	}
	
	public String getMD5(String senha) {
		String sen = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        sen = hash.toString(16);              
        return sen;
		
	}
	
	public Integer getIdEmpresa () {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		return (Integer) session.getAttribute("idEmpresa");
	}
	
	public void setIdEmpresa (Integer idEmpresa) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("idEmpresa", idEmpresa);
	}
	
	public Integer getAdmin () {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		return (Integer) session.getAttribute("admin");
	}
	
	public void setAdmin (Integer admin) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("admin", admin);
	}
	
	public Integer getIdUsuario () {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		return (Integer) session.getAttribute("idUsuario");
	}
	
	public void setIdUsuario (Integer idUsuario) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("idUsuario", idUsuario);
	}

	public void encerraSessao(){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		session.invalidate();
	}

	public void setIdCandidato(Integer idCandidato) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("idCandidato", idCandidato);		
	}

	public Integer getIdCandidato() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		return (Integer) session.getAttribute("idCandidato");			
	}

	public void setIdEscolaridade(Integer idEscolaridade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("idEscolaridade", idEscolaridade);		
	}
	
	public Integer getIdEscolaridade (){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		return (Integer) session.getAttribute("idEscolaridade");
	}

	@SuppressWarnings("deprecation")
	public void enviaEmailBoasVindas(String emailCandidato, ConfiguracoesSistema cs, EmailPadrao ep) throws EmailException {
		HtmlEmail email = new HtmlEmail();  
        
        // adiciona uma imagem ao corpo da mensagem e retorna seu id  
//        URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");  
//        String cid = email.embed(url, "Apache logo");     
          
        // configura a mensagem para o formato HTML
        email.setHtmlMsg(ep.getMensagemboasvindas());
        
        // configure uma mensagem alternativa caso o servidor não suporte HTML  
        email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");  
          
        email.setHostName(cs.getEmailsmtp()); // o servidor SMTP para envio do e-mail  
        email.addTo(emailCandidato); //destinatário  
        email.setFrom(cs.getEmailfrom(), "UniPesquisas"); // remetente  
        email.setSubject(ep.getAssuntoboasvindas()); // assunto do e-mail  
//        email.setMsg("Teste de Email HTML utilizando commons-email"); //conteudo do e-mail  
        email.setAuthentication(cs.getEmaillogin(), cs.getEmailsenha());  
        email.setSmtpPort(Integer.parseInt(cs.getEmailsmtpporta()));
//        email.setSSLCheckServerIdentity(true);
        if(cs.getEmailssl() == 1){
        	email.setSSL(true);
        }
        if(cs.getEmailtls() == 1){
        	email.setTLS(true);
        }
        // envia email  
        try{
        	email.send();
        }catch (Exception e){
        	e.printStackTrace();
        }
	}

	@SuppressWarnings("deprecation")
	public void enviaEmailNovaSenha(String emailCandidato, String senha, ConfiguracoesSistema cs) throws EmailException {
		HtmlEmail email = new HtmlEmail();  
        
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<p align=\"center\"><font face=\"Arial\">Você solicitou no UniPesquisas.com para criar uma nova senha.<br/><br/>");
		sb.append("Clique no link abaixo e crie sua nova senha:<br/><br/>");
		sb.append("<a href=\"http://www.unipesquisas.com/pesquisa/NovaSenha.jsf?email="+emailCandidato+"&id="+senha+"\">Criar Nova Senha</a><br/><br/>");
		sb.append("Caso não tenha sido você a solicitar a criação da nova senha, não se preoculpe. Ignore esse email.");
		sb.append("</body>");
		sb.append("</html>");
        // configura a mensagem para o formato HTML
        email.setHtmlMsg(sb.toString());
        
        // configure uma mensagem alternativa caso o servidor não suporte HTML  
        email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");  
          
        email.setHostName(cs.getEmailsmtp()); // o servidor SMTP para envio do e-mail  
        email.addTo(emailCandidato); //destinatário  
        email.setFrom(cs.getEmailfrom(), "UniPesquisas"); // remetente
        email.setSubject("UniPesquisas.com - Criar nova senha."); // assunto do e-mail  
//        email.setMsg("Teste de Email HTML utilizando commons-email"); //conteudo do e-mail  
        email.setAuthentication(cs.getEmaillogin(), cs.getEmailsenha());  
        email.setSmtpPort(Integer.parseInt(cs.getEmailsmtpporta()));
//        email.setSSLCheckServerIdentity(true);
        if(cs.getEmailssl() == 1){
        	email.setSSL(true);
        }
        if(cs.getEmailtls() == 1){
        	email.setTLS(true);
        }
        // envia email  
        try{
        	email.send();
        }catch (Exception e){
        	e.printStackTrace();
        }
	}

	@SuppressWarnings("deprecation")
	public void enviarEmailNovoUsuario(Usuario usuario, String senha, ConfiguracoesSistema cs) throws EmailException {
		HtmlEmail email = new HtmlEmail();  
        
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<p align=\"center\"><font face=\"Arial\">Um novo login de acesso ao UniPesquisas.com foi criado. <br/><br/>");
		sb.append("Para acessar o Painel de Controle acesse:<br/><br/>");
		sb.append("<a href=\"http://www.unipesquisas.com/pesquisa/admin\">http://www.unipesquisas.com/pesquisa/admin</a><br/><br/>");
		sb.append("E utilize login: "+usuario.getEmail()+", e senha: "+senha);
		sb.append("</body>");
		sb.append("</html>");
        // configura a mensagem para o formato HTML
        email.setHtmlMsg(sb.toString());
        
        // configure uma mensagem alternativa caso o servidor não suporte HTML  
        email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");  
          
        email.setHostName(cs.getEmailsmtp()); // o servidor SMTP para envio do e-mail  
        email.addTo(usuario.getEmail()); //destinatário  
        email.setFrom(cs.getEmailfrom(), "UniPesquisas"); // remetente
        email.setSubject("UniPesquisas.com - Novo login de acesso."); // assunto do e-mail  
//        email.setMsg("Teste de Email HTML utilizando commons-email"); //conteudo do e-mail  
        email.setAuthentication(cs.getEmaillogin(), cs.getEmailsenha());  
        email.setSmtpPort(Integer.parseInt(cs.getEmailsmtpporta()));
//        email.setSSLCheckServerIdentity(true);
        if(cs.getEmailssl() == 1){
        	email.setSSL(true);
        }
        if(cs.getEmailtls() == 1){
        	email.setTLS(true);
        }
        // envia email  
        try{
        	email.send();
        }catch (Exception e){
        	e.printStackTrace();
        }		
	}

	@SuppressWarnings("deprecation")
	public void enviarEmailBoasVindasEmpresa(Integer idEmpresa, String emailResponsavel,
			String responsavel, ConfiguracoesSistema cs) throws EmailException {
		HtmlEmail email = new HtmlEmail();  
        
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<p align=\"center\"><font face=\"Arial\">Bem vindo ao UniPesquisas.com. <br/><br/>");
		sb.append("Informações Básicas:<br/><br/>");
		sb.append("Para acessar o painel de controle utilize o link abaixo:<br/>");
		sb.append("<a href=\"http://www.unipesquisas.com/pesquisa/admin\">http://www.unipesquisas.com/pesquisa/admin</a><br/><br/>");
		sb.append("Para divulgar a área dos alunos para que eles criem novas contas, e respondam as pesquisas, divulgue o link abaixo:<br/><br/>");
		sb.append("<a href=\"http://www.unipesquisas.com/pesquisa/admin/default.jsf?idEmpresa="+idEmpresa+"\">http://www.unipesquisas.com/pesquisa/default.jsf?idEmpresa="+idEmpresa+"</a><br/><br/>");
		sb.append("</body>");
		sb.append("</html>");
        // configura a mensagem para o formato HTML
        email.setHtmlMsg(sb.toString());
        
        // configure uma mensagem alternativa caso o servidor não suporte HTML  
        email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");  
          
        email.setHostName(cs.getEmailsmtp()); // o servidor SMTP para envio do e-mail  
        email.addTo(emailResponsavel); //destinatário  
        email.setFrom(cs.getEmailfrom(), "UniPesquisas"); // remetente
        email.setSubject("Bem Vindo ao UniPesquisas.com"); // assunto do e-mail  
//        email.setMsg("Teste de Email HTML utilizando commons-email"); //conteudo do e-mail  
        email.setAuthentication(cs.getEmaillogin(), cs.getEmailsenha());  
        email.setSmtpPort(Integer.parseInt(cs.getEmailsmtpporta()));
//        email.setSSLCheckServerIdentity(true);
        if(cs.getEmailssl() == 1){
        	email.setSSL(true);
        }
        if(cs.getEmailtls() == 1){
        	email.setTLS(true);
        }
        // envia email  
        try{
        	email.send();
        }catch (Exception e){
        	e.printStackTrace();
        }	
	}

	public boolean emailValido(String email){
		if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
	}
	
	public String formatIntoHHMMSS(Integer secsIn)
	{
		int hours = secsIn / 3600,
		remainder = secsIn % 3600,
		minutes = remainder / 60,
		seconds = remainder % 60;
	
		return ( (hours < 10 ? "0" : "") + hours
		+ ":" + (minutes < 10 ? "0" : "") + minutes
		+ ":" + (seconds< 10 ? "0" : "") + seconds );
	}

	public void setIdStatusCandidato(Integer idStatusCandidato) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("idStatusCandidato", idStatusCandidato);		
	}
	
	public Integer getIdStatusCandidato (){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpSession session = request.getSession();
		return (Integer) session.getAttribute("idStatusCandidato");
	}
}
