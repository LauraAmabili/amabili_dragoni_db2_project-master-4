package it.polimi.db2.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ServicePackageOptional", schema = "database2")
@NamedQuery(name = "ServicePackageOptional.findServicePackageOptionalProducts", query = "SELECT spo.optionalProduct FROM ServicePackageOptional spo WHERE spo.servicePackage = :name")
public class ServicePackageOptional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "optionalProduct")
    private String optionalProduct;

    @Column(name = "servicePackage")
    private String servicePackage;




}
