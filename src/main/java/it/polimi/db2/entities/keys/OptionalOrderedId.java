package it.polimi.db2.entities.keys;

import java.io.Serializable;
import java.util.Objects;

public class OptionalOrderedId implements Serializable {
    String optionalProduct;
    int order;

    public OptionalOrderedId() {
    }

    public OptionalOrderedId(String optionalProduct, int order) {
        this.optionalProduct = optionalProduct;
        this.order = order;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        OptionalOrderedId optionalOrderedId = (OptionalOrderedId) obj;
        return (optionalOrderedId.optionalProduct == this.optionalProduct && optionalOrderedId.order == this.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionalProduct, order);
    }


    public String getOptionalProduct() {
        return optionalProduct;
    }

    public void setOptionalProduct(String optionalProduct) {
        this.optionalProduct = optionalProduct;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
