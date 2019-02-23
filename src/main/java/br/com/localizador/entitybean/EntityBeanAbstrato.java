package br.com.localizador.entitybean;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import br.com.localizador.util.UtilBeans;

public class EntityBeanAbstrato<T> {
	
	@PersistenceContext(unitName="LocalizadorDS")  
	private EntityManager manager;
	
	/**
	 * Retorna o(a) manager.
	 * 
	 * @return EntityManager
	 */
	protected EntityManager getManager() {
		return this.manager;
	}
	/**
	 * Retorna o(a) Criteria Builder.
	 * 
	 * @return EntityManager
	 */
	protected CriteriaBuilder getCriteriaBuilder() {
		return this.manager.getCriteriaBuilder();
	}

	/**
	 * Atribui o(a) manager.
	 * 
	 * @param manager
	 *            EntityManager
	 */
	protected final void setManager(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * {@inheritDoc}
	 */
	public void alterar(T entity) throws Exception {
		this.getManager().merge(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void excluir(T entity) throws Exception {
		try {
			Class entityType = this.getEntityType();
			Query query =
					this.getManager().createQuery(
							"delete from " + entityType.getName()
									+ " vo where " + "vo." + this.getIdName(entity)
									+ " = :param");

			query.setParameter("param", this.getIdValue(entity));
			query.executeUpdate();
		} catch (EntityExistsException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void incluir(T entity) throws Exception {
		try {
			this.getManager().persist(entity);
		} catch (Exception e) {
			if (e instanceof java.sql.BatchUpdateException) {
				((java.sql.BatchUpdateException) e).getNextException()
						.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> listar() {
		Class entityType = this.getEntityType();
		Annotation[] annotations = entityType.getAnnotations();
		String namedQuery = null;
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(NamedQuery.class)) {
				NamedQuery named = (NamedQuery) annotation;
				namedQuery = named.name();
				break;
			}
		}
		Query query = null;
		if (namedQuery != null) {
			query = this.getManager().createNamedQuery(namedQuery);
		} else {
			query =
					this.getManager().createQuery(
							"FROM ".concat(entityType.getName()));
		}
		return query.getResultList();
	}

	/**
	 * Retorna o Tipo da Parametrizacao da concretizacao do Bean
	 * 
	 * @return clazz Class
	 */
	@SuppressWarnings("unchecked")
	private Class getEntityType() {
		ParameterizedType parameterizedType =
				(ParameterizedType) this.getClass().getGenericSuperclass();
		Class entityType =
				(Class) parameterizedType.getActualTypeArguments()[0];
		return entityType;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T recuperar(Serializable id) throws Exception{
		T r = (T) this.getManager().find(this.getEntityType(), id);
		if(r == null){
//			throw new BusinessException(ConstantesMensagem.NENHUM_REGISTRO_ENCONTRADO);
		}
		return r;
	}

	/**
	 * Retorna uma Lista de entidades de acordo com a query de pesquisa.
	 * 
	 * @param query
	 *            Query de pesquisa
	 * @param paramNames
	 *            Nomes dos parametros da pesquisa
	 * @param paramValues
	 *            Valores que cada parametro recebera
	 * @return list Lista de Entidades
	 */
	@SuppressWarnings("unchecked")
	protected List<T> pesquisarEntidadesPorQuery(String query,
			String[] paramNames, Object[] paramValues) {
		Query queryMananger = this.getManager().createQuery(query);
		try {
			for (int i = 0; i < paramNames.length; i++) {
				queryMananger.setParameter(paramNames[i], paramValues[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
					"Numero de parametros diferentes dos valores");
		}
		return queryMananger.getResultList();
	}

	/**
	 * Retorna uma Entidade de acordo com a query de pesquisa.
	 * 
	 * @param query
	 *            Query de pesquisa
	 * @param paramNames
	 *            Nomes dos parametros da pesquisa
	 * @param paramValues
	 *            Valores que cada parametro recebera
	 * @return list Lista de Entidades
	 * @throws Exception
	 *             e
	 */
	@SuppressWarnings("unchecked")
	protected T recuperarEntidadePorQuery(String query, String[] paramNames,
			Object[] paramValues) throws Exception {
		Query queryMananger = this.getManager().createQuery(query);
		T result = null;
		try {
			for (int i = 0; i < paramNames.length; i++) {
				queryMananger.setParameter(paramNames[i], paramValues[i]);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
					"Numero de parametros diferentes dos valores");
		}
		try {
			result = (T)queryMananger.getSingleResult();
		} catch (NoResultException e) {
//			this.logger.error("Nenhum registro encontrado para a entidade");
		}
		return result;
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@SuppressWarnings("unchecked")
//	public final List<T> pesquisarPorCriterioExemplo(T entidade) {
//		Example example = Example.create(entidade);
//		example.excludeZeroes();
//		example.enableLike(MatchMode.ANYWHERE);
//		example.ignoreCase();
//		Criteria criteria =
//			this.getSession().createCriteria(entidade.getClass()).add(example);
//		return criteria.list();
//	}
//	/**
//	 * {@inheritDoc}
//	 */
//	@SuppressWarnings("unchecked")
//	public  List<T> pesquisarPorCriterioExemplo(T entidade,String... excludeFields)throws Exception {
//		Example example = Example.create(entidade);
//		example.excludeZeroes();
//		example.enableLike(MatchMode.ANYWHERE);
//		for(String field:excludeFields){
//			example.excludeProperty(field);
//		}
//		example.ignoreCase();
//		Criteria criteria =
//				this.getSession().createCriteria(entidade.getClass()).add(example);
//		return criteria.list();
//	}
	/**
	 * {@inheritDoc}
	 */
//	@SuppressWarnings("unchecked")
//	public final List<T> pesquisarPorCriterioExemplo(T entidade,
//			EntityBeanLoclatorAbstractLocal.ResultOrder resultOrder, String... orderFields){
//		Example example = Example.create(entidade);
//		example.excludeZeroes();
//		example.enableLike(MatchMode.ANYWHERE);
//		example.ignoreCase();
//		
//		Criteria criteria =
//			this.getSession().createCriteria(entidade.getClass()).add(example);
//		
//		if ((orderFields!=null) && (orderFields.length > 0)){
//			for (String string : orderFields) {
//			switch (resultOrder) {
//			case ASC:
//				criteria.addOrder(Order.asc(string));
//				break;
//			default:
//				criteria.addOrder(Order.desc(string));
//				break;
//			}
//				
//			}
//		}
//
//		
//		return criteria.list();
//	};

	/**
	 * Retorna o id do objeto parametrizado
	 * 
	 * @param entity
	 *            Entidade que contem o id
	 * @return id Object
	 */
	private Map<String, Object> getId(Object entity) {
		Map<String, Object> mapa = new HashMap<String, Object>();

		try {
			Set<Field> fields = UtilBeans.getAllFields(entity.getClass());
			GOTO: for (Field field : fields) {
				Annotation[] annotationsFields = field.getAnnotations();
				for (Annotation annotation : annotationsFields) {
					if (annotation.annotationType().equals(Id.class)
							|| annotation.annotationType().equals(
									EmbeddedId.class)) {
						field.setAccessible(true);
						mapa.put(field.getName(), field.get(entity));
						break GOTO;
					}
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Nao foi possivel encontrar o Id da Entidade "
							.concat(entity.getClass().getName()));
		}

		return mapa;
	}

	/**
	 * Retorna o Nome da Propriedade que representa a chave primaria da Entidade
	 * 
	 * @return string Nome do campo Id
	 */
	private String getIdName(T entity) {
		final Map<String, Object> id = this.getId(entity);
		return id.keySet().iterator().next();
	}

	private Object getIdValue(T entity) {
		return this.getId(entity).get(this.getIdName(entity));
	}

	/**
	 * {@inheritDoc}
	 */
	public void removerListaEntidades(Collection<T> lista) throws Exception {
		if ((lista == null) || (lista.size() < 1)) {
			return;
		}
		StringBuilder jql = new StringBuilder("DELETE FROM ");
		jql.append(this.getEntityType().getName());
		jql.append(" entity ");
		jql.append(" WHERE ");
		jql.append(" entity ");
		// jql.append(getIdName(lista.iterator().next()));
		jql.append(" IN (:list) ");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("list", lista);
		query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	public void inserirListaEntidades(Collection<T> lista) throws Exception {
		if ((lista == null) || (lista.size() < 1)) {
			return;
		}
		for (T entity : lista) {
			this.incluir(entity);
		}
	}

}