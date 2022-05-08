use database2;

drop trigger if exists updateTotalPurchasesPerPacket;
drop trigger if exists updateTotalPurchasesPerPacketValidityPeriod12;
drop trigger if exists updateTotalPurchasesPerPacketValidityPeriod24;
drop trigger if exists updateTotalPurchasesPerPacketValidityPeriod36;
drop trigger if exists updateTotalPurchasesPerPacketTotalPackageSales;
drop trigger if exists updateTotalPackageSales;
drop trigger if exists updateTotalPackageSalesWithOp;
drop trigger if exists updateTotalPackageSalesWithoutOp;
drop trigger if exists updateBestOptional;
drop trigger if exists insertOptionalProduct;
drop trigger if exists insertServicePackage;
drop trigger if exists updateProva;


drop table if exists TotalPurchasesPerPacket;
drop table if exists TotalPurchasesPerPacketValidityPeriod;
drop table if exists TotalPackageSales;
drop table if exists BestOptional;
drop table if exists AveragePackageOptionalProducts;
drop view if exists optionalProductsPerOrder;



/*Number of total purchases per package*/
create table TotalPurchasesPerPacket(
    servicePackage varchar(45) not null,
    purchases int not null default 0,
    foreign key (servicePackage) references ServicePackage(PackageName) on delete cascade on update cascade,
    primary key (servicePackage)
);

create trigger updateTotalPurchasesPerPacket
    after insert on ActivationSchedule
    for each row
    update TotalPurchasesPerPacket
    set purchases = purchases + 1
    where servicePackage = (select sp.PackageName from ServicePackage as sp, Orders as ord
                            where new.orderId = ord.orderId and ord.orderedService = sp.PackageName);


/*Number of total purchases per package and validity period*/
create table TotalPurchasesPerPacketValidityPeriod(
    servicePackage varchar(45) not null,
    purchases12 int not null default 0,
    purchases24 int not null default 0,
    purchases36 int not null default 0,
    foreign key (servicePackage) references ServicePackage(PackageName) on delete cascade on update cascade,
    primary key (servicePackage));

create trigger updateTotalPurchasesPerPacketValidityPeriod12
    after insert on ActivationSchedule
    for each row
    update TotalPurchasesPerPacketValidityPeriod
    set purchases12 = purchases12 + 1
    where servicePackage = (select sp.PackageName from ServicePackage as sp, Orders as ord
                            where new.orderId = ord.orderId and ord.orderedService = sp.PackageName and ord.ValidityPeriodMonth = 12);

create trigger updateTotalPurchasesPerPacketValidityPeriod24
    after insert on ActivationSchedule
    for each row
    update TotalPurchasesPerPacketValidityPeriod
    set purchases24 = purchases24 + 1
    where servicePackage = (select sp.PackageName from ServicePackage as sp, Orders as ord
                            where new.orderId = ord.orderId and ord.orderedService = sp.PackageName and ord.ValidityPeriodMonth = 24);

create trigger updateTotalPurchasesPerPacketValidityPeriod36
    after insert on ActivationSchedule
    for each row
    update TotalPurchasesPerPacketValidityPeriod
    set purchases36 = purchases36 + 1
    where servicePackage = (select sp.PackageName from ServicePackage as sp, Orders as ord
                            where new.orderId = ord.orderId and ord.orderedService = sp.PackageName and ord.ValidityPeriodMonth = 36);


/*Total value of sales per package with and without optional products*/
create table TotalPackageSales(
    servicePackage varchar(45) not null,
    totSalesWithOp decimal(10,2) not null default 0.00,
    totSalesWithoutOP decimal(10,2) not null default 0.00,
    foreign key (servicePackage) references ServicePackage(PackageName) on delete cascade on update cascade,
    primary key (servicePackage));

create trigger updateTotalPackageSalesWithOp
    after insert on ActivationSchedule
    for each row
    update TotalPackageSales
        set totSalesWithOp = totSalesWithOp + (select o.TotalCost from Orders as o where new.orderId = o.orderId)
    where servicePackage = (select o.orderedService from Orders o where o.orderId = new.orderId);

