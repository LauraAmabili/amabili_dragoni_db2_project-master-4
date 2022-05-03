package it.polimi.db2.services;

import it.polimi.db2.entities.OptionalProduct;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OptionalProductService {

    @PersistenceContext( unitName = "databaseEJB" )
    private EntityManager em;

    public OptionalProductService() { }


    public void addNewOptionalProduct(String name, float monthlyFee){
        OptionalProduct newOp = new OptionalProduct();
        newOp.setName(name);
        newOp.setMonthlyFee(monthlyFee);
        em.persist(newOp);
    }

    public Boolean optionalProductAlreadyExists(String name){
        List<OptionalProduct> optionalProducts = em.createNamedQuery("OptionalProduct.findOptionalProductById", OptionalProduct.class).setParameter(1, name).getResultList();
        if (optionalProducts.isEmpty()) return false;
        else return true;
    }

    public List<OptionalProduct> getAllOptionalProducts(){
        List<OptionalProduct> optionalProducts = em.createNamedQuery("OptionalProduct.getAllOptionalProducts", OptionalProduct.class).getResultList();
        return optionalProducts;
    }


    public OptionalProduct getOptionalProductById(String name){
        List<OptionalProduct> optionalProduct = em.createNamedQuery("OptionalProduct.findOptionalProductById", OptionalProduct.class).setParameter(1, name).getResultList();
        return optionalProduct.get(0);
    }


}
