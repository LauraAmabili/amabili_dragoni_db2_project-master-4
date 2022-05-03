package it.polimi.db2.entities;




import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Montlyfee", schema = "database2")
@NamedQuery (name = "MonthlyFee.findMonthlyFeeId",
        query = "SELECT m FROM MonthlyFee m WHERE m.TwelveMonthPrice = :price12 and m.TwentyFourMonthPrice = :price24 and m.ThirtySixMonthPrice = :price36")

public class MonthlyFee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IdMontlyFee")
    private int id;

    @Column(name = "12MonthPrice")
    private float TwelveMonthPrice;

    @Column(name = "24MonthPrice")
    private float TwentyFourMonthPrice;

    @Column(name = "36Monthprice")
    private float ThirtySixMonthPrice;

    @OneToMany(mappedBy = "packageFees")
    private List<ServicePackage> servicePackageList;

    public MonthlyFee() {
    }

    public MonthlyFee(int id, int Twelve, int TwentyFour, int ThirtySix) {
        this.id = id;
        this.TwelveMonthPrice = Twelve;
        this.TwentyFourMonthPrice = TwentyFour;
        this.ThirtySixMonthPrice = ThirtySix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTwelveMonthPrice() {
        return TwelveMonthPrice;
    }

    public void setTwelveMonthPrice(float twelveMonthPrice) {
        TwelveMonthPrice = twelveMonthPrice;
    }

    public float getTwentyFourMonthPrice() {
        return TwentyFourMonthPrice;
    }

    public void setTwentyFourMonthPrice(float twentyFourMonthPrice) {
        TwentyFourMonthPrice = twentyFourMonthPrice;
    }

    public float getThirtySixMonthPrice() {
        return ThirtySixMonthPrice;
    }

    public void setThirtySixMonthPrice(float thirtySixMonthPrice) {
        ThirtySixMonthPrice = thirtySixMonthPrice;
    }

    public List<ServicePackage> getServicePackageList() {
        return servicePackageList;
    }

    public void setServicePackageList(List<ServicePackage> servicePackageList) {
        this.servicePackageList = servicePackageList;
    }
}
