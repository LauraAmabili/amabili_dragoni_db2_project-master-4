use database2;

drop trigger if exists updateTotalPurchasesPerPacket;
drop trigger if exists insertServicePackage;
drop table if exists TotalPurchasesPerPacket;
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




delimiter $$
create trigger insertServicePackage
    after insert on ServicePackage
    for each row
begin
    insert into TotalPurchasesPerPacket(servicePackage,purchases) values(new.PackageName, default);
end $$
delimiter ;
