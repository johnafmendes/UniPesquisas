package unipesquisas.model.entity;

import java.io.Serializable;

public class ConfiguracoesSistema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5366769034625552598L;

	private String emailsmtp;
	private String emailfrom;
	private String emaillogin;
	private String emailsenha;
	private String emailsmtpporta;
	private Integer status;
	private Integer emailssl;
	private Integer emailtls;
	private Integer emailautenticacaosmtp;

	public String getEmailsmtp() {
		return emailsmtp;
	}
	public void setEmailsmtp(String emailsmtp) {
		this.emailsmtp = emailsmtp;
	}
	public String getEmailfrom() {
		return emailfrom;
	}
	public void setEmailfrom(String emailfrom) {
		this.emailfrom = emailfrom;
	}
	public String getEmaillogin() {
		return emaillogin;
	}
	public void setEmaillogin(String emaillogin) {
		this.emaillogin = emaillogin;
	}
	public String getEmailsenha() {
		return emailsenha;
	}
	public void setEmailsenha(String emailsenha) {
		this.emailsenha = emailsenha;
	}
	public String getEmailsmtpporta() {
		return emailsmtpporta;
	}
	public void setEmailsmtpporta(String emailsmtpporta) {
		this.emailsmtpporta = emailsmtpporta;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getEmailssl() {
		return emailssl;
	}
	public void setEmailssl(Integer emailssl) {
		this.emailssl = emailssl;
	}
	public Integer getEmailautenticacaosmtp() {
		return emailautenticacaosmtp;
	}
	public void setEmailautenticacaosmtp(Integer emailautenticacaosmtp) {
		this.emailautenticacaosmtp = emailautenticacaosmtp;
	}
	public Integer getEmailtls() {
		return emailtls;
	}
	public void setEmailtls(Integer emailtls) {
		this.emailtls = emailtls;
	}

}