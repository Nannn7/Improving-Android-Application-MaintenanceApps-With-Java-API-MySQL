<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idMesin = $_POST['idMesin'];
    $idPIC = $_POST['idPIC'];

    date_default_timezone_set("Asia/Jakarta");
    $now = date("Y-m-d H:i:s");

    $query = "INSERT INTO mnt_tr_Maintenance (ID_Mesin, PIC, TimeError, TimeStart, TimeStop, Status)
                VALUES ('$idMesin', '$idPIC', '0000-00-00 00:00:00', '$now', '0000-00-00 00:00:00', '1')";

    $result1 = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));

    echo ($result1) ?
        json_encode(array("kode" => 1, "pesan" => "Data berhasil input")) :
        json_encode(array("kode" => 0, "pesan" => "Data gagal input"));
}
