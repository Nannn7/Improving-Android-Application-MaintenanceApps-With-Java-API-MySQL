<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $uname = $_POST['uname'];
    $pass = $_POST['password'];
    // $email = "aasdasdil.com";
    // $pass = "123";

    $query = "SELECT ID, Nama, Role, Status
                FROM app_User
                WHERE Username = '$uname' AND Password = '$pass'";

    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));

    $user = mysqli_fetch_object($result);

    echo ($user != null) ?
        json_encode(array("kode" => 1, "response" => $user)) :
        json_encode(array("kode" => 0));
}
