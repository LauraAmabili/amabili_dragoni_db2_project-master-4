use database2;

drop trigger if exists updateTotalPurchasesPerPacket;
drop trigger if exists updateTotalPurchasesPerPacketValidityPeriod12;
drop trigger if exists updateTotalPurchasesPerPacketValidityPeriod24;
drop trigger if exists updateTotalPurchasesPerPacketValidityPeriod36;
drop trigger if exists updateTotalPurchasesPerPacketTotalPackageSales;
drop trigger if exists insertServicePackage;
drop trigger if exists updateTotalPackageSales;
drop trigger if exists updateTotalPackageSalesWithOp;
drop trigger if exists updateTotalPackageSalesWithoutOp12;

drop table if exists TotalPurchasesPerPacket;
drop table if exists TotalPurchasesPerPacketValidityPeriod;
drop table if exists TotalPackageSales;



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
        set totSalesWithOp = totSalesWithOp + (select o.TotalCost from Orders as o where new.orderId = o.orderId);

delimiter $$
create trigger updateTotalPackageSalesWithoutOp12
    after insert on ActivationSchedule
    for each row
    begin

        IF ( 12 = (select o.ValidityPeriodMonth from Orders as o where new.orderId = o.orderId)) THEN
            update TotalPackageSales
            set totSalesWithoutOP = totSalesWithoutOP + ((Select mf.12MonthPrice from MontlyFee as mf, ServicePackage as sp, Orders as o
                                                         where mf.idMontlyFee = sp.packageFees and new.orderId = o.orderId and o.orderedService = sp.packageName) * 12);

        ELSEIF ( 24 = (select o.ValidityPeriodMonth from Orders as o where new.orderId = o.orderId)) THEN
            update TotalPackageSales
            set totSalesWithoutOP = totSalesWithoutOP + ((Select mf.24MonthPrice from MontlyFee as mf, ServicePackage as sp, Orders as o
                                                         where mf.idMontlyFee = sp.packageFees and new.orderId = o.orderId and o.orderedService = sp.packageName)*24);

        ELSEIF ( 36 = (select o.ValidityPeriodMonth from Orders as o where new.orderId = o.orderId)) THEN
            update TotalPackageSales
            set totSalesWithoutOP = totSalesWithoutOP + ((Select mf.36MonthPrice from MontlyFee as mf, ServicePackage as sp, Orders as o
                                                         where mf.idMontlyFee = sp.packageFees and new.orderId = o.orderId and o.orderedService = sp.packageName)*36);
        END IF;
    end $$

delimiter ;



delimiter $$
create trigger insertServicePackage
    after insert on ServicePackage
    for each row
    begin
    insert into TotalPurchasesPerPacket(servicePackage,purchases) values(new.PackageName, default);
    insert into TotalPurchasesPerPacketValidityPeriod(servicePackage,purchases12, purchases24, purchases36) values(new.PackageName, default, default, default);
    insert into TotalPackageSales(servicePackage, totSalesWithOp, totSalesWithoutOP) values(new.PackageName, default, default);
end $$
delimiter ;
