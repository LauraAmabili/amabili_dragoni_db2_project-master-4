package it.polimi.db2.services;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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


    public List<String> showServicePackageOptionalProducts(ServicePackage servicePackage) throws CredentialsException, NonUniqueResultException {
        List<String> optionalProductsIds = new ArrayList<>();
        List<OptionalProduct>optionalProducts = servicePackage.getOptionalProducts();
        if(optionalProducts != null) {
            optionalProducts.forEach(op -> optionalProductsIds.add(op.getName()));
        }
        return optionalProductsIds;
    }

    public float totAmountOptionalProduct(List<OptionalProduct> optionalProducts){
        float total = 0;
        for(OptionalProduct product : optionalProducts){
            total = total + product.getMonthlyFee();
        }
        return total;
    }



}
