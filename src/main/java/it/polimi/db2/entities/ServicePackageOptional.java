package it.polimi.db2.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ServicePackageOptional", schema = "database2")
public class ServicePackageOptional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "optionalProduct")
    private String optionalProduct;

    @Column(name = "servicePackage")
    private String servicePackage;


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
