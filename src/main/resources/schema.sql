create table if not exists citizens
(
    id        IDEntity    not null primary key,
    firstname varchar(50) not null,
    lastname  varchar(50) not null,
    birthday  date        not null,
    ssn       varchar(50) not null,
    phone     varchar(50) not null,
    email     varchar(50) not null,
    city      varchar(50) not null,
    street    varchar(50) not null
);

/*private String firstname;
private String lastname;
private LocalDate birthday;
private String ssn;
private String phone;
private String email;
private String city;
private String street;*/