package it.polimi.db2.entities.keys;

import java.io.Serializable;
import java.util.Objects;

public class PkgServicePhoneId implements Serializable {

    String servicePackage;
    String mobilePhone;


    public PkgServicePhoneId(String servicePackage, String mobilePhone) {
        this.servicePackage = servicePackage;
        this.mobilePhone = mobilePhone;
    }

    public PkgServicePhoneId() {
    }

    @Override
    public boolean equals(Object obj) {
        // checking if both the object references are
        // referring to the same object.
        if (this == obj)
            return true;
        // it checks if the argument is of the
        // type Geek by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        // type casting of the argument.
        PkgServicePhoneId pkgServicePhoneId = (PkgServicePhoneId) obj;

        // comparing the state of argument with
        // the state of 'this' Object.
        return (pkgServicePhoneId.servicePackage == this.servicePackage && pkgServicePhoneId.mobilePhone == this.mobilePhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(servicePackage, mobilePhone);
    }


    public String getservicePackage() {
        return servicePackage;
    }

    public void setservicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}

