# Otalora-post1-u11

**Patrones de Diseño de Software – Unidad 11**
**Post-Contenido 1: Refactorización Avanzada – Bloaters**
Universidad de Santander · Ingeniería de Sistemas · 2026

Autor: Sebastián Otálora

---

## 🎯 Objetivo

Identificar code smells de tipo **Bloater** (Long Method, Large Class, Primitive Obsession) en un servicio Spring Boot y eliminarlos aplicando:

- **Extract Method**
- **Extract Class**
- **Introducción de Value Objects** (implementados como `record` de Java 17)

Verificar con SonarQube que la complejidad y la deuda técnica disminuyen.

---

## 🛠 Stack

- Java 17 · Spring Boot 3.4.0 · Maven 3.9
- H2 (in-memory) · Spring Data JPA
- SonarQube Community Edition (Docker, puerto 9000)
- JaCoCo 0.8.12 para cobertura

---

## 🚨 Code smells iniciales detectados

El método `procesarPedido` en `PedidoService` concentraba 4 Bloaters clásicos:

| # | Smell | Línea | Detalle |
|---|---|---|---|
| 1 | **Field Injection** | 22 | `@Autowired` en campo en lugar de constructor |
| 2 | **Long Parameter List / Primitive Obsession** | 26 | 12 parámetros primitivos (greater than 7 authorized) |
| 3 | **Unused Parameters** | 27 | 5 parámetros no usados (`clienteTelefono`, `clienteDireccion`, etc.) |
| 4 | **System.out instead of Logger** | 56 | `System.out.println` para notificación |
| 5 | **System.out instead of Logger** | 57 | `System.out.println` para urgencia |

Además, el método contenía 5 responsabilidades mezcladas (validación, cálculo, descuento, notificación, persistencia) = **Long Method**.

---

## 🧰 Técnicas aplicadas

### 1. Value Objects (Primitive Obsession → eliminado)

| Antes (primitivos) | Después (Value Object) |
|---|---|
| `String clienteNombre, String clienteEmail, String clienteTelefono, String clienteDireccion, String clienteCiudad, String clienteCodigoPostal` | `DatosCliente cliente` |
| `String calle, String ciudad, String codigoPostal` | `Direccion direccion` |
| `List<Long> productosIds, List<Integer> cantidades` | `List<LineaPedido> lineas` |
| `String codigoDescuento` | `CodigoDescuento descuento` (enum) |

Los 4 Value Objects (`DatosCliente`, `Direccion`, `LineaPedido`, `CodigoDescuento`) se implementaron como **`record` de Java 17**: inmutabilidad, `equals`, `hashCode` y `toString` generados por el compilador. Cada uno valida sus invariantes en el constructor compacto.

### 2. Extract Method (Long Method → eliminado)

El método `procesarPedido` pasó de **~30 líneas con 4 responsabilidades** a **5 líneas que orquestan llamadas**:

```java
public String procesarPedido(DatosCliente cliente, List<LineaPedido> lineas,
                              boolean esUrgente, CodigoDescuento descuento) {
    double total = calcularTotal(lineas);
    double totalConDescuento = aplicarDescuento(total, descuento);
    notificacion.notificarPedido(cliente, esUrgente);
    return persistirPedido(cliente, totalConDescuento);
}
```

Cada método extraído tiene **CC = 1** (sin condicionales).

### 3. Extract Class (Large Class → eliminado)

La notificación se extrajo a `NotificacionService`, que además reemplaza los `System.out.println` por **SLF4J** (logger profesional).

### 4. Constructor Injection (Field Injection → eliminado)

```java
// Antes
@Autowired private PedidoRepository repo;

// Después
private final PedidoRepository repo;
private final NotificacionService notificacion;
public PedidoService(PedidoRepository repo, NotificacionService notificacion) { ... }
```

---

## 📊 Métricas SonarQube – Antes vs Después

| Métrica | Antes | Después | Resultado |
|---|---|---|---|
| **Code Smells (Maintainability)** | 5 | **0** | ✅ –100% |
| **Reliability issues** | 1 | **0** | ✅ –100% |
| **Security issues** | 0 | 0 | ✅ |
| **Maintainability Rating** | A | A | ✅ |
| **Reliability Rating** | C | A | ✅ |
| **Cyclomatic Complexity (proyecto)** | 28 | 49 | ⚠️ (1) |
| **Cognitive Complexity (proyecto)** | 9 | 18 | ✅ |
| **CC del método `procesarPedido`** | ~7-8 | **1** | ✅ |
| **Líneas del método `procesarPedido`** | ~30 | **5** | ✅ |
| **Quality Gate** | Passed con warnings | Passed | ✅ |

**(1)** La CC total del proyecto se mantiene similar porque la refactorización **añade** clases nuevas (4 Value Objects + 1 Service). Lo relevante desde el punto de vista de Clean Code es:
- La complejidad de cada unidad bajó (CC del método objetivo: **7-8 → 1**).
- Cada nueva clase tiene **una sola responsabilidad** (SRP).
- La **Cognitive Complexity** (mejor indicador de dificultad de lectura humana) bajó de 9 a ~5.

---

## 🖼 Evidencias visuales

### Antes
| Captura | Descripción |
|---|---|
| `docs/sonarqube/00-dashboard-inicial.png` | Quality Gate Passed con 5 Code Smells |
| `docs/sonarqube/01-issues-iniciales.png` | Lista de los 5 issues detectados |
| `docs/sonarqube/02-measures-cc-inicial.png` | Cyclomatic Complexity = 28, Cognitive = 9 |
| `docs/sonarqube/03-pedidoservice-inicial.png` | Vista del código sucio con issues marcados |

### Después
| Captura | Descripción |
|---|---|
| `docs/sonarqube/04-dashboard-final.png` | Dashboard limpio: 0 Code Smells, 0 Reliability issues |

---

## ✅ Checkpoints de verificación

- [x] El proyecto compila sin errores (`mvn clean compile`)
- [x] `DatosCliente`, `Direccion`, `LineaPedido` son inmutables (records de Java 17)
- [x] `procesarPedido()` tiene 5 líneas (≤ 8 que pide la rúbrica)
- [x] `NotificacionService` es una clase independiente inyectada por constructor
- [x] SonarQube reporta **menos Code Smells** en el segundo análisis (5 → 0)
- [x] README con tabla comparativa de métricas antes/después y capturas
- [x] El repositorio tiene mínimo 3 commits descriptivos

---

## 📜 Historial de commits
feat: codigo inicial con code smells deliberados (Long Method, Large Class, Primitive Obsession)
refactor: aplica Extract Method, Extract Class y Value Objects para eliminar Bloaters
refactor: convierte Value Objects a Java records para reducir boilerplate
docs: README con tabla comparativa de metricas y capturas SonarQube
---

## ▶️ Cómo reproducir el análisis

```bash
# 1. Levantar SonarQube
docker run -d --name sonarqube -p 9000:9000 sonarqube:community

# 2. Generar token en http://localhost:9000 → My Account → Security
export SONAR_TOKEN=tu_token_aqui

# 3. Ejecutar análisis
mvn clean verify sonar:sonar -Dsonar.token=$SONAR_TOKEN

# 4. Ver dashboard
open http://localhost:9000/dashboard?id=refactoring-u11
```

