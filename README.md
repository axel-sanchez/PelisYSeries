# PelisYSeries
La aplicación cuenta con 4 capas principales: 
  * data: dentro de ella tenemos la Database que es la clase encargada de crear la base de datos y tengo un objeto creado para acceder a los nombres de las tablas
  Contiene 3 paquetes:
    - models: es donde tengo las clases para armar recibir las respuestas de la api y armar las peliculas y los videos.
    - repository: contiene GenericRepository que se encarga de hacer todas las consultas a la database, esta hecha con el patrón singleton
    - service: contiene una interface llamada ApiService que contiene las peticiones a la api y en la clase ConnectToApi a través de retrofit hago las llamadas a la api para optener las peliculas o videos
  * domain: contiene los casos de uso que crean instancias del repositorio local o del ConectToApi, en el caso de DetailsUseCase devuelve una Movie, el MovieUseCase devuelve un listado de ItemViewPager que es una clase que sirve
            para el armado del viewpager de MainFragment y PopularUseCase, TopRatedUseCase, UpcomingUseCase devuelven un listado de Movie ya sea de la categoria o de la búsqueda online
  * ui: contiene los fragments y activities de la aplicacion. El main activity tiene un FrameLayout donde pega el MainFragment que tiene un viewPager donde se muestran los PopularFragment
        TopRatedFragment y UpcomingFragment que poseen un recyclerView con peliculas, cuando se hace click en uno de estos items del recyclerview se traslada la app a DetailsActivity, donde se muestran los detalles de la 
        pelicula y se puede ver el trailer utilizando la api de youtube
        Dentro de la ui tenemos 3 paquetes:
          - adapter: donde tengo el adaptador del viewPager y el del recyclerView
          - customs: donde tengo la base del fragment, que tiene implementada una extension function
          - interfaces: donde tengo las interfaces para la navegacion entre fragments
  * viewmodel: contiene todos los viewmodels y sus respectivas factories y se encargan de pedirle la información al correspondiente caso de uso e insertarlo en un LiveData para que luego sean observados desde 
               las clases que correspondan del paquete ui y así actualizar las vistas

1) En el principio de responsabilidad única lo que se hace es destinarle a cada clase una sola responsabilidad lo cuál va a hacer que solo haya una razón para que cambie esa clase
Con este principio logramos código menos complejo, se entiende mejor, el mantenimiento se facilita y permite una mejor reutilización
            
2) Yo creo que un código limpio debe tener nombres de variables y métodos entendibles, comentarios para saber como funciona nuestra aplicación pero evitando la redundancia y 
se debe tratar de que las funciones tengan un solo objetivo
