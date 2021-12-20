package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import dao.PersonDao;
import model.Person;

public class RegistrationBean {
	
	private String name;
	private String email;
	private String password;
	private String numer;
	private String ddd;
	
	private String resulstado = "";
	private String tipo = "";
	
	private boolean fix;
	private boolean mobile;
	
	public Person cadastrarPessoa() {
		
		ddd = numer.substring(1, 3);
		final String shortNumer = numer.substring(4, numer.length());		
		if (fix == true) {
			tipo = "Fix";
		} else if(mobile == true) {
			tipo = "Mobile";
		}
		
		List<String> telefone = new ArrayList<String>();
		telefone.add(ddd);
		telefone.add(shortNumer);
		telefone.add(tipo);
		
		Person person = new Person();
		person.setNome(name);
		person.setEmail(email);
		person.setSenha(password);
		person.setTelefone(telefone);
		person.setLogin(false);
		
		System.out.println("Nome: "+name+", E-mail: "+email+", Senha: "+password+", Numero: "+numer+", DDD: "+ddd+", "
				+ "Fixo: "+fix+", Celular: "+mobile+", Logado: "+person.getLogin()+"");
		
		PersonDao pessoaDao = new PersonDao();
		pessoaDao.cadastrar(person);
		
		this.setResulstado("");
		this.setResulstado("Registration ...! Регистрация...! :)");
		
		return person;
	}
	
	public String limparResultado() {
		this.setResulstado("");
		return "";
	}
	
    public void info() {
        FacesContext.getCurrentInstance().addMessage(
        		null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
        				"Registration sucsess. Регистрация прошла успешно"));
    }
    
	public String irParaLogin() {
		return "login?faces-redirect=true";
	}
	
	public String irParaLogout() {
		return "logout?faces-redirect=true";
	}
	
	public String getNome() {
		return name;
	}
	public void setNome(String nome) {
		this.name = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return password;
	}
	public void setSenha(String senha) {
		this.password = senha;
	}
	public String getNumero() {
		return numer;
	}
	public void setNumero(String numero) {
		this.numer = numero;
	}
	public boolean isFixo() {
		return fix;
	}
	public void setFixo(boolean fixo) {
		this.fix = fixo;
	}
	public boolean isCelular() {
		return mobile;
	}
	public void setCelular(boolean celular) {
		this.mobile = celular;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	
	
	public String getResulstado() {
		return resulstado;
	}
	public void setResulstado(String resulstado) {
		this.resulstado = resulstado;
	}
	
}
