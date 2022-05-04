/*
package it.polimi.db2.services;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class OptionalOrderedService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public OptionalOrderedService() { }


    public void addOptionalProductToOrder(String optionalProduct, int order) {
        OptionalOrdered optionalOrdered = new OptionalOrdered();
        optionalOrdered.setOptionalProduct(optionalProduct);
        optionalOrdered.setOrder(order);
        em.persist(optionalOrdered);
    }

    public int numberOfOrderOptionalProduct(int orderId) {
        List<OptionalOrdered> opNum = em.createNamedQuery("OptionalOrder.getOrderOptionalProducts", OptionalOrdered.class).setParameter("orderId", orderId).getResultList();
        return opNum.size();
    }

*/
/*
    public List<Object[]> topOptionalOrdered (){

        List<Object[]> top = em.createNamedQuery("OptionalOrder.TopOptional", Object[].class).getResultList();

        return top;
    }*//*


    */
/*

      Collectors.toMap(
        tuple -> ((Number) tuple.get("year")).intValue(),
        tuple -> ((Number) tuple.get("postCount")).intValue()
    )


Map<Number, Number> postCountByYearMap = (Map<Number, Number>) entityManager
.createQuery(
    "select " +
    "   YEAR(p.createdOn) as year, " +
    "   count(p) as postCount " +
    "from " +
    "   Post p " +
    "group by " +
    "   YEAR(p.createdOn)")
.unwrap(org.hibernate.query.Query.class)
.setResultTransformer(
    new MapResultTransformer<Number, Number>()
)
.getSingleResult();*//*






}
*/
