package br.com.inventory.db;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;

import br.com.inventory.core.Category;
import io.dropwizard.hibernate.AbstractDAO;

public class CategoryDao extends AbstractDAO<Category> {

	public CategoryDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Category findById(Long id) {
		return get(id);
	}

	public List<Category> findByName(String name) {
		final CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		final CriteriaQuery<Category> query = cb.createQuery(Category.class);
		final Root<Category> from = query.from(Category.class);

		query.where(cb.like(from.get("name"), cb.parameter(String.class, "nameParam")));

		return currentSession().createQuery(query).setParameter("nameParam", '%' + name + '%').list();
	}

	public List<Category> findAll() {
		final CriteriaBuilder cb = currentSession().getCriteriaBuilder();
		final CriteriaQuery<Category> query = cb.createQuery(Category.class);
		final Root<Category> from = query.from(Category.class);

		query.orderBy(cb.desc(from.get("id")));

		return list(query);
	}

	public void delete(Category category) {
		currentSession().remove(category);
	}

	public void update(Category category) {
		currentSession().saveOrUpdate(category);
	}

	public Category create(Category category) {
		return persist(category);
	}

}