delimiter $$
create trigger updateTotalPackageSalesWithoutOp
    after insert on ActivationSchedule
    for each row
    begin

        IF ( 12 = (select o.ValidityPeriodMonth from Orders as o where new.orderId = o.orderId)) THEN
            update TotalPackageSales
            set totSalesWithoutOP = totSalesWithoutOP + ((Select mf.12MonthPrice from MontlyFee as mf, ServicePackage as sp, Orders as o
                                                         where mf.idMontlyFee = sp.packageFees and new.orderId = o.orderId and o.orderedService = sp.packageName) * 12)
            where servicePackage = (select o.orderedService from Orders o where o.orderId = new.orderId);

        ELSEIF ( 24 = (select o.ValidityPeriodMonth from Orders as o where new.orderId = o.orderId)) THEN
            update TotalPackageSales
            set totSalesWithoutOP = totSalesWithoutOP + ((Select mf.24MonthPrice from MontlyFee as mf, ServicePackage as sp, Orders as o
                                                         where mf.idMontlyFee = sp.packageFees and new.orderId = o.orderId and o.orderedService = sp.packageName)*24)
            where servicePackage = (select o.orderedService from Orders o where o.orderId = new.orderId);

        ELSEIF ( 36 = (select o.ValidityPeriodMonth from Orders as o where new.orderId = o.orderId)) THEN
            update TotalPackageSales
            set totSalesWithoutOP = totSalesWithoutOP + ((Select mf.36MonthPrice from MontlyFee as mf, ServicePackage as sp, Orders as o
                                                         where mf.idMontlyFee = sp.packageFees and new.orderId = o.orderId and o.orderedService = sp.packageName)*36)
            where servicePackage = (select o.orderedService from Orders o where o.orderId = new.orderId);

        END IF;
    end $$

delimiter ;

/*Average number of optional products sold together with each service package*/
create table AveragePackageOptionalProducts
(
    servicePackage varchar(45) not null,
    average decimal(10,2) default 0.00,
    foreign key (servicePackage) references ServicePackage(PackageName) on delete cascade on update cascade,
    primary key (servicePackage)
);

create view optionalProductsPerOrder as (
select actSched.orderId as orderId, sp.PackageName as servicePackage, count(oo.orderId) as numOrdered
from ActivationSchedule as actSched left join OptionalOrdered as oo on actSched.orderId = oo.orderId, Orders as o, ServicePackage as sp
where o.orderId = actSched.orderId and sp.PackageName = o.orderedService
group by actSched.orderId );

create trigger updateOptionalProductsPerOrder
    after insert on ActivationSchedule
    for each row
    update AveragePackageOptionalProducts
    set average = (SELECT avg(oppo.numOrdered) from optionalProductsPerOrder oppo,Orders as o
        where new.orderId = o.orderId and oppo.servicePackage = o.orderedService)
    where servicePackage = (SELECT sp.PackageName from ServicePackage as sp, Orders as o
        WHERE new.orderId = o.orderId and o.orderedService = sp.PackageName);

/*best seller optional products*/
/*in the table are not ordered*/
create table BestOptional(optionalProduct varchar(45) not null,
                          pricePerMonth decimal(10,2) not null default 0.00,
                          totSales decimal(10,2) not null default 0.00,
                          foreign key (optionalProduct) references OptionalProduct(name) on delete cascade on update cascade,
                          primary key (optionalProduct));

delimiter $$
create trigger updateBestOptional
    after insert on ActivationSchedule
    for each row
    update BestOptional
    set totSales = totSales + (select o.ValidityPeriodMonth from Orders as o where new.orderId = o.orderId ) * pricePerMonth
    where optionalProduct in (select oo.optionalProductId from ServicePackage as sp, Orders as ord, OptionalOrdered as oo
                                where new.orderId = ord.orderId and ord.orderId = oo.orderId);

delimiter ;

delimiter $$
create trigger insertServicePackage
    after insert on ServicePackage
    for each row
begin
    insert into TotalPurchasesPerPacket(servicePackage,purchases) values(new.PackageName, default);
    insert into TotalPurchasesPerPacketValidityPeriod(servicePackage,purchases12, purchases24, purchases36) values(new.PackageName, default, default, default);
    insert into TotalPackageSales(servicePackage, totSalesWithOp, totSalesWithoutOP) values(new.PackageName, default, default);
    insert into AveragePackageOptionalProducts(servicePackage, average) values(new.PackageName, default);
end $$
delimiter ;


delimiter $$
create trigger insertOptionalProduct
    after insert on OptionalProduct
    for each row
begin
    insert into BestOptional(optionalProduct, pricePerMonth, totSales) values(new.name, new.montlyFee, default);
end $$
delimiter ;
