![LogoIns](src/resources/images/upslogo.png)

# Estructura de Datos

### Integrantes

- **Nombre:** Cristopher Carangui
- **Nombre:** Edwin Pintado
- **Nombre:** Richard Japón

### Correos Institucionales

- ccaranguic@est.ups.edu.ec
- epintador@est.ups.edu.ec
- rjaponl@est.ups.edu.ec

## Objetivo

Desarrollar una aplicación en Java que modele un mapa de calles mediante la estructura de datos de grafos, permitiendo representar
intersecciones como nodos y calles como aristas sobre una imagen de fondo, e implementar los algoritmos de búsqueda BFS y DFS para encontrar y 
visualizar rutas entre dos puntos, aplicando el patrón de arquitectura MVC y mecanismos de persistencia de información.

# Desarrollo

## Marco Teórico

### Grafos

Los grafos son una composición interesante de conjuntos de objetos que denominamos nodos. En ellos se almacena diferentes tipos de elementos o
datos que podemos utilizar para procesar o conocer con fines específicos.
Adicionalmente estos nodos, suelen estar unidos o conectados a otros nodos a través de elementos que denominamos aristas.
Los nodos pertenecientes a un grafo pueden contener datos estructurada o no estructurada y al interrelacionarse con otros nodos producen 
relaciones interesantes que podemos analizar con diferentes finalidades.
Estos elementos son reconocidos por su capacidad de manejar altos volúmenes de datos y ser fácilmente procesados por motores de búsqueda o 
gestores de bases de datos orientados a grafos.

### BFS (Breadth-First Search)

La búsqueda en amplitud (BFS, por sus siglas en inglés) es un algoritmo de recorrido de grafos que comienza en un nodo de origen y explora el 
grafo nivel por nivel. Primero, visita todos los nodos directamente adyacentes al origen. Luego, continúa visitando los nodos adyacentes a 
estos, y este proceso se repite hasta que se hayan visitado todos los nodos alcanzables.

Como su nombre lo indica, BFS requiere que recorra el grafo en amplitud de la siguiente manera:

1. Primero, desplázate horizontalmente y visita todos los nodos de la capa actual.
2. Pasa a la siguiente capa.

### DFS (Depth First Search)

La búsqueda en profundidad (DFS) parte de un vértice de origen y explora un camino hasta la máxima profundidad posible. Al llegar a un vértice sin vecinos sin visitar, retrocede al vértice anterior para explorar otros caminos no visitados. Este proceso continúa hasta que se visitan todos los vértices accesibles desde el origen. 


- Ventajas:

La búsqueda en profundidad en un árbol binario generalmente requiere menos memoria que la búsqueda en amplitud.
La búsqueda en profundidad se puede implementar fácilmente con recursión.

- Desventajas

Una búsqueda en profundidad (DFS) no necesariamente encuentra el camino más corto a un nodo, mientras que una búsqueda en amplitud sí lo hace.

## Arquitectua y estructura de carpetas


```text
ProyectoFinal-EST/
├── .vscode/
├── bin/
├── src/
│   ├── controllers/
│   │   └── MapController.java
│   ├── graphs/
│   │   ├── BFSPathFinder.java
│   │   ├── DFSPathFinder.java
│   │   ├── Graph.java
│   │   ├── PathFinder.java
│   │   └── PathResult.java
│   ├── models/
│   │   ├── MapPoint.java
│   │   └── node/
│   │       └── Node.java
│   ├── persistence/
│   │   ├── FileGraphRepository.java
│   │   └── GraphRepository.java
│   ├── resources/
│   │   ├── images/
│   │   │   └── upslogo.png
│   │   └── maps/
│   │       └── MapaED.png
│   ├── views/
│   │   ├── MainFrame.java
│   │   └── MapPanel.java
│   └── App.java
└── README.md
```

## Explicación general de funcionamiento

---- Completar

## Ejemplo de ejecución

---- Completar

## Tabla comparativa

---- Completar

## Conclusiones

---- Completar

## Recomendaciones

---- Completar

## Fuentes

- https://www.grapheverywhere.com/grafos-que-son-tipos-orden-y-herramientas-de-visualizacion/
- https://www.geeksforgeeks.org/dsa/breadth-first-search-or-bfs-for-a-graph/
- https://www.hackerearth.com/practice/algorithms/graphs/breadth-first-search/tutorial/
- https://www.geeksforgeeks.org/dsa/depth-first-search-or-dfs-for-a-graph/
- https://www.interviewcake.com/concept/java/dfs