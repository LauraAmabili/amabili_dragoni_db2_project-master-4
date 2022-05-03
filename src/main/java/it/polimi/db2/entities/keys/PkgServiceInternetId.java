package it.polimi.db2.entities.keys;

import java.io.Serializable;
import java.util.Objects;

public class PkgServiceInternetId implements Serializable {
    String packageService;
    String internetService;

    public PkgServiceInternetId(String packageService, String internetService) {
        this.packageService = packageService;
        this.internetService = internetService;
    }

    public PkgServiceInternetId() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        PkgServiceInternetId pkgServiceInternetId = (PkgServiceInternetId) obj;
        return (pkgServiceInternetId.packageService == this.packageService && pkgServiceInternetId.internetService == this.internetService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageService, internetService);
    }


    public String getPackageService() {
        return packageService;
    }

    public void setPackageService(String packageService) {
        this.packageService = packageService;
    }

    public String getInternetService() {
        return internetService;
    }

    public void setInternetService(String internetService) {
        this.internetService = internetService;
    }
}
