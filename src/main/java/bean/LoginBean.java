package bean;

import dao.PersonDao;

public class LoginBean {
	
	private Long id;
	private String name;
	private String password;
	
	public String irParaPessoas() {
		PersonDao personDao = new PersonDao();
		personDao.login(this.getNome(), this.getSenha());
		return "pessoas?faces-redirect=true";
	}
	
	public String irParaLogout(Long id) {
		PersonDao personDao = new PersonDao();
		personDao.logout(id);
		return "logout?faces-redirect=true";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
	}

	public String getSenha() {
		return password;
	}

	public void setSenha(String senha) {
		this.password = senha;
	}

}
