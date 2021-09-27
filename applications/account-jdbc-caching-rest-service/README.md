
```sqlite-sql
CREATE TABLE ACCOUNTS (
ACCT_ID  VARCHAR(50)  NOT NULL,
ACCT_NM  VARCHAR(50) NOT NULL,
PRIMARY KEY(ACCT_ID)
);
```


```shell
create region --name=AccountCache --entry-time-to-live-expiration=1000 --enable-statistics=true --type=PARTITION
```