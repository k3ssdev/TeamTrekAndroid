<?php
// Incluye el archivo de configuración
require_once 'config.php';
require_once './vendor/autoload.php'; // Asegúrate de que esta ruta coincida con la de tu autoloader de Composer

use Firebase\JWT\JWT;
use Firebase\JWT\Key;

// Clave secreta para codificar y decodificar el token
// Debes guardar esta clave en un lugar seguro, como una variable de entorno
$jwtKey = getenv('JWT_SECRET_KEY');

// Intenta ejecutar el proceso de autenticación
try {
    // Crea una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // Establecer el modo de errores de PDO a excepciones
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Asegurarse de que la solicitud es POST
    if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
        throw new Exception('Invalid request method, only POST is allowed');
    }

    // Obtener los datos del formulario
    $usuario = $_POST['usuario'] ?? null;
    $contrasena = $_POST['contrasena'] ?? null;

    if (!$usuario || !$contrasena) {
        throw new Exception('Username and password are required');
    }

    // Consulta SQL para obtener la contraseña hasheada del usuario
    $sql = "SELECT EmpleadoID, Contrasena FROM Usuarios WHERE Usuario = :usuario LIMIT 1";
    $stmt = $conn->prepare($sql);
    $stmt->bindParam(':usuario', $usuario, PDO::PARAM_STR);
    $stmt->execute();

    if ($stmt->rowCount() === 0) {
        throw new Exception('User does not exist');
    }

    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    // Verificar la contraseña con la versión hasheada
    if (!password_verify($contrasena, $user['Contrasena'])) {
        throw new Exception('Invalid password');
    }

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
