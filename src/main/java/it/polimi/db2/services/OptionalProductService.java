package it.polimi.db2.services;

import it.polimi.db2.entities.OptionalProduct;
import it.polimi.db2.entities.ServicePackage;
import it.polimi.db2.entities.ServicePackageOptional;
import it.polimi.db2.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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


    public void addNewPkgOptionalProduct(String pkgName, String optionalProdName) {
        ServicePackageOptional newSpo = new ServicePackageOptional();
        newSpo.setOptionalProduct(optionalProdName);
        newSpo.setServicePackage(pkgName);
        em.persist(newSpo);
    }

    public List<String> showServicePackageOptionalProducts(ServicePackage servicePackage) throws CredentialsException, NonUniqueResultException {
        List<String> uList;
        try {
            uList = em.createNamedQuery("ServicePackageOptional.findServicePackageOptionalProducts", String.class).setParameter("name",servicePackage.getPackageName()).getResultList();
        } catch (PersistenceException var5) {
            throw new CredentialsException("Optional Products Error");
        }
        if (uList.isEmpty()) {
            return null;
        } else {
            return uList;
        }
    }

}
