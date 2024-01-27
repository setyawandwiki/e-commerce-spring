# Your Application Name

Brief description of your application.

## Configuration

### Database Configuration

Create an `application.properties` file in the `src/main/resources` folder with the following content:

```properties
# Database Configuration
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

# Hibernate Configuration
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show_sql=true
spring.jpa.hibernate.ddl-auto=update

# Jackson Configuration
spring.jackson.serialization.fail-on-empty-beans=false

# Midtrans Configuration
midtrans.serverKey=YOUR_MIDTRANS_SERVER_KEY
midtrans.clientKey=YOUR_MIDTRANS_CLIENT_KEY

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
