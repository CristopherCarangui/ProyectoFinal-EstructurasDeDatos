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

## Descripcion del problema

En el mundo actual la busqueda de rutas mediante aplicaciones como Google Maps es una tarea diaria que sin una buena implementacion o estructura
dentro de estas aplicaciones resultaria ineficiente o imposible de determinar un recorrido util entre dos puntos, en especial si existen muchos 
caminos o intersecciones con multiples direcciones.

El proyecto busca solucionar o plantear la solicion de este problema mediante la representacion de un mapa con calles y puntos donde cada punto 
corresponde a un nodo y cada calle un arista o conexión, usando estos puntos se implementan algoritmos de busqueda por anchura(BFS) o 
profundidad(DFS) permitiendo encontrar rutas entre los puntos que seleccione el usuario.

Este proyecto, además de calcular la ruta, permite visualizar el proceso de exploracion de cada algoritmo de busqueda, y anima el recorrido final encontrado para una mejor comprension, permitiendo comparar ambos algoritmos y a su vez permite guardar operaciones anteriores como
nodos y conexiones en archivos de texto para un uso posterior en caso de necesitarlo.

# Desarrollo

## Marco Teórico

### Grafos

Los grafos son una composición de conjuntos de objetos que denominamos nodos. En ellos se almacena diferentes tipos de elementos o
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

La búsqueda en profundidad (DFS) parte de un vértice de origen y explora un camino hasta la máxima profundidad posible. Al llegar a un vértice 
sin vecinos sin visitar, retrocede al vértice anterior para explorar otros caminos no visitados. Este proceso continúa hasta que se visitan 
todos los vértices accesibles desde el origen. 

- Ventajas:

La búsqueda en profundidad en un árbol binario generalmente requiere menos memoria que la búsqueda en amplitud.
La búsqueda en profundidad se puede implementar fácilmente con recursión.

- Desventajas

Una búsqueda en profundidad (DFS) no necesariamente encuentra el camino más corto a un nodo, mientras que una búsqueda en amplitud sí lo hace.

## Tecnologías utilizadas

**Instrumentos**
- Laptops
- GitHub
- Visual Studio Code
- Google
- Youtube

**Tecnologías**

- Java
- Java Swing
- Arquitectura MVC
- LinkedHashSet
- LinkedHashMap
- Queue
- LinkedList
- Stack
- Graphs
- BFS
- DFS
- Persistencia de datos (Archivos txt)

## Diagrama UML

![alt text](src/resources/images/uml.jpeg)

## Arquitectua y estructura de carpetas

```text
ProyectoFinal-EST/
├── .vscode/
├── bin/
├── src/
│   ├── controllers/
│   │   └── MapController.java
│   ├── graphs/    
│   │   ├── Graph.java
│   │   ├── PathFinder.java
│   │   ├── PathResult.java
│   │   └── implementations/
│   │       ├── BFSPathFinder.java
│   │       └── DFSPathFinder.java 
│   ├── models/
│   │   ├── MapPoint.java
│   │   ├── VisualizationMode.java
│   │   └── node/
│   │       └── Node.java
│   ├── persistence/
│   │   ├── FileGraphRepository.java
│   │   └── GraphRepository.java
│   ├── resources/
│   │   ├── images/
│   │   │   └── upslogo.png
│   │   └── maps/
│   │       └── MapaBarcelona.png
│   ├── views/
│   │   ├── MainFrame.java
│   │   └── MapPanel.java
│   └── App.java
└── README.md
```

## Explicación general de funcionamiento

El proyecto simula un mapa de calles mediante grafos usando Java Swing, donde el usuario dibuja intersecciones y calles a mano sobre una imagen 
de fondo y selecciona un punto de partida y uno final, para luego buscar rutas usando BFS o DFS. 

El proyecto se estrcutura de la siguente manea, siguiendo la arquitectura Modelo-Vista-Controlador (MVC) que separa la logica interna de la interfaz gráfica y el control de eventos:

**Modelo**

Es el que contiene las clases que representan la informacion del mapa y la estructura de datos utilizada.

- node.Node : Es un parametro del grafo que encapsula datos genericos y es utilizado por la clase Graph para almacenar los elementos del mapa.

- `graphs.Graph<T>` : Un `Map<Node<T>`, `Set<Node<T>>>` (lista de adyacencia). Soporta aristas dirigidas (addEdgeUni) y
bidireccionales. Tambien permite operaciones como agregar y eliminar nodos,ademas de crear y consultar las conexiones entre los puntos del 
mapa.  

