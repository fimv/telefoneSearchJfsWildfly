package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import bean.UpdateBean;
import model.Person;

public class PersonDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6246523898993213355L;
	private Boolean login = false;
	
	public static SessionFactory obterSessionFactory() {
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(Person.class);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public Person cadastrar(Person person) {
		Session sessionCadastrar = obterSessionFactory().openSession();
		sessionCadastrar.beginTransaction();
		sessionCadastrar.save(person);
		sessionCadastrar.getTransaction().commit();
		sessionCadastrar.close();
		return person;
	}
	
	public Boolean login(String name, String password) {
		Person person = new Person();
		
		Session sessionLogin = obterSessionFactory().openSession();
		sessionLogin.beginTransaction();
		
		CriteriaBuilder cb = sessionLogin.getCriteriaBuilder();
		CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		Root<Person> _person = cq.from(Person.class);
		Predicate predicate = cb.equal(_person.get("name"), name);
		cq.select(_person).where(predicate);
		
		Query<Person> query = sessionLogin.createQuery(cq);
		person = query.getSingleResult();
		
		if (this.getLogin() == false) {
			person.setLogin(true);
			this.setLogin(true);
			
			UpdateBean updateBean = new UpdateBean();
			updateBean.setId(person.getId());
			updateBean.setNome(person.getNome());
			updateBean.setEmail(person.getEmail());
			updateBean.setSenha(person.getSenha());
			updateBean.setNumero(person.getTelefone().get(0));
			updateBean.setDdd(person.getTelefone().get(1));
			
			if (person.getTelefone().get(2) == "Fix") {
				updateBean.setFixo(true);
			}
			
			if (person.getTelefone().get(2) == "Mobile") {
				updateBean.setCelular(true);
			}
			
			sessionLogin.saveOrUpdate(person);
		}
		
		sessionLogin.getTransaction().commit();
		sessionLogin.close();
		return false;
	}
	
	public Boolean logout(Long id) {
		Person person = new Person();
		
		Session sessionPessoa = obterSessionFactory().openSession();
		sessionPessoa.beginTransaction();
		
		person = (Person) sessionPessoa.get(Person.class, id);
		
		if (this.getLogin() == true) {
			person.setLogin(false);
			this.setLogin(false);
			sessionPessoa.saveOrUpdate(person);
			return true;
		}
		
		sessionPessoa.getTransaction().commit();
		sessionPessoa.close();
		return false;
	}
	
	public Person obterUmaPessoa(Long id) {
		if (this.getLogin() == true) {
			Person person = new Person();
			Session sessionPessoa = obterSessionFactory().openSession();
			sessionPessoa.beginTransaction();
			
			person = (Person) sessionPessoa.get(Person.class, id);
			
			sessionPessoa.getTransaction().commit();
			sessionPessoa.close();
			return person;
		}
		return null;
	}
	
	public List<Person> obterPessoas() {
		List<Person> persons = new ArrayList<>();
		Session sessionPessoas = obterSessionFactory().openSession();
		sessionPessoas.beginTransaction();
		
		CriteriaBuilder cb = sessionPessoas.getCriteriaBuilder();
		CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		Root<Person> person = cq.from(Person.class);
		cq.select(person).orderBy(cb.asc(person.get("id")));
		Query<Person> query = sessionPessoas.createQuery(cq);
		persons = query.list();
		
		sessionPessoas.getTransaction().commit();
		sessionPessoas.close();
		return persons;
	}
	
	public List<Person> searchPessoas(String text) {
		List<Person> persons=null;
		Session sessionPessoas = obterSessionFactory().openSession();
		sessionPessoas.beginTransaction();

			FullTextSession fullTextSession = Search.getFullTextSession(sessionPessoas);						
			try {
				fullTextSession.createIndexer().startAndWait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			QueryBuilder qb;
			try {
				qb = fullTextSession.getSearchFactory().buildQueryBuilder()
						.forEntity(Person.class).get();			
			
			//Create lucene  query
			// Set indexed field
			org.apache.lucene.search.Query lucenceQuery=qb.keyword()
					.onFields("name","email","telefone").matching(text).createQuery();
			
			//Warp lucene query in org.hibernate.query.Query
			@SuppressWarnings("unchecked")
			Query<Person> query=fullTextSession.createFullTextQuery(lucenceQuery,
					Person.class);
			persons=query.getResultList();	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return persons;

	}
	
	public Boolean atualizarPeloId(Long id, Person pessoa) {
		Session sessionAtualizar = obterSessionFactory().openSession();
		sessionAtualizar.beginTransaction();
		
		sessionAtualizar.saveOrUpdate(pessoa);
		
		sessionAtualizar.getTransaction().commit();
		sessionAtualizar.close();
		return true;
	}
	
	public Person deletarPessoaPeloId(Long id) {
		Person person = new Person();
		Session sessionDeletar = obterSessionFactory().openSession();
		sessionDeletar.beginTransaction();
		
		person = sessionDeletar.get(Person.class, id);
		sessionDeletar.delete(person);
		
		sessionDeletar.getTransaction().commit();
		sessionDeletar.close();
		return person;
	}

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}

}
