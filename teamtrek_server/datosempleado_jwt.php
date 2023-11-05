<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Incluye el archivo de configuración y las dependencias de JWT
include('config.php');
require_once('./vendor/autoload.php'); // Asegúrate de que esta ruta coincida con la de tu autoloader de Composer

use Firebase\JWT\JWT;
use Firebase\JWT\Key;

// Clave secreta para verificar el JWT
$jwtKey = 'JWT_SECRET_KEY';

// COMENTAR PARA PROBAR SIN TOKEN
try {
    // Obtener el token JWT de la cabecera de autorización o de otro lugar donde lo hayas colocado
    $authHeader = getallheaders()['Authorization'] ?? '';
    preg_match('/Bearer\s(\S+)/', $authHeader, $matches);
    if (!isset($matches[1])) {
        throw new Exception("Token no proporcionado o inválido");
    }


    // Decodificar el token
    $token = $matches[1];
    $decoded = JWT::decode($token, new Key($jwtKey, 'HS256'));
    $userId = $decoded->userid;
// HASTA AQUI

     // USERID PARA PROBAR
     //$userId = "102";

    // Crear una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Comprobar si el usuario es administrador
    $stmt = $conn->prepare("SELECT GROUP_CONCAT(r.Nombre SEPARATOR ', ') AS Roles FROM Usuarios u LEFT JOIN UsuarioRoles ur ON u.EmpleadoID = ur.UsuarioID LEFT JOIN Roles r ON ur.RolID = r.ID WHERE u.EmpleadoID = :userId");
    $stmt->bindParam(':userId', $userId, PDO::PARAM_INT);
    $stmt->execute();

    $userRoles = $stmt->fetch(PDO::FETCH_ASSOC);
    $isAdmin = strpos($userRoles['Roles'], 'Administrador') !== false;

    if ($isAdmin) {
        // Usuario es administrador, puede obtener todos los empleados
         $sql = "SELECT 
            e.ID, 
            e.Nombre, 
            e.FechaIngreso, 
            d.Nombre AS NombreDepartamento, 
            h.Descripcion AS DescripcionHorario, 
            h.HoraInicio, 
            h.HoraFin, 
            u.Usuario, 
            GROUP_CONCAT(r.Nombre SEPARATOR ', ') AS Roles,
            dp.Telefono,
            dp.Direccion,
            dp.Email,
            dp.FechaNacimiento,
            dp.NIF
        FROM 
            Empleados e
            LEFT JOIN Departamentos d ON e.DepartamentoID = d.ID
            LEFT JOIN EmpleadoHorario eh ON e.ID = eh.EmpleadoID
            LEFT JOIN Horarios h ON eh.HorarioID = h.ID
            LEFT JOIN Usuarios u ON e.ID = u.EmpleadoID
            LEFT JOIN UsuarioRoles ur ON u.EmpleadoID = ur.UsuarioID
            LEFT JOIN Roles r ON ur.RolID = r.ID
            LEFT JOIN DatosPersonales dp ON e.ID = dp.EmpleadoID
        WHERE e.ID = :userId
        GROUP BY 
            e.ID, 
            e.Nombre, 
            e.FechaIngreso, 
            d.Nombre, 
            h.Descripcion, 
            h.HoraInicio, 
            h.HoraFin, 
            u.Usuario,
            dp.Telefono,
            dp.Direccion,
            dp.Email,
            dp.FechaNacimiento,
            dp.NIF";
    } else {
        // Usuario no es administrador, solo puede ver su propia información
        $userId = filter_var($userId, FILTER_SANITIZE_NUMBER_INT);

                $sql = "SELECT 
            e.ID, 
            e.Nombre, 
            e.FechaIngreso, 
            d.Nombre AS NombreDepartamento, 
            h.Descripcion AS DescripcionHorario, 
            h.HoraInicio, 
            h.HoraFin, 
            u.Usuario, 
            GROUP_CONCAT(r.Nombre SEPARATOR ', ') AS Roles,
            dp.Telefono,
            dp.Direccion,
            dp.Email,
            dp.FechaNacimiento,
            dp.NIF
        FROM 
            Empleados e
            LEFT JOIN Departamentos d ON e.DepartamentoID = d.ID
            LEFT JOIN EmpleadoHorario eh ON e.ID = eh.EmpleadoID
            LEFT JOIN Horarios h ON eh.HorarioID = h.ID
            LEFT JOIN Usuarios u ON e.ID = u.EmpleadoID
            LEFT JOIN UsuarioRoles ur ON u.EmpleadoID = ur.UsuarioID
            LEFT JOIN Roles r ON ur.RolID = r.ID
            LEFT JOIN DatosPersonales dp ON e.ID = dp.EmpleadoID
        WHERE e.ID = :userId
        GROUP BY 
            e.ID, 
            e.Nombre, 
            e.FechaIngreso, 
            d.Nombre, 
            h.Descripcion, 
            h.HoraInicio, 
            h.HoraFin, 
            u.Usuario,
            dp.Telefono,
            dp.Direccion,
            dp.Email,
            dp.FechaNacimiento,
            dp.NIF";
    }

    $stmt = $conn->prepare($sql);

    // Si no es administrador, enlazar userId a la consulta
    if (!$isAdmin) {
        $stmt->bindParam(':userId', $userId, PDO::PARAM_INT);
    }

    $stmt->execute(); // Ejecutar la consulta

    // Header para JSON
    header('Content-Type: application/json');

    // Inicializar el array que contendrá los datos de los empleados
    $empleados = [];


    // Fetch the data
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        // Cada $row representa un empleado, simplemente lo añadimos al array
        $empleados[] = [
            'identificacion' => [
                'ID' => $row['ID'],
                'Nombre' => $row['Nombre'],
                'FechaIngreso' => $row['FechaIngreso'],
                'Usuario' => $row['Usuario'],
            ],
            'departamento' => [
                'NombreDepartamento' => $row['NombreDepartamento'],
            ],
            'horario' => [
                'DescripcionHorario' => $row['DescripcionHorario'],
                'HoraInicio' => $row['HoraInicio'],
                'HoraFin' => $row['HoraFin'],
            ],
            'roles' => [
                'Roles' => $row['Roles'],
            ],
            'datosPersonales' => [
                'Telefono' => $row['Telefono'],
                'Direccion' => $row['Direccion'],
                'Email' => $row['Email'],
                'FechaNacimiento' => $row['FechaNacimiento'],
                'NIF' => $row['NIF'],
            ]
        ];
    }

    // Convertir los datos a JSON y mostrar
    echo json_encode(['empleados' => $empleados]);

    // Recuerda cerrar la conexión a la base de datos si es necesario
    $conn = null;

// COMNTAR PARA PROBAR SIN TOKEN
} catch (Exception $e) {
    // Si hay un error en el token o en la consulta, manejar aquí
    header('HTTP/1.0 401 Unauthorized');
    echo json_encode(["error" => $e->getMessage()]);
    exit;
}

?>
