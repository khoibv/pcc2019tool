# NEV tools
![UI](screenshots/Screenshot1.png?raw=true)

### Development
- Run từ class vn.nev.tools.pcctool.PcctoolApplication

- Hoặc build và start từ command line:
```
$ mvn spring-boot:run -Dmaven.test.skip=true
```
- Mở [browser](http://localhost:8080)

### Production
- Chỉnh sửa setting ở application.yml và log-back.xml

- Build:
```
$ mvn package -Dmaven.test.skip=true -Pproduction
```

- Copy pcctool.war vào thư mục webapps của Tomcat -> start Tomcat -> mở [browser](http://localhost:8080/pcctool)

