package bean;

import java.util.ArrayList;
import java.util.List;

import dao.PersonDao;
import model.Person;

public class PersonBean { 
	
	public List<Person> listarPessoas() {
		List<Person> persons = new ArrayList<>();
		PersonDao personDao = new PersonDao();
		persons.addAll(personDao.obterPessoas()); 
		return persons;
	}
	
	public String deletarPessoa(Long id) {
		PersonDao personDao = new PersonDao();
		personDao.deletarPessoaPeloId(id);
		return "pessoas?faces-redirect=true";
	}
	
	public String irParaCadastro() {
		return "index?faces-redirect=true";
	}
	
	public String irParaAtualizar() {
		return "atualizar?faces-redirect=true";
	}
	
	public String irParaLogout() {
		return "logout?faces-redirect=true";
	}

}
