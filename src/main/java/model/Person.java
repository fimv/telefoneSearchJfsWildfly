package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Index;

@Entity
@Table(name = "person")
@Indexed
public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -20505665646013859L;
	
	@Id
	@GeneratedValue(generator = "gerador_de_id", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "gerador_de_id", sequenceName = "sequencia_h2", initialValue = 50, allocationSize = 1)
	@Column(nullable = false)
	private Long id;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(nullable = false, length = 40, name = "name")
	private String name;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(nullable = false, length = 40, name = "email")
	private String email;
	@Column(nullable = false, length = 40, name = "password")
	private String password;
	@IndexedEmbedded
	@ElementCollection(fetch = FetchType.EAGER)
	@Column(nullable = false, length = 20)
	private List<String> telefone;
	
	private Boolean login;
	
	
	public Person() {
		super();
	}

	public Person(String name, String email, String password, List<String> telefone) {
		super();
		this.name = name;		
		this.email = email;
		this.password = password;
		this.telefone = telefone;
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

	public void setNome(String name_) {
		this.name = name_;
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

	public void setSenha(String password) {
		this.password = password;
	}

	public List<String> getTelefone() {
		return telefone;
	}

	public void setTelefone(List<String> telefone) {
		this.telefone = telefone;
	}

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}

}