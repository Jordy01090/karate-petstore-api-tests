# 🐾 Proyecto PetStore - Pruebas Automatizadas con Karate

## 📖 Descripción General
Este proyecto automatiza las pruebas sobre la API pública **Swagger PetStore** utilizando **Karate** y **JUnit 5**. 
Se validan las operaciones CRUD básicas del endpoint `/pet` y se genera un **reporte HTML** con la librería **Masterthought**.

### Flujo probado
1. Añadir una mascota (POST /pet)
2. Consultar mascota por ID (GET /pet/{id})
3. Actualizar nombre y estado a “sold” (PUT /pet)
4. Consultar mascota por estado (GET /pet/findByStatus)

## 🧱 Estructura
```
petstore-karate/
├─ pom.xml
├─ src/test/java/runner/
│  ├─ PetStoreRunner.java
│  └─ ManagementTest.java
├─ src/test/resources/features/petstore.feature
└─ target/
   ├─ karate-reports/
   └─ cucumber-html-reports/
```

## ⚙️ Requisitos
- Java JDK 17+
- Apache Maven 3.8+
- Conexión a Internet

Verifica:
```bash
java -version
mvn -version
```

## ▶️ Ejecución paso a paso

### Opción 1 — Desde IDE
1. Abre el proyecto como Maven Project.
2. Ejecuta la clase `ManagementTest.java` → método `testParallel()`.

### Opción 2 — Desde terminal
```bash
mvn clean test
```

Karate ejecutará las pruebas y generará los reportes automáticamente.

## 📊 Reporte HTML
El reporte se crea en:
```
target/cucumber-html-reports/feature-overview.html
```
Ábrelo en tu navegador para ver resultados.

## 💡 Notas técnicas
- `outputCucumberJson(true)` fuerza la creación de JSON para el reporte.
- `ManagementTest` usa `ReportBuilder` para generar el HTML consolidado.
- Los reintentos en los escenarios ayudan a manejar latencia de la API pública.

## 🚨 Errores comunes
| Error | Causa | Solución |
|-------|--------|----------|
| `not found: classpath:features/petstore.feature` | Ruta incorrecta | Asegura que esté en `src/test/resources/features` |
| `No JSON report file was found!` | JSON no generado | Usa `.outputCucumberJson(true)` |
| `too many retry attempts` | API lenta | Aumenta tiempo en el step `retry until` |

**Autor:** Jordy Ávila  
**Tecnologías:** Karate 1.4.1 · JUnit 5 · Java 17 · Maven 3.8+  
**Fecha:** Octubre 2025
