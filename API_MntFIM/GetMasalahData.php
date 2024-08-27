<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id_masalah'];

    $query = "SELECT *
            FROM mnt_ms_Masalah
            WHERE ID = '$id'";

    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));

    $row = mysqli_fetch_object($result);

    echo json_encode($row);
}
