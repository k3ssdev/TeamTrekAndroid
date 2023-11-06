<?php

// Incluye el archivo de configuración
include('config.php');

try {
    // Crear una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);

    // Establecer el modo de errores de PDO a excepciones
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Preparar la consulta para obtener los avisos
    $sql = "SELECT 
        a.id_aviso, 
        a.titulo, 
        a.descripcion, 
        a.fecha_publicacion, 
        a.fecha_expiracion, 
        a.autor_id, 
        a.activo
    FROM 
        Avisos a";

    // Si necesitas filtrar los avisos por usuario o alguna otra condición, puedes agregar aquí un WHERE y usar bindParam como antes

    $stmt = $conn->prepare($sql);
    $stmt->execute(); // Ejecutar la consulta

    // Crear el documento XML de respuesta
    header('Content-Type: text/xml');

    // Start of XML structure
    $xml = new SimpleXMLElement('<root/>');

    // Fetch the data and create XML structure in a loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        // Aviso node
        $aviso = $xml->addChild('aviso');

        // Añadir datos del aviso
        $aviso->addChild('ID', $row['id_aviso']);
        $aviso->addChild('Titulo', $row['titulo']);
        $aviso->addChild('Descripcion', $row['descripcion']);
        $aviso->addChild('FechaPublicacion', $row['fecha_publicacion']);
        $aviso->addChild('FechaExpiracion', $row['fecha_expiracion']);
        $aviso->addChild('AutorID', $row['autor_id']);
        $aviso->addChild('Activo', $row['activo'] ? 'true' : 'false'); // Ejemplo de cómo manejar un booleano
    }

    // Output or save the XML
    echo $xml->asXML();

} catch (PDOException $e) {
    // En caso de error en la conexión o consulta, manejar la excepción
    die("Error en la conexión: " . $e->getMessage());
}

?>
