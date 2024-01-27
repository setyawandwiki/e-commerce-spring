add your apllication.properties on resources folder, here's list variables you must set up :
spring.datasource.url= YOUR DATABASE URL
spring.datasource.username= YOUR DATABASE USERNAME
spring.datasource.password= YOUR DATABASE PASSWORD

spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show_sql=true

spring.jpa.hibernate.ddl-auto=update

spring.jackson.serialization.fail-on-empty-beans=false

midtrans.serverKey= YOUR MIDSTRANS SERVER KEY
midtrans.clientKey= YOUR MIDSTRANS CLIENT KEY

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username= YOUR EMAIL
spring.mail.password= YOUR PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