- models.MapPoint : un punto del mapa con id y coordenadas (x, y); es el tipo T que llena el grafo.

**Algoritmos de búsqueda**

La busqueda se implementa mediante una interfaz en la cual puede seleccionar dos algoritmos de busqueda diferentes.

- PathFinder<T> es una interfaz con un método find(Graph<T>, start, end).

- BFSPathFinder usa una cola para recorrer el grafo y un mapa de predecesores para reconstruir el camino encontrado.

- DFSPathFinder es recursivo, explora en profundidad y hace backtracking si un camino no llega al destino.

Ambos devuelven un PathResult con dos cosas: la lista de nodos visitados en orden (para animar la exploración) y el camino final encontrado.

**Persistencia**

Se implementa mediante la interfaz GraphRepository y su implementacion FileGraphRepository, se encarga de guardar y cargar un mapa desde un
archivo de texto, este archivo almacena los nodos y conexiones para cargar el grafo guardado una vez terminada la ejecucion del programa.

- FileGraphRepository guarda/carga el grafo en un archivo de texto plano con un formato simple: líneas NODE;id;x;y y EDGE;origen;destino;
bidireccional.

**Vista**

- MapPanel dibuja la imagen del mapa, los nodos, las flechas de conexión y anima la búsqueda con un javax.swing.Timer (pinta los nodos visitados uno a uno y luego "recorre" la ruta final con un punto naranja).

- MainFrame arma la ventana: el panel del mapa, el panel de botones (crear nodo, conectar, marcar inicio/fin, ejecutar BFS/DFS, guardar/cargar)
y el panel de resultados en texto.

**Controlador**

- MapController : Registra los listeners de todos los botones, decide en qué "modo" está el panel (crear, conectar, eliminar, 
marcar inicio/fin...) y, cuando el usuario hace clic sobre el mapa, interpreta ese clic según el modo activo. También dispara BFSPathFinder/
DFSPathFinder, mide el tiempo de ejecución con System.nanoTime(), y muestra el resultado.

## Configuraciones de diferentes mapas

BFS

![alt text](src/resources/images/bfs.jpeg)

DFS

![alt text](src/resources/images/dfs.jpeg)

## Ejemplo comentado y explicado

![alt text](src/resources/images/ejemploBFS.jpeg)

Busqueda Usada: BFS

Punto inicial: P9

Punto final: P47 

**Salida**

```text
Algoritmo: BFS

Tiempo: 0,242 ms

Visitados: 44

Orden visitados: [P9, P8, P2, P10, P3, P1, P13, P15, P4, P11, P12, P14, P18, P16, P5, P7, P19, P35, P34, P17, P6, P23, P20, P33, P37, P22, P32, P36, P42, P40, P25, P31, P44, P46, P43, P39, P41, P38, P30, P21, P45, P26, P24, P47]

Ruta encontrada (Longitud=10): [P9, P10, P15, P18, P19, P20, P32, P44, P45, P47]
```
Funcionamiento: Como utilizamos el algoritmo BFS, empezamos desde el punto P9(color verde), este algoritmo va a visitar todos los vecinos de P9

P9 entra a la cola y se marca como visitado. Es el único nodo del "nivel 0": BFS todavía no lo compara con el destino, solo lo registra.
Al sacar P9 de la cola, BFS revisa las calles que salen de él y encola a sus vecinos inmediatos, marcándolos como visitados y guardando a P9 
como su punto de partida para poder reconstruir el camino después.

El bucle se repite: saca el siguiente nodo de la cola, lo agrega a la lista de "visitados", y encola sus vecinos no visitados aún. Su 
comportamiento es como una onda la cual se expande de forma uniforme desde el origen, visitando cada nodo por niveles.

En algún momento P47 sale de la cola. La condición current.equals(end) se cumple de inmediato y el algoritmo se detiene, sin seguir 
vaciando el resto de la cola.

Usando recursividad dentro del mapa guardado en cada paso, BFS retrocede desde P47 hasta llegar a P9 (punto de partida), y luego invierte esa lista para obtener el camino en el orden correcto: de inicio a fin.

## Tabla comparativa

```text
| Caso | Algoritmo | Inicio | Destino | Nodos visitados | Cantidad de aristas | Tiempo |
|------|-----------|--------|---------|-----------------|---------------------|--------|
| 1    | BFS       | | | | | |
| 1    | DFS       | | | | | |
| 2    | BFS       | | | | | |
| 2    | DFS       | | | | | |
| 3    | BFS       | | | | | |
| 3    | DFS       | | | | | |
```

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