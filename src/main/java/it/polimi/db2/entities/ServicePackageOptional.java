package it.polimi.db2.entities;

import it.polimi.db2.entities.keys.ServicePackageOptionalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ServicePackageOptionalId.class)
@Table(name = "ServicePackageOptional", schema = "database2")
public class ServicePackageOptional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "optionalProduct")
    private String optionalProduct;

    @Id
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
