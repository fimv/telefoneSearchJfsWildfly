package bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import dao.PersonDao;
import model.Person;

public class SearchBean {
	private String searchText;
	private String resulstado = "";	
	
	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void limparResultado() {
		this.setResulstado("");
		this.setResulstado("Searching...! Поиск...! :)");			
	}
	
    public void info() {
        FacesContext.getCurrentInstance().addMessage(
        		null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
        				"Searched Data. Результаты поиска"));
    }
    
    public String irParaLogout() {
		return "logout?faces-redirect=true";
	}
	
	
	public String getResulstado() {
		return resulstado;
	}
	public void setResulstado(String resulstado) {
		this.resulstado = resulstado;
	}


	public List<Person> listarPessoas() {
		List<Person> persons = new ArrayList<>();
		PersonDao personDao = new PersonDao();
		persons.addAll(personDao.searchPessoas(searchText));
		return persons;
	}

}
