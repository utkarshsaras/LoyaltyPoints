## Assumptions for Loyalty Points Management
1. Customers already exists in the system before purchases.
2. Redemption cannot exceed the current balance.
3. The system assumes all requests are authenticated.
4. Event store is append only.

## Questions 
1. Should support multiple currencies?
2. Can customer update account levels?
3. Should old events be archived or deleted after a certain period?
4. Any rate limit on puchases per customer?

## Limitations
1. As current balance is calculated from transaction history , it could become slow as histories become large.
2. No authentication and authorization implemented
3. In memory db used
4. Events not deleting from db after certain time.
