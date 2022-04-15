package it.polimi.db2.entities;




import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Montlyfee", schema = "database2")
public class MontlyFee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IdMontlyFee")
    private int id;

    @Column(name = "12MonthPrice")
    private int TwelveMonthPrice;

    @Column(name = "24MonthPrice")
    private int TwentyFourMonthPrice;

    @Column(name = "36Monthprice")
    private int ThirtySixMonthPrice;

    @OneToMany(mappedBy = "packageFees")
    private List<ServicePackage> servicePackageList;

    public MontlyFee(){
    }

    public MontlyFee(int id, int Twelve, int TwentyFour, int ThirtySix){
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

    public int getTwelveMonthPrice() {
        return TwelveMonthPrice;
    }

    public void setTwelveMonthPrice(int twelveMonthPrice) {
        TwelveMonthPrice = twelveMonthPrice;
    }

    public int getTwentyFourMonthPrice() {
        return TwentyFourMonthPrice;
    }

    public void setTwentyFourMonthPrice(int twentyFourMonthPrice) {
        TwentyFourMonthPrice = twentyFourMonthPrice;
    }

    public int getThirtySixMonthPrice() {
        return ThirtySixMonthPrice;
    }

    public void setThirtySixMonthPrice(int thirtySixMonthPrice) {
        ThirtySixMonthPrice = thirtySixMonthPrice;
    }
}
