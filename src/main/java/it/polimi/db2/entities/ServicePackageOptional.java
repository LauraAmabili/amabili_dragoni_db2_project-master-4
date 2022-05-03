package it.polimi.db2.entities;

import it.polimi.db2.entities.keys.ServicePackageOptionalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ServicePackageOptional", schema = "database2")
@IdClass(ServicePackageOptionalId.class)
// query that gets the optional products associated to a given service package
@NamedQuery(name = "ServicePackageOptional.findServicePackageOptionalProducts", query = "SELECT spo.optionalProduct FROM ServicePackageOptional spo WHERE spo.servicePackage = :name")
public class ServicePackageOptional implements Serializable {

    private static final long serialVersionUID = 1L;



    @Id
    @Column(name = "optionalProduct")
    private String optionalProduct;

    @Column(name = "servicePackage")
    private String servicePackage;


    public ServicePackageOptional() {
    }


    public String getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(String optionalProduct) {
        this.optionalProduct = optionalProduct;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }
}
