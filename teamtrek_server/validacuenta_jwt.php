<?php
// Incluye el archivo de configuración
require_once 'config.php';
require_once './vendor/autoload.php'; // Asegúrate de que esta ruta coincida con la de tu autoloader de Composer

use Firebase\JWT\JWT;
use Firebase\JWT\Key;

// Clave secreta para codificar y decodificar el token
// Debes guardar esta clave en un lugar seguro, como una variable de entorno
//$jwtKey = getenv('JWT_SECRET_KEY');
$jwtKey = 'JWT_SECRET_KEY';

// Intenta ejecutar el proceso de autenticación
try {
    // Crea una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // Establecer el modo de errores de PDO a excepciones
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Asegúrate de que la solicitud es POST
	if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
	    throw new Exception('Invalid request method, only POST is allowed');
	}

	// Lee los datos JSON enviados con la solicitud POST
	$json = file_get_contents('php://input');
	$data = json_decode($json, true);

	// Comprueba si se decodificaron correctamente los datos JSON
	if (is_null($data)) {
	    throw new Exception('Error decoding JSON');
	}

	// Asigna las variables con los datos obtenidos del JSON
	$usuario = $data['usuario'] ?? null;
	$contrasena = $data['contrasena'] ?? null;

	if (!$usuario || !$contrasena) {
	    throw new Exception('Username and password are required');
	}


    // Consulta SQL para obtener la contraseña hasheada del usuario
    $sql = "SELECT * FROM Usuarios WHERE Usuario = :usuario AND Contrasena = :contrasena";
    $stmt = $conn->prepare($sql);

    
    // Escapar los valores antes de incluirlos en la consulta
    $stmt->bindParam(':usuario', $usuario);
    $stmt->bindParam(':contrasena', $contrasena);
    $stmt->execute();

    if ($stmt->rowCount() === 0) {
        throw new Exception('User does not exist');
    }

    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    // Verificar la contraseña con la versión hasheada
    //if (!password_verify($contrasena, $user['Contrasena'])) {
    //    throw new Exception('Invalid password');
    //}

    // Generar el token JWT
    $issuedAt = time();
    $expirationTime = $issuedAt + 3600; // Token válido por 1 hora desde su emisión
    $payload = [
        'userid' => $user['EmpleadoID'],
        'iat' => $issuedAt,
        'exp' => $expirationTime
    ];

    $token = JWT::encode($payload, $jwtKey, 'HS256');

    // Preparar la respuesta
    $respuesta = [
        'estado' => 'ok',
        'EmpleadoID' => $user['EmpleadoID'],
        'token' => $token
    ];

} catch (Exception $e) {
    $respuesta = [
        'estado' => 'error',
        'mensaje' => $e->getMessage()
    ];
}

// Establecer cabecera de tipo de contenido a JSON
header('Content-Type: application/json');

// Imprimir la respuesta en formato JSON
echo json_encode($respuesta);
