package it.polimi.db2.entities.keys;

import java.io.Serializable;
import java.util.Objects;

public class ServicePackageOptionalId implements Serializable {
    private String optionalProduct;
    private String servicePackage;


    public ServicePackageOptionalId(String optionalProduct, String servicePackage) {
        this.optionalProduct = optionalProduct;
        this.servicePackage = servicePackage;
    }

    public ServicePackageOptionalId() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        ServicePackageOptionalId servicePackageOptionalId = (ServicePackageOptionalId) obj;
        return (servicePackageOptionalId.optionalProduct == this.optionalProduct && servicePackageOptionalId.servicePackage == this.servicePackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionalProduct, servicePackage);
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
