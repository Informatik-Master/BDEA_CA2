<br />
<div align="center">

  <h2 align="center">BDEA CA2</h2>

</div>


Dieses Projekt ist im Rahmen der Vorlesung BDEA an der Hochschule Mannheim entstanden. Die Anwendung basiert auf einer Lambda-Architektur, mit der Word-Clouds mithilfe von Apache Spark anhand der Term-Frequenz generiert werden können. Darüber hinaus bietet die Anwendung die Berechnung der Dokumentenfrequenz und den TF-IDF.

Die Anwendung kann mit dem folgenden Befehl gestartet werden und ist anschließend unter http://localhost erreichbar.
```bash
docker-compose up
```

> Docker verwendet die Maven-Distribution aus diesem Repository. Damit die Container bauen können, muss die mvnw-Datei Linux-Zeilenumbrüche haben (LF). Je nach Git-Einstellunge werden diese womöglich bei einem clone überschrieben.